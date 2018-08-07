package com.telstra.health.shot.dto;

import java.io.Serializable;
import java.util.List;

public class PatientSearchDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private List<PatientDTO> patientList;
	private Long count;
	
	public List<PatientDTO> getPatientList() {
		return patientList;
	}
	public void setPatientList(List<PatientDTO> patientList) {
		this.patientList = patientList;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
}
