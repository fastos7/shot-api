package com.telstra.health.shot.api.exception;

import java.util.List;

import org.springframework.http.HttpStatus;

public class ApiExcepionMsg {
	
	private HttpStatus status;
	private List<String> errorMessages;
	private String errorMessage;
		
	public ApiExcepionMsg(HttpStatus status, String message) {
	    super();
	    this.status = status;
 	    this.errorMessage = message;
	}
	
	public ApiExcepionMsg(HttpStatus status, List<String> errorMessages) {
	    super();
	    this.status = status;
 	    this.errorMessages = errorMessages;
	}
	
	public List<String> getErrorMessages() {
		return errorMessages;
	}

	public void setErrorMessages(List<String> errorMessages) {
		this.errorMessages = errorMessages;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	} 
	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	 

}
