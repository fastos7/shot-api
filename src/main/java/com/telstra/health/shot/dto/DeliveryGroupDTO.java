package com.telstra.health.shot.dto;

import java.util.SortedSet;

public class DeliveryGroupDTO {

	private String facilityName;
	private String facilityContactNo;
	private Boolean displayIsRestrictedFlag = false;
	
	private SortedSet < DeliveryDateTimeDTO > deliveryDateTimes;

	public DeliveryGroupDTO() {
		
	}
	
	public DeliveryGroupDTO(String facilityName, String facilityContactNo,
			SortedSet<DeliveryDateTimeDTO> deliveryDateTimes) {
		this.facilityName = facilityName;
		this.facilityContactNo = facilityContactNo;
		this.deliveryDateTimes = deliveryDateTimes;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public String getFacilityContactNo() {
		return facilityContactNo;
	}

	public void setFacilityContactNo(String facilityContactNo) {
		this.facilityContactNo = facilityContactNo;
	}

	public SortedSet<DeliveryDateTimeDTO> getDeliveryDateTimes() {
		return deliveryDateTimes;
	}

	public void setDeliveryDateTimes(SortedSet<DeliveryDateTimeDTO> deliveryDateTimes) {
		this.deliveryDateTimes = deliveryDateTimes;
	}

	public Boolean getDisplayIsRestrictedFlag() {
		return displayIsRestrictedFlag;
	}

	public void setDisplayIsRestrictedFlag(Boolean displayIsRestrictedFlag) {
		this.displayIsRestrictedFlag = displayIsRestrictedFlag;
	}
}
