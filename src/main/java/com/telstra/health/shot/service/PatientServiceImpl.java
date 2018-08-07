package com.telstra.health.shot.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.telstra.health.shot.dao.PatientDAO;
import com.telstra.health.shot.dao.exception.DataAccessException;
import com.telstra.health.shot.dto.GeneralSearchPatientDTO;
import com.telstra.health.shot.dto.PatientDTO;
import com.telstra.health.shot.dto.PatientHistoryDTO;
import com.telstra.health.shot.entity.Patient;
import com.telstra.health.shot.service.exception.ShotServiceException;
import com.telstra.health.shot.util.CSVUtil;
import com.telstra.health.shot.util.ShotConstants;
 
@Service
public class PatientServiceImpl implements PatientService{
	
    public PatientServiceImpl() {
		super();
	}
 

	private static final Logger logger = LoggerFactory.getLogger(PatientServiceImpl.class);


	@Autowired
	PatientDAO patientDao;
	
    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private Environment env;
    
    @Autowired
    private CSVUtil csvutil;
	
	@Override
	public List<PatientDTO> savePatients(List<PatientDTO> patientsDtoList, char action) throws ShotServiceException {
		
		try{
			
		 List<Patient> patients = patientsDtoList.stream()
		          .map(patientsDto -> convertToEntity(patientsDto))
		          .collect(Collectors.toList());
		 
			Timestamp currentTime = new Timestamp((new java.util.Date()).getTime());						 
			
			for(int i = 0; i < patients.size(); i++) {
				if(action == ShotConstants.ACTION_INSERT)
				{
					patients.get(i).setCreatedDate(currentTime);
				}
				else {
					patients.get(i).setUpdatedDate(currentTime);
				}
			}

		 
		 patients  = (List<Patient>) patientDao.save(patients);
		 
		 return patients.stream()
		          .map(patient -> convertToDto(patient))
		          .collect(Collectors.toList());	
		 
		}catch(Exception  ex){  
			logger.error("PatientServiceImpl.savePatients() : Failed to save patients records  : " , ex);
			handleSaveException(ex);
		}
		return patientsDtoList;
 		 
	} 
	
	@Override
 	public List<PatientDTO> getAllPatients(String customerId,
										String firstName,
										String surName,
										String ur,
										Boolean status,
										Pageable pageable) throws ShotServiceException {
		
		List<Patient> patients = null;
		try {
			
			if(StringUtils.isEmpty(customerId))
				throw new ShotServiceException(env.getProperty("error.customerid"));
			
			patients = patientDao.searchPatients(customerId,firstName,surName, ur, status, pageable);
			
			return patients.stream()
			          .map(patient -> convertToDto(patient))
			          .collect(Collectors.toList());
			
		}catch (DataAccessException ex) {
			logger.error("PatientServiceImpl.getAllPatients() : Failed to getAllPatients : Error : " , ex);
			throw new ShotServiceException(env.getProperty("error.patient.search"));
		}
		
		
	}
	
	
	@Override
	public Long getPatientCount(String customerId,
						String firstName,
						String surName,
						String ur,
						Boolean status) throws ShotServiceException {
		
		Long patientCount = new Long(0);
		try {
			
			if(StringUtils.isEmpty(customerId))
				throw new ShotServiceException(env.getProperty("error.customerid"));
			
			patientCount = patientDao.getPatientCount(customerId,firstName,surName, ur, status);
			
			return patientCount;
			
		}catch (DataAccessException ex) {
			logger.error("PatientServiceImpl.getPatientCount() : Failed to getPatientCount : Error : " , ex);
			throw new ShotServiceException(env.getProperty("error.patient.search"));
		}
		
		
	}
	
	@Override
	public List<PatientHistoryDTO> getPatientHistory(long patientId) throws ShotServiceException {
		try { 
			
			return patientDao.getPatientHistory(patientId);
		
		}catch (DataAccessException ex) {
			logger.error("PatientServiceImpl.getPatientHistory() : Failed to getPatientHistory : Error : " , ex);
			throw new ShotServiceException(env.getProperty("error.patienthistory.search"));
		}
	}
	
	@Override
	public PatientDTO getPatient(long patientId, String customerKey) throws ShotServiceException {
		
		try {  
			Patient patient =  patientDao.findOne(patientId,customerKey);
			
			return  convertToDto(patient);
		}
		catch (Exception ex) {
			logger.error("PatientServiceImpl.getPatient() : Failed to getPatientHistory : Error : " , ex);
			throw new ShotServiceException(env.getProperty("error.patient.search"));
		}
		           
	}
	
	@Override
 	public List<PatientDTO> processPatientsFile(List<PatientDTO> patientDtoList) throws ShotServiceException { 
		
			List<Patient> patients = patientDtoList.stream()
			          .map(patientDto -> convertToEntity(patientDto))
			          .collect(Collectors.toList());

			Timestamp currentTime = new Timestamp((new java.util.Date()).getTime());						 
			
			for(int i = 0; i < patients.size(); i++) {
				patients.get(i).setCreatedDate(currentTime);
			}
			
			try{
				patientDao.save(patients);
			} catch(Exception ex){ 
				logger.error("PatientServiceImpl.processPatientsFile() : Failed to save patients records  : " , ex); 
				handleSaveException(ex); 
	 		}

			return patients.stream()
			          .map(patient -> convertToDto(patient))
			          .collect(Collectors.toList());	

	}

