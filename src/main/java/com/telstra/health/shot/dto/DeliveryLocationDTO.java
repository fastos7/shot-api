package com.telstra.health.shot.dto;

public class DeliveryLocationDTO {
	private String locationKey;
	private String locationName;

	public DeliveryLocationDTO() {
		
	}
	
	public DeliveryLocationDTO(String locationKey, String locationName) {
		this.locationKey = locationKey;
		this.locationName = locationName;
	}

	public String getLocationKey() {
		return locationKey;
	}
	public void setLocationKey(String customerKey) {
		this.locationKey = customerKey;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
}
