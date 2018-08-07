package com.telstra.health.shot.service;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring4.SpringTemplateEngine;

import com.telstra.health.shot.dto.ContactUsDTO;
import com.telstra.health.shot.util.ShotConstants;

@Service
public class RecaptchaServiceImpl implements RecaptchaService {
	
	 
	  @Value("${google.recaptcha.secret}") 
	  String recaptchaSecret;
	  
	  @Value("${google.recaptcha.verify.url}")
	  private String GOOGLE_RECAPTCHA_VERIFY_URL;
	   
	  @Autowired
	  RestTemplateBuilder restTemplateBuilder;
	 
	  public String verifyRecaptcha(String recaptchaResponse){
	    Map<String, String> body = new HashMap<>();
	    body.put("secret", recaptchaSecret);
	    body.put("response", recaptchaResponse);
	    System.out.println("Request body for recaptcha: {}" + body);
	    ResponseEntity<Map> recaptchaResponseEntity = 
	      restTemplateBuilder.build()
	        .postForEntity(GOOGLE_RECAPTCHA_VERIFY_URL+
	          "?secret={secret}&response={response}", 
	          body, Map.class, body);
	             
	    Map<String, Object> responseBody = 
	      recaptchaResponseEntity.getBody();
	       
	    boolean recaptchaSucess = (Boolean)responseBody.get("success");
	    if ( !recaptchaSucess) {
	    	return ShotConstants.RECAPTCHA_VERIFY_ERROR_CODE;
	    }else {
	      return ShotConstants.RECAPTCHA_VERIFY_SUCCESS;
	    }
	  }
}
