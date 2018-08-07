package com.telstra.health.shot.dto;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ResetPasswordDTO {

	private Long userId;
	private String email;
	private String resetToken;
	private String password;
	
	@JsonIgnore
	private ZonedDateTime utcDateTime;
	

	public ResetPasswordDTO() {
	}
	
	public ResetPasswordDTO(String resetToken, Long userId, ZonedDateTime utcDateTime) {
		this.resetToken = resetToken;
		this.userId = userId;
		this.utcDateTime = utcDateTime;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getResetToken() {
		return resetToken;
	}
	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public ZonedDateTime getUtcDateTime() {
		return utcDateTime;
	}
	public void setUtcDateTime(ZonedDateTime utcDateTime) {
		this.utcDateTime = utcDateTime;
	}
}