	@Override
 	public  List<String> validatePatientFile(List<PatientDTO> patientDtoList, String customerId, List<String> invalidRecords) throws ShotServiceException { 
		
		try{
			List<String> urs = new ArrayList();
			List<Patient> patients = patientDtoList.stream()
			          .map(patientDto -> convertToEntity(patientDto))
			          .collect(Collectors.toList());
			 
			for(int idxPatient = 0 ; idxPatient < patients.size() ; idxPatient++){
				urs.add(patients.get(idxPatient).getUr());
			}
			if(urs.size() > 0)
			{
				List<Patient> patientList = patientDao.searchPatientsWithUR(customerId, urs);
				if(null != patientList && patientList.size() != 0)
				{
					String existingPatURs = "";
					for(int idxRetPats = 0 ; idxRetPats < patientList.size(); idxRetPats++) {
						existingPatURs += patientList.get(idxRetPats).getUr() + ((idxRetPats < (patientList.size()-1))?",":"");
					}
					invalidRecords.add(env.getProperty("error.patient.upload.urExists") +" " + existingPatURs);
				}
			}
			
			return invalidRecords;

		} catch(Exception ex){ 
			logger.error("PatientServiceImpl.validatePatientFile() : Failed to validate patients records  : " , ex); 
			throw new ShotServiceException(env.getProperty("error.patient.upload.validate"));
 		}
	}
	
	@Override
	public List<PatientDTO> getPatientsFromFile(MultipartFile csvFile, String customerId) throws ShotServiceException {
		try{
			List<Patient> patients = null;
			List<String[]> csvData = csvutil.getCsvData(csvFile);
			patients = convertToPatientsData(csvData, customerId);
			return patients.stream()
			          .map(patient -> convertToDto(patient))
			          .collect(Collectors.toList());	
		}
		catch(Exception ex){ 
			logger.error("PatientServiceImpl.getPatientsFromFile() : Failed to retrieve patients records  : " , ex);
			throw new ShotServiceException(env.getProperty("error.patient.upload.validate"));
		}
		
	}

	@Override
	public List<GeneralSearchPatientDTO> generalSearchPatients(String customerKey, String searchStr) {
 		return patientDao.generalSearchPatients(customerKey, searchStr);
 	}

	private List<Patient> convertToPatientsData(List<String[]> csvData, String customerId) throws ParseException{
		
		List<Patient> patients = new ArrayList<Patient>(); 
 		 
		 SimpleDateFormat formatter = new SimpleDateFormat(env.getProperty("app.date.format"));
		 
		
			for( String[] csvRow : csvData){
			
				Date dob =  StringUtils.isNotEmpty(csvRow[4]) ? 
								 new Date(formatter.parse(csvRow[4]).getTime()) : null;
								 
//				Timestamp currentTime = new Timestamp((new java.util.Date()).getTime());						 
	
				Patient patient = new Patient();
				patient.setActive(StringUtils.isEmpty(csvRow[5]) || "Y".equals(csvRow[5])? true : false);
//				patient.setCreatedBy(0);
//				patient.setCreatedDate(currentTime);
				patient.setPatientId(StringUtils.isNotEmpty(csvRow[0]) ? Long.valueOf(csvRow[0]) : null);
				patient.setCustomerKey(customerId);
				patient.setFirstName(csvRow[2]);
				patient.setSurName(csvRow[1]);
				patient.setUr(csvRow[3]);
				patient.setDob(dob);
				
				if(StringUtils.isNotEmpty(csvRow[0])){
//					patient.setUpdatedBy(0);
//					patient.setUpdatedDate(currentTime);
				}
				
				patients.add(patient);
			}
		 
		
		return patients;
		
	}
	
	private PatientDTO convertToDto(Patient patient) {
		if(null == patient ) return null;
		else{
			PatientDTO patientDto = modelMapper.map(patient, PatientDTO.class); 
		    return patientDto;
		}
	}
	
	private Patient convertToEntity(PatientDTO patientdto) {
		if(null == patientdto ) return null;
		else{
			Patient patient = modelMapper.map(patientdto, Patient.class); 
		    return patient;
		}
	}
	
	private void handleSaveException(Exception ex) throws ShotServiceException{
		if(ex instanceof DataIntegrityViolationException){
			DataIntegrityViolationException diex = (DataIntegrityViolationException) ex;
			String errorMsg = diex.getMostSpecificCause().getMessage();
			if(StringUtils.isNotEmpty(errorMsg) 
					&& errorMsg.contains(env.getProperty("contrtaint.unique.ur"))
					&& errorMsg.contains(env.getProperty("error.duplicate.key")))
				
				throw new ShotServiceException(String.format(env.getProperty("error.unique.ur"), errorMsg.split(env.getProperty("error.duplicate.key"))[1]), ShotConstants.ErrorCodes.PATIENT_MANAGEMENT_PATIENT_SAVE_DUPLICATE_MRN);
			else
				throw new ShotServiceException(env.getProperty("error.patient.upload"));
		}else if (ex instanceof ShotServiceException){
			throw (ShotServiceException)ex;
		}
		else{
			throw new ShotServiceException(env.getProperty("error.patient.upload"));
		}
	}
	
	public String retrieveNextMRN() {
		return patientDao.retrieveNextMRN();
	}
	
	public int getPatientForUR(String ur, String customerKey) {
		int numberOfPatients = 0;
		
		numberOfPatients = patientDao.getPatientByURForCustomer(ur, customerKey);
		
		return numberOfPatients;
	}

}
