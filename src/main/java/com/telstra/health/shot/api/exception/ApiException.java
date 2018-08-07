package com.telstra.health.shot.api.exception;

import java.util.ArrayList;
import java.util.List;


 
public class ApiException extends RuntimeException  { 
 
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 495111243420902462L;
	
	
	private List<String> errorMessages;
	private String errorMessage;
	private String errorCode;
	
	public ApiException(String errorMessage) {
		super(errorMessage);
		this.setErrorMessage(errorMessage);
		List<String> errors = new ArrayList();
		errors.add(errorMessage);
		this.setErrorMessages(errors);
	} 
	
	public ApiException(String errorMessage, String errorCode) {
		super(errorMessage);
		this.setErrorMessage(errorMessage);
		this.setErrorCode(errorCode);
		List<String> errors = new ArrayList();
		errors.add(errorMessage);
		this.setErrorMessages(errors);
	} 

	public ApiException(List<String> errorMessages, String errorCode) {
		this.setErrorMessages(errorMessages);
		this.setErrorCode(errorCode);
	} 

	public ApiException(List<String> errorMessages) {
 		this.setErrorMessages(errorMessages);
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public List<String> getErrorMessages() {
		return errorMessages;
	}

	public void setErrorMessages(List<String> errorMessages) {
		this.errorMessages = errorMessages;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	} 
	
	

}
