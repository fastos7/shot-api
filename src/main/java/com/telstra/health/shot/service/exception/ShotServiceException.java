package com.telstra.health.shot.service.exception;

import com.telstra.health.shot.common.enums.ShotErrors;

public class ShotServiceException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5784291546189301L;
	private String errorCode;
	private String messageKey;
	
	public ShotServiceException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
	}
	
	public ShotServiceException(String message) {
        super(message);
	}

	public ShotServiceException(String errorCode, String messageKey, String message) {
		super(message);
		this.errorCode = errorCode;
		this.messageKey = messageKey;
	}
	
	public ShotServiceException(ShotErrors shotErrors) {
		this.errorCode = shotErrors.getErrorCode();
		this.messageKey = shotErrors.getMsgKey();
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessageKey() {
		return messageKey;
	}

	public void setMessageKey(String messageKey) {
		this.messageKey = messageKey;
	}

}
