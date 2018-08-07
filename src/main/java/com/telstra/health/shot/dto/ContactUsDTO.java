package com.telstra.health.shot.dto;

public class ContactUsDTO {
	
	private String name;
	private String email;
	private String phone;
	private String reason;
	private String detailedDescription;
	private String captcha;
	private String captchaVerifyCode;
	private String emailConfirmation;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getDetailedDescription() {
		return detailedDescription;
	}
	public void setDetailedDescription(String detailedDescription) {
		this.detailedDescription = detailedDescription;
	}
	public String getCaptcha() {
		return captcha;
	}
	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCaptchaVerifyCode() {
		return captchaVerifyCode;
	}
	public void setCaptchaVerifyCode(String captchaVerifyCode) {
		this.captchaVerifyCode = captchaVerifyCode;
	}
	public String getEmailConfirmation() {
		return emailConfirmation;
	}
	public void setEmailConfirmation(String emailConfirmation) {
		this.emailConfirmation = emailConfirmation;
	}
}
