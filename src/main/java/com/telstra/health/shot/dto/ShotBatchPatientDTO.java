package com.telstra.health.shot.dto;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ShotBatchPatientDTO {

	private String patientFirstName;
	private String patientLastName;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Timestamp patientDob;

	private String patientUr;
	private String patientId;

	private List<ShotBatchTempDTO> batches;

	public Timestamp getPatientDob() {
		return patientDob;
	}

	public void setPatientDob(Timestamp patientDob) {
		this.patientDob = patientDob;
	}

	public String getPatientFirstName() {
		return patientFirstName;
	}

	public void setPatientFirstName(String patientFirstName) {
		this.patientFirstName = patientFirstName;
	}

	public String getPatientLastName() {
		return patientLastName;
	}

	public void setPatientLastName(String patientLastName) {
		this.patientLastName = patientLastName;
	}

	public String getPatientUr() {
		return patientUr;
	}

	public void setPatientUr(String patientUr) {
		this.patientUr = patientUr;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public List<ShotBatchTempDTO> getBatches() {
		return batches;
	}

	public void setBatches(List<ShotBatchTempDTO> batches) {
		this.batches = batches;
	}

	
}
