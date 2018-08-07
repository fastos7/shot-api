package com.telstra.health.shot.service;

public interface RecaptchaService {
	public String verifyRecaptcha(String recaptchaResponse);
}
