package com.telstra.health.shot.api.response;

public class BatchAPIResponse {

	private long batchId;
	private String errorCode;
	private String shotSideMessage;
	private String shotCancelSave;

	public BatchAPIResponse() {
	}

	public BatchAPIResponse(long batchId, String errorCode, String shotSideMessage, String shotCancelSave) {
		super();
		this.batchId = batchId;
		this.errorCode = errorCode;
		this.shotSideMessage = shotSideMessage;
		this.shotCancelSave = shotCancelSave;
	}

	public long getBatchId() {
		return batchId;
	}

	public void setBatchId(long batchId) {
		this.batchId = batchId;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getShotSideMessage() {
		return shotSideMessage;
	}

	public void setShotSideMessage(String shotSideMessage) {
		this.shotSideMessage = shotSideMessage;
	}

	public String getShotCancelSave() {
		return shotCancelSave;
	}

	public void setShotCancelSave(String shotCancelSave) {
		this.shotCancelSave = shotCancelSave;
	}

}
