package com.telstra.health.shot.api;

import org.thymeleaf.context.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.telstra.health.shot.dto.ContactUsDTO;
import com.telstra.health.shot.dto.MessageDetailsDTO;
import com.telstra.health.shot.service.EmailService;
import com.telstra.health.shot.service.RecaptchaService;
import com.telstra.health.shot.util.ShotConstants;

@RestController
@RequestMapping("/api/contactus/email/")
public class ContactUsAPI {
	
	@Autowired
	RecaptchaService captchaService;
	
	@Autowired
	EmailService emailService;
	
	@RequestMapping(value ="/", method = RequestMethod.POST)
 	public @ResponseBody ContactUsDTO  validateAndSendEmail(@RequestBody ContactUsDTO contactUsDetails) {
		
		String captcha = contactUsDetails.getCaptcha();
		String captchaVerifyMessage = captchaService.verifyRecaptcha(captcha);
	    contactUsDetails.setCaptchaVerifyCode(captchaVerifyMessage);
		if(captchaVerifyMessage.equalsIgnoreCase(ShotConstants.RECAPTCHA_VERIFY_SUCCESS))
		{
			Context ctx = new Context();
			ctx.setVariable(ShotConstants.CONTACT_US_TOKEN_NAME, contactUsDetails.getName());
			ctx.setVariable(ShotConstants.CONTACT_US_TOKEN_EMAIL, contactUsDetails.getEmail());
			ctx.setVariable(ShotConstants.CONTACT_US_TOKEN_PHONE, contactUsDetails.getPhone());
			ctx.setVariable(ShotConstants.CONTACT_US_TOKEN_REASON, contactUsDetails.getReason());
			ctx.setVariable(ShotConstants.CONTACT_US_TOKEN_DETAILED_DESCRIPTION, contactUsDetails.getDetailedDescription());
			
			String templateName = ShotConstants.CONTACT_US_EMAIL_TEMPLATE;
			
			MessageDetailsDTO messageDetailsDTO = new MessageDetailsDTO();
			messageDetailsDTO.setSubject(ShotConstants.CONTACT_US_SUBJECT + " - " + contactUsDetails.getReason());
			messageDetailsDTO.setFrom(contactUsDetails.getEmail());
			messageDetailsDTO.setTo(ShotConstants.CONTACT_US_TO);
			
			String emailConfirmation = emailService.sendEmail(ctx, templateName, messageDetailsDTO);
			contactUsDetails.setEmailConfirmation(emailConfirmation);
		}
			 
	    return contactUsDetails;
	}
}
