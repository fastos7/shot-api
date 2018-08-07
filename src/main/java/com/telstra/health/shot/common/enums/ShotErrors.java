package com.telstra.health.shot.common.enums;

public enum ShotErrors {

	USER_EMAIL_DOES_NOT_EXIST("USR-MAIL-NOT-FOUND", "user.email.not.exists"),
	PWD_RESET_TOKEN_EXPIRED("PWD-RESET-TOKEN-EXPIRED", "pwd.reset.token.expired"),
	INVALID_DELV_DATE_TIME("INVALID-DELV-DATE-TIME", "delivery.date.time.invalid");
	
	private String errorCode;
	private String msgKey;
	
	ShotErrors(String code, String key) {
		this.errorCode = code;
		this.msgKey = key;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getMsgKey() {
		return msgKey;
	}
}
