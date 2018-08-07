package com.telstra.health.shot.service;

public interface PasswordResetService {

	String generatePasswordToken(String email);
	
	void resetPassword(String resetToken, String newPassword);
}
