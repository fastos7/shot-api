package com.telstra.health.shot.dto;

import java.util.List;

public class OrderDTO {

	private String ordNo;
	private String ordCusKey;
	private String ordLastUpdAction;
	private String ordStatus;
	// send date/time in axis dispatch date/time in SHOT
	private String ordSendDate;
	private String ordDeliveryDate;
	private int createdBy;
	private String ordDeliveryLocation;
	private String ordDeliveryLocationName;
	private String ordBillTo;
	private String ordDate;
	private List<ShotBatchPatientDTO> patients;
	private List<BatchDTO> batches;
	private String ordEntity;
	
	public OrderDTO() {
	}

	public String getOrdNo() {
		return ordNo;
	}

	public void setOrdNo(String ordNo) {
		this.ordNo = ordNo;
	}

	public String getOrdCusKey() {
		return ordCusKey;
	}

	public void setOrdCusKey(String ordCusKey) {
		this.ordCusKey = ordCusKey;
	}

	public String getOrdLastUpdAction() {
		return ordLastUpdAction;
	}

	public void setOrdLastUpdAction(String ordLastUpdAction) {
		this.ordLastUpdAction = ordLastUpdAction;
	}

	public String getOrdStatus() {
		return ordStatus;
	}

	public void setOrdStatus(String ordStatus) {
		this.ordStatus = ordStatus;
	}

	public String getOrdSendDate() {
		return ordSendDate;
	}

	public void setOrdSendDate(String ordSendDate) {
		this.ordSendDate = ordSendDate;
	}

	public String getOrdDeliveryDate() {
		return ordDeliveryDate;
	}

	public void setOrdDeliveryDate(String ordDeliveryDate) {
		this.ordDeliveryDate = ordDeliveryDate;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public String getOrdDeliveryLocation() {
		return ordDeliveryLocation;
	}

	public void setOrdDeliveryLocation(String ordDeliveryLocation) {
		this.ordDeliveryLocation = ordDeliveryLocation;
	}

	public String getOrdDeliveryLocationName() {
		return ordDeliveryLocationName;
	}

	public void setOrdDeliveryLocationName(String ordDeliveryLocationName) {
		this.ordDeliveryLocationName = ordDeliveryLocationName;
	}

	public String getOrdBillTo() {
		return ordBillTo;
	}

	public void setOrdBillTo(String ordBillTo) {
		this.ordBillTo = ordBillTo;
	}

	public String getOrdDate() {
		return ordDate;
	}

	public void setOrdDate(String ordDate) {
		this.ordDate = ordDate;
	}

	public String getOrdEntity() {
		return ordEntity;
	}

	public void setOrdEntity(String ordEntity) {
		this.ordEntity = ordEntity;
	}

	public List<ShotBatchPatientDTO> getPatients() {
		return patients;
	}

	public void setPatients(List<ShotBatchPatientDTO> patients) {
		this.patients = patients;
	}

	public List<BatchDTO> getBatches() {
		return batches;
	}

	public void setBatches(List<BatchDTO> batches) {
		this.batches = batches;
	}

	@Override
	public String toString() {
		return "OrderDTO [ordNo=" + ordNo + ", ordCusKey=" + ordCusKey + ", ordLastUpdAction=" + ordLastUpdAction
				+ ", ordStatus=" + ordStatus + ", ordSendDate=" + ordSendDate + ", ordDeliveryDate=" + ordDeliveryDate
				+ ", createdBy=" + createdBy + ", ordDeliveryLocation=" + ordDeliveryLocation
				+ ", ordDeliveryLocationName=" + ordDeliveryLocationName + ", ordBillTo=" + ordBillTo + ", ordDate="
				+ ordDate + ", patients=" + patients + ", batches=" + batches + ", ordEntity=" + ordEntity + "]";
	}

	
}
