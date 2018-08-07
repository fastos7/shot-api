package com.telstra.health.shot.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;


/**
 * The persistent class for the Patient database table.
 * 
 */
@Entity
@Table(name = "SHOT_Patients")
public class Patient implements Serializable {
	private static final long serialVersionUID = 1L;

 	@Id
 	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "patientid")
	private Long patientId;
 	
	@Column(name="customerkey")
	private String customerKey;
	
	@Column(name="firstname")
	private String firstName;

	@Column(name="surname")
	private String surName; 

	@Column(name="dob")
	private Date dob; 
	
	@Column(name="ur")
	private String ur;

	@Column(name="trialid")
	private String trialId; 
	
	@Column(name="trialkey")
	private String trialKey; 
	
	@Column(name="isactive")
	@Type(type="yes_no")
	private Boolean isActive;

	@Column(name="createdby")
	private int createdBy;

	@Column(name="createddate", insertable = true, updatable = false)
	private Timestamp createdDate;  

	@Column(name="updatedby")
	private int updatedBy;

	@Column(name="updateddate",  insertable = false, updatable = true)
	private Timestamp updatedDate;
	
	public Patient(Long patientId, String customerKey, String firstName,
			String surName, Date dob, String ur, String trialId,
			String trialKey, boolean isActive, int createdBy,
			Timestamp createdDate, int updatedBy, Timestamp updatedDate) {
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
		this.createdDate = createdDate;
		this.updatedBy = updatedBy;
		this.updatedDate = updatedDate;
	} 

	public Patient() {
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

	public Long getPatientId() {
		return this.patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}
 
	public int getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Date getDob() {
		return this.dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}
 

	public int getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Timestamp getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

}