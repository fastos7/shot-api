package com.telstra.health.shot.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.telstra.health.shot.dao.exception.DataAccessException;
import com.telstra.health.shot.dto.GeneralSearchPatientDTO;
import com.telstra.health.shot.dto.PatientHistoryDTO;
import com.telstra.health.shot.entity.Patient;

public interface PatientDAOCustom {

	public List<Patient> searchPatients(String customerKey, 
								   		String firstName, 
								   		String surName, 
								   		String ur,
								   		Boolean status,
								   		Pageable pageable) throws DataAccessException;
	
	public Long getPatientCount(String customerKey, 
						   		String firstName, 
						   		String surName, 
						   		String ur,
						   		Boolean status) throws DataAccessException;

	List<PatientHistoryDTO> getPatientHistory(long patientId) throws DataAccessException; 
	
	List<GeneralSearchPatientDTO> generalSearchPatients(String customerKey, String searchStr);

	List<Patient> searchPatientsWithUR(String customerKey, List<String> listOfURs) throws DataAccessException;

 	String retrieveNextMRN() throws DataAccessException;
 	
 	int getPatientByURForCustomer(String ur, String customerKey) throws DataAccessException;

}
