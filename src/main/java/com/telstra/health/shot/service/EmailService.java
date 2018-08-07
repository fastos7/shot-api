package com.telstra.health.shot.service;

import org.thymeleaf.context.Context;

import com.telstra.health.shot.dto.MessageDetailsDTO;

public interface EmailService {
	
	public String sendEmail(Context ctx, String templateName, MessageDetailsDTO messageDetailsDTO);

}
