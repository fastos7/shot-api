package com.telstra.health.shot.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import com.telstra.health.shot.dto.MessageDetailsDTO;
import com.telstra.health.shot.util.ShotConstants;

@Service
public class EmailServiceImpl implements EmailService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	private SpringTemplateEngine templateEngine;
	
	@Override
	public String sendEmail(Context ctx, String templateName, MessageDetailsDTO messageDetailsDTO) {

		try {
			final MimeMessage mimeMessage = this.emailSender.createMimeMessage();
			final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8"); // true = multipart
			message.setSubject(messageDetailsDTO.getSubject());
			message.setFrom(messageDetailsDTO.getFrom());
			message.setTo(messageDetailsDTO.getTo());
	
			// Create the HTML body using Thymeleaf template
			final String htmlContent = this.templateEngine.process(templateName, ctx);
			message.setText(htmlContent, true); // true = isHtml
	
			// Send email
			this.emailSender.send(mimeMessage);
		} catch (MessagingException ex) {
			logger.error("Error occurred in sending Email message", ex);
			return ShotConstants.EMAIL_SEND_ERROR;
			
		}
	
		return ShotConstants.EMAIL_SEND_SUCCESS;
	}

}
