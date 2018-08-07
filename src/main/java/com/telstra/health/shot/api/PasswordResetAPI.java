package com.telstra.health.shot.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.telstra.health.shot.dto.ResetPasswordDTO;
import com.telstra.health.shot.service.PasswordResetService;

@RestController
@RequestMapping("/password/token/")
public class PasswordResetAPI {
	
	@Autowired
	private PasswordResetService passWordResetService; 
	
	@PostMapping("/")
	@ResponseStatus(HttpStatus.CREATED)
	public void createPasswordResetToken(@RequestBody ResetPasswordDTO resetPasswordDTO) {
		
		this.passWordResetService.generatePasswordToken(resetPasswordDTO.getEmail());
	}

	@PutMapping("/{resetToken}")
	@ResponseStatus(HttpStatus.OK)
	public void updateUserPassword(@PathVariable String resetToken, @RequestBody ResetPasswordDTO resetPasswordDTO) {
		
		this.passWordResetService.resetPassword(resetToken, resetPasswordDTO.getPassword());
	}
}
