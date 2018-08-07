package com.telstra.health.shot.service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import com.telstra.health.shot.common.enums.ShotErrors;
import com.telstra.health.shot.dao.UserAccountDAO;
import com.telstra.health.shot.dto.ResetPasswordDTO;
import com.telstra.health.shot.entity.Users;
import com.telstra.health.shot.service.exception.ShotServiceException;
import com.telstra.health.shot.util.ShotUtils;

/**
 * Implementation class of Service responsible for Resetting User's password.
 * @author osama.shakeel
 *
 */
@Service
@Transactional
public class PasswordResetServiceImpl implements PasswordResetService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserAccountDAO userAccountDAO;

	@Autowired
	private UserService userService;

	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	private SpringTemplateEngine templateEngine;

	@Value("${password.reset.token.expiry.minutes}")
	private Integer tokenExpiryMinutes;

	@Value("${ui.app.url}")
	private String uiAppUrl;
	
	@Value("${shot.app.enc.key}")
	private String shotEncKey;

	// Encryption used to encrypt Password reset token
	private Cipher encryptCipher;
	
	// Decryption Ciphers used to decrypt Password reset token
	private Cipher decryptCipher;

	/**
	 * Initializes the Encryption and Decryption Ciphers using the encryption 
	 * key configured in the properties file. The encrypted key, is first decrypted
	 * using AES Algo before initializing the Ciphers.
	 */
	@PostConstruct
	public void init() {
		try {
			String shotKey = new String(Base64.decodeBase64(shotEncKey), "UTF-8");
			SecretKey secretKey = new SecretKeySpec(shotKey.getBytes("UTF-8"), "AES");

			encryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			encryptCipher.init(Cipher.ENCRYPT_MODE, secretKey);

			decryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			decryptCipher.init(Cipher.DECRYPT_MODE, secretKey);
		} catch (Exception ex) {
			throw new RuntimeException("Cipher initialization failed", ex);
		}
	}

	/**
	 * Generates an Encrypted password reset token and
	 * sends it as part of the link in an email to the User with given email.
	 * @param email User's email
	 * @return Password reset token
	 */
	public String generatePasswordToken(String email) {
		String resetToken = null;

		// Get user by email
		Users user = this.userService.getUserByEmail(email);

		// If User with the specified email exists then generate Password Reset Token
		if (user != null) {
			try {
				// Generate token and save it in User instance.
				resetToken = this.createEncryptedPasswordToken(user.getUserId());

				// Send mail to User's email containing the reset password link
				this.sendPasswordTokenEmail(user, resetToken);
				logger.info("Password Reset token generated and Email sent for User with email: {}", email);
			} catch (RuntimeException ex) {
				logger.error("Error occurred in generating Password Reset token for User with email: " + email, ex);
				throw ex;
			}
		} else {
			throw new ShotServiceException(ShotErrors.USER_EMAIL_DOES_NOT_EXIST);
		}
		return resetToken;
	}

	/**
	 * Resets the User's password with the given new Password. 
	 * The reset password token is first validated for expiry before
	 * the password is actually reset.
	 * @param resetToken Reset Password token
	 * @param The new Password to set.
	 */
	public void resetPassword(String resetToken, String newPassword) {
		ResetPasswordDTO resetPasswordDTO = this.decryptPasswordToken(resetToken);
		Users user = this.userAccountDAO.findOne(resetPasswordDTO.getUserId());

		// Validate the token before resetting the password.
		if (validatePasswordResetToken(resetPasswordDTO, user)) {
			this.userService.updateUserPassword(user.getEmail(), newPassword);
			logger.info("Password for User with email: {} was successfully reset.", user.getEmail());
		}
	}

	/**
	 * Validate the given Password reset token for the given User.
	 * Checks if the token has not expired and if the User is a valid user.
	 * @param resetPasswordDTO
	 * @param user
	 * @return true if the reset token is valid.
	 */
	protected boolean validatePasswordResetToken(ResetPasswordDTO resetPasswordDTO, Users user) {
		ZonedDateTime currentUTCTime = ZonedDateTime.now(ZoneId.of("UTC"));

		// Check if the current token has not expired
		if (currentUTCTime.isBefore(resetPasswordDTO.getUtcDateTime().plusMinutes(tokenExpiryMinutes))) {

			// Check if the current User is Active and not Deleted.
			if (user != null && !Boolean.TRUE.equals(user.getIsDeleted()) && Boolean.TRUE.equals(user.getIsActive())) {
				logger.info("Password Reset token successfully validated for User with email: {}", user.getEmail());
				return true;
			} else {
				logger.error("User with UserId: {} does not exist in SHOT", resetPasswordDTO.getUserId());
				throw new ShotServiceException(ShotErrors.USER_EMAIL_DOES_NOT_EXIST);
			}
		} else {
			logger.error("Password Reset token has expired for the User with UserId: {}", resetPasswordDTO.getUserId());
			throw new ShotServiceException(ShotErrors.PWD_RESET_TOKEN_EXPIRED);
		}
	}

	/**
	 * Sends the given User an email containing the Password reset link containing the reset token.
	 * @param user User instance whose password reset link will be generated.
	 * @param token Password Reset Token
	 */
	protected void sendPasswordTokenEmail(Users user, String token) {
		// Create Password Reset URL
		StringBuilder urlBuilder = new StringBuilder(this.uiAppUrl).append("reset-password/").append(token);

		// Prepare the template context
		final Context ctx = new Context();
		ctx.setVariable("token_url", urlBuilder.toString());
		String expiryTime = "";
		if(this.tokenExpiryMinutes < 60) {
			expiryTime = this.tokenExpiryMinutes + " minute(s)";
		}
		else {
			if(this.tokenExpiryMinutes % 60 == 0) {
				expiryTime = this.tokenExpiryMinutes/60 + " hour(s)";
			}
			else {
				expiryTime = (int)this.tokenExpiryMinutes/60 + " hour(s)" + " and " + this.tokenExpiryMinutes%60 + " minute(s)";
			}
		}
		ctx.setVariable("token_expiry_time", expiryTime);
		String fullName = " ";
		if(user.getFirstName()!=null && !"".equalsIgnoreCase(user.getFirstName())) {
			if(user.getLastName() != null && !"".equalsIgnoreCase(user.getLastName())) {
				fullName += user.getFirstName() + " " + user.getLastName();
			}
			else {
				fullName += user.getFirstName();
			}
		}
		else {
			if(user.getLastName() != null && !"".equalsIgnoreCase(user.getLastName())) {
				fullName += user.getLastName();
			}
			else {
				fullName = "";
			}
		}

		ctx.setVariable("token_user_full_name", fullName);

		try {
			// Prepare message using a Spring helper
			final MimeMessage mimeMessage = this.emailSender.createMimeMessage();
			final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8"); // true = multipart
			message.setSubject("Reset your SHOT password");
			message.setFrom("NO-REPLY@sladehealth.com.au");
			message.setTo(user.getEmail());

			// Create the HTML body using Thymeleaf template
			final String htmlContent = this.templateEngine.process("email-password-token", ctx);
			message.setText(htmlContent, true); // true = isHtml

			// Send email
			this.emailSender.send(mimeMessage);
		} catch (MessagingException ex) {
			logger.error("Error occurred in sending Password Reset Email message", ex);
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Creates an encrypted password reset token for the given User.
	 * @param userId
	 * @return
	 */
	protected String createEncryptedPasswordToken(Long userId) {
		try {
			StringBuilder builder = new StringBuilder(String.valueOf(userId)).append("#")
					.append(ShotUtils.formatDatetoUTC(ZonedDateTime.now()));
			
			return new String(Base64.encodeBase64URLSafeString(encryptCipher.doFinal(builder.toString().getBytes("UTF-8"))));
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Decrypts the given password reset token
	 * @param resetToken User Password reset token
	 * @return ResetPasswordDTO instance containing the details extracted from the token.
	 */
	protected ResetPasswordDTO decryptPasswordToken(String resetToken) {
		try {
			String decodedToken = new String(decryptCipher.doFinal(Base64.decodeBase64(resetToken.getBytes("UTF-8"))));
			Scanner scanner = new Scanner(decodedToken).useDelimiter("#");
			Long userId = scanner.nextLong();
			String tokenUTCDate = scanner.next();
			scanner.close();

			ZonedDateTime utcDateTime = ShotUtils.parseUTCDate(tokenUTCDate);
			return new ResetPasswordDTO(resetToken, userId, utcDateTime);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}
