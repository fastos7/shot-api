package com.telstra.health.shot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name= "SHOT_CUSTOMER_LOCATION")
public class CustomerLocation {

	@Id
	private Long locationId;
	
	@Column(name = "CUSTOMER_KEY")
	private String customerKey;
	
	@Column(name = "NAME")
	private String name;

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public String getCustomerKey() {
		return customerKey;
	}

	public void setCustomerKey(String customerKey) {
		this.customerKey = customerKey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
