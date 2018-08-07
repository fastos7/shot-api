package com.telstra.health.shot.dto;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * The persistent class for the Patient database table.
 * 
 */
 public class PatientDTO implements Serializable { 
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long patientId; 
	private String customerKey;	
	private String firstName;
	private String surName; 	
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date dob; 	
	private String ur;
	private String trialId;	
	private String trialKey; 	
	private boolean isActive = true;
	private int createdBy;
	
	private int updatedBy; 
	
	
	public PatientDTO(){}
	
	public PatientDTO(long patientId, String customerKey, String firstName,
			String surName, Date dob, String ur, String trialId, String trialKey,
			boolean isActive, int createdBy, Date createdDate,
			int updatedBy, Date updatedDate) {
		super();
		this.patientId = patientId;
		this.customerKey = customerKey;
		this.firstName = firstName;
		this.surName = surName;
		this.dob = dob;
		this.ur = ur;
		this.trialId = trialId;
		this.trialKey = trialKey;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
	}
	public long getPatientId() {
		return patientId;
	}
	public void setPatientId(long patientId) {
		this.patientId = patientId;
	}
	public String getCustomerKey() {
		return customerKey;
	}
	public void setCustomerKey(String customerKey) {
		this.customerKey = customerKey;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getSurName() {
		return surName;
	}
	public void setSurName(String surName) {
		this.surName = surName;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getUr() {
		return ur;
	}
	public void setUr(String ur) {
		this.ur = ur;
	}
	public String getTrialId() {
		return trialId;
	}
	public void setTrialId(String trialId) {
		this.trialId = trialId;
	}
	public String getTrialKey() {
		return trialKey;
	}
	public void setTrialKey(String trialKey) {
		this.trialKey = trialKey;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public int getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}

	
	
	@Override
	public int hashCode() {
		
		return new HashCodeBuilder(17, 37)
        .append(patientId).append(customerKey).append(firstName)
        .append(surName).append(dob).append(ur)
        .append(trialId).append(trialKey).append(isActive)
        .toHashCode(); 
	}

	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		PatientDTO other = (PatientDTO) obj;
		
		return new EqualsBuilder()
        .append(patientId, other.patientId)
        .append(customerKey, other.customerKey)
        .append(surName, other.surName)
        .append(dob, other.dob)
        .append(ur, other.ur)
        .append(trialId, other.trialId)
        .append(trialKey, other.trialKey)
        .append(isActive, other.isActive)
        .append(createdBy, other.createdBy)
        .append(updatedBy, other.updatedBy)
        .isEquals();
		
	 
	}


}