package com.telstra.health.shot.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.telstra.health.shot.dto.GeneralSearchPatientDTO;
import com.telstra.health.shot.dto.PatientDTO;
import com.telstra.health.shot.dto.PatientHistoryDTO;
import com.telstra.health.shot.entity.Patient;
import com.telstra.health.shot.service.exception.ShotServiceException;

public interface PatientService {
	
	List<PatientDTO> savePatients(List<PatientDTO> patient, char action) throws ShotServiceException;
	//void deletePatient(Patient patient);
	//void updatePatient(Patient patient); 
	
 	List<PatientDTO> getAllPatients(String customerId, String firstName, String surName, String ur, Boolean status, Pageable pageable) throws ShotServiceException;

 	Long getPatientCount(String customerId, String firstName, String surName, String ur, Boolean status) throws ShotServiceException;
 	 	
 	PatientDTO getPatient(long patientId, String customerKey) throws ShotServiceException;
 	
	List<PatientHistoryDTO> getPatientHistory(long patientId) throws ShotServiceException;
	
	List<PatientDTO> processPatientsFile(List<PatientDTO> patientDtoList) throws ShotServiceException;
 	
 	List<GeneralSearchPatientDTO> generalSearchPatients(String customerKey, String searchStr);
 	
 	List<String> validatePatientFile(List<PatientDTO> patientDtoList , String customerId, List<String> invalidRecords) throws ShotServiceException;
 	
	List<PatientDTO> getPatientsFromFile(MultipartFile csvFile, String customerId) throws ShotServiceException;
	
	String retrieveNextMRN() throws ShotServiceException;
	
	int getPatientForUR(String ur, String customerKey) throws ShotServiceException;
 	
}
