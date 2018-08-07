package com.telstra.health.shot.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.telstra.health.shot.util.ShotUtils;

public class GeneralSearchPatientDTO {

	@JsonIgnore
	private static DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
	
	private Long patientId;	
	private String firstName;
	private String surName; 
	private String fullName;
	private String mrnNo;
	private String dob;
	private String mrnDob;
	private String fullNameMrn;

	public GeneralSearchPatientDTO() {

	}

	public GeneralSearchPatientDTO(Long patientId, String firstName, String surName, Date dob, String mrnNo) {
		firstName = ShotUtils.isEmpty(firstName)? "-" : firstName;
		surName = ShotUtils.isEmpty(surName)? "-" : surName;
		mrnNo = ShotUtils.isEmpty(mrnNo)? "-" : mrnNo;

		this.patientId = patientId;
		this.firstName = firstName;
		this.surName = surName;
		StringBuilder fullNameBuilder = new StringBuilder(firstName).append(" ").append(surName);
		this.fullName = fullNameBuilder.toString();
		this.fullNameMrn = fullNameBuilder.append(" - ").append(mrnNo).toString();
		this.mrnNo = mrnNo;
		
		StringBuilder mrnDobBuilder = new StringBuilder(mrnNo);
		try {
			String dobStr = "-";
			if (dob != null) {
				dobStr = dateFormat.format(dob);
				dobStr = dobStr.replaceAll("\\.", "");
			}
			this.dob = dobStr;
			mrnDobBuilder.append(", ").append(dobStr).toString();
		} catch (Exception ex) {
			// TODO: handle exception
		}
		this.mrnDob = mrnDobBuilder.toString();
	}
	
	public Long getPatientId() {
		return patientId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getFullNameMrn() {
		return fullNameMrn;
	}

	public void setFullNameMrn(String fullNameMrn) {
		this.fullNameMrn = fullNameMrn;
	}

	public String getMrnDob() {
		return mrnDob;
	}

	public void setMrnDob(String mrnDob) {
		this.mrnDob = mrnDob;
	}

	public String getMrnNo() {
		return mrnNo;
	}

	public void setMrnNo(String mrn) {
		this.mrnNo = mrn;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
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
	
	
}
