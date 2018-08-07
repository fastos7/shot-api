package com.telstra.health.shot.api.exception;

import org.springframework.http.HttpStatus;

public class UserAPIException extends RuntimeException {

	private HttpStatus status;
	private String message;

	public UserAPIException() {
		super();
	}

	public UserAPIException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.message = message;
	}
	
	public UserAPIException(String message, HttpStatus status, Throwable cause) {
		super(message, cause);
		this.message = message;
		this.status = status;
	}
	
	public UserAPIException(String message) {
		super(message);
		this.message = message;
	}
	
	public UserAPIException(Throwable cause) {
		super(cause);
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
