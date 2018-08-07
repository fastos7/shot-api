package com.telstra.health.shot.api.exception;

import java.util.List;

public class APIError {
	
	private String errorCode;
	private List<String> errorMessages;
	private int status;

	public APIError(String errorCode, List<String> errorMessages, int status) {
		super();
		this.errorCode = errorCode;
		this.errorMessages = errorMessages;
		this.status = status;
	}

	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public List<String> getErrorMessages() {
		return errorMessages;
	}
	public void setErrorMessage(List<String> errorMessages) {
		this.errorMessages = errorMessages;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
