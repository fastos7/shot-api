package com.telstra.health.shot.util;

public interface ShotConstants {
	
	public interface ErrorCodes {
		String PATIENT_MANAGEMENT_PATIENT_SAVE_DUPLICATE_MRN = "SH-PM-PS-001";
		String PATIENT_MANAGEMENT_VALIDATE_UPLOAD_ERRORS = "SH-PM-VU-001";
		String ORDER_MANAGEMENT_UPDATE_SHOTBATCH_STATUS = "SH-OM-UB-001";
	}
	
	char ACTION_INSERT = 'I';
	char ACTION_UPDATE = 'U';
	
	public static final String CONTACT_US_EMAIL_TEMPLATE = "contact-us-email-template";
	public static final String RECAPTCHA_VERIFY_ERROR_CODE = "RECAPTCHA_ERROR";
	public static final String RECAPTCHA_VERIFY_SUCCESS = "RECAPTCHA_VERIFY_SUCCESS";
	
	public static final String CONTACT_US_TOKEN_NAME = "token_name";
	public static final String CONTACT_US_TOKEN_EMAIL = "token_email";
	public static final String CONTACT_US_TOKEN_PHONE = "token_phone";
	public static final String CONTACT_US_TOKEN_REASON = "token_reason";
	public static final String CONTACT_US_TOKEN_DETAILED_DESCRIPTION = "token_detailedDescription";
	
	public static final String CONTACT_US_SUBJECT = "New Message From SHOT User";
	public static final String CONTACT_US_TO = "-";
	
	public static final String EMAIL_SEND_SUCCESS = "EMAIL_SEND_SUCCESS";
	public static final String EMAIL_SEND_ERROR = "EMAIL_SEND_ERROR";
	
}
