package com.telstra.health.shot.api;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.telstra.health.shot.api.exception.ApiException;
import com.telstra.health.shot.dto.GeneralSearchPatientDTO;
import com.telstra.health.shot.dto.PatientDTO;
import com.telstra.health.shot.dto.PatientHistoryDTO;
import com.telstra.health.shot.dto.PatientSearchDTO;
import com.telstra.health.shot.entity.Patient;
import com.telstra.health.shot.service.PatientService;
import com.telstra.health.shot.service.exception.ShotServiceException;
import com.telstra.health.shot.util.CSVUtil;
import com.telstra.health.shot.util.ShotConstants;

@Controller
@RequestMapping("/api/customers/{customerId}/patients/")
public class PatientsAPI {
	
	@Autowired
	private PatientService patientService;
	
    @Autowired
    private CSVUtil csvutil;
    
    @Autowired
    private Environment env;
    
    @Autowired
    private ModelMapper modelMapper;
    
    /*	PatientsApi(PatientService patientService) {
        this.patientService = patientService;
    }
	*/
	@RequestMapping(value ="/", method = RequestMethod.GET)
 	public @ResponseBody PatientSearchDTO  getAllPatients
 				(@PathVariable("customerId") String customerId ,
 				 @RequestParam(value="firstName", required=false) String firstName,
 				 @RequestParam(value="surName", required=false) String surName,
 				 @RequestParam(value="ur", required=false) String ur,
 				@RequestParam(value="status", required=false) Boolean status, Pageable pageable) {	
		
		try {
			PatientSearchDTO patientSearch = new PatientSearchDTO();
			patientSearch.setCount(patientService.getPatientCount(customerId, firstName, surName, ur, status));
			patientSearch.setPatientList(patientService.getAllPatients(customerId, firstName, surName, ur, status, pageable));
			return patientSearch;
		} catch (ShotServiceException ex) {
			throw new ApiException(ex.getMessage(), ex.getErrorCode());
		}
	}
	
	@RequestMapping(value ="/{patientId}/", method = RequestMethod.GET)
 	public @ResponseBody PatientDTO getPatient(@PathVariable("patientId") long patientId, 
 											@PathVariable("customerId") String customerId ) { 
		try {
			return patientService.getPatient(patientId, customerId);
 		} catch (ShotServiceException ex) {
			throw new ApiException(ex.getMessage());
		}
	}
	
	@RequestMapping(value ="/", method = RequestMethod.POST)
 	public ResponseEntity<List<PatientDTO>> addPatient(@RequestBody List<PatientDTO> patient) {		
		try {
			int numberOfPatients = 0;
			for(int i = 0; i < patient.size(); i++) {
				if(null != patient.get(i).getUr() && !"".equalsIgnoreCase(patient.get(i).getUr()))
				{
					numberOfPatients = patientService.getPatientForUR(patient.get(i).getUr(), patient.get(i).getCustomerKey());
				}
			}
			if(numberOfPatients > 0) {
				throw new ApiException(env.getProperty("error.patient.add.urExists"), ShotConstants.ErrorCodes.PATIENT_MANAGEMENT_PATIENT_SAVE_DUPLICATE_MRN);
			}
			List<PatientDTO>  savedPatients =patientService.savePatients(patient, ShotConstants.ACTION_INSERT); 
	        return new ResponseEntity<List<PatientDTO>>(savedPatients, HttpStatus.OK);

		} catch (ShotServiceException ex) {
			throw new ApiException(ex.getMessage(), ex.getErrorCode());
		}
	}
	
	@RequestMapping(value ="/", method = RequestMethod.PUT)
 	public ResponseEntity<List<PatientDTO>>  updatePatient(@RequestBody List<PatientDTO> patient) {		
		try {
			int numberOfPatients = 0;
			for(int i = 0; i < patient.size(); i++) {
				PatientDTO patientForId = patientService.getPatient(patient.get(i).getPatientId(), patient.get(i).getCustomerKey());
				if(!patientForId.getUr().equalsIgnoreCase(patient.get(0).getUr()) && null != patient.get(i).getUr() && !"".equalsIgnoreCase(patient.get(i).getUr()))
				{
					numberOfPatients = patientService.getPatientForUR(patient.get(i).getUr(), patient.get(i).getCustomerKey());
				}
			}
			if(numberOfPatients > 0) {
				throw new ApiException(env.getProperty("error.patient.add.urExists"), ShotConstants.ErrorCodes.PATIENT_MANAGEMENT_PATIENT_SAVE_DUPLICATE_MRN);
			}
			List<PatientDTO>  savedPatients = patientService.savePatients(patient , ShotConstants.ACTION_UPDATE);
	        return new ResponseEntity<List<PatientDTO>>(savedPatients, HttpStatus.OK);
		} catch (ShotServiceException ex) {
			throw new ApiException(ex.getMessage(), ex.getErrorCode());
		}
	}
	
	@RequestMapping(value ="/{patientId}/history/", method = RequestMethod.GET)
 	public @ResponseBody List<PatientHistoryDTO> getPatientHistory(@PathVariable("patientId") long patientId){		
		try {
			return patientService.getPatientHistory(patientId);
		} catch (ShotServiceException ex) {
			throw new ApiException(ex.getMessage());
		}
	}
	
	@RequestMapping(value ="/upload/", method = RequestMethod.POST)
 	public @ResponseBody List<PatientDTO> uploadPatients( @RequestBody List<PatientDTO> patients) {		
		  try {
/*				for(int i = 0; i < patients.size(); i++) {
					if(null == patients.get(i).getUr() || "".equalsIgnoreCase(patients.get(i).getUr()))
					{
						String nextUR = patientService.retrieveNextMRN();
						patients.get(i).setUr(nextUR);
					}
				}
*/			  
			   List<PatientDTO> addedPatients = patientService.processPatientsFile(patients);
			   return addedPatients;
		} catch (ShotServiceException ex) {
			throw new ApiException(ex.getMessage());
		}
	}
	
	@RequestMapping(value ="/validateUpload/", method = RequestMethod.POST , consumes = MediaType.MULTIPART_FORM_DATA_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
 	public @ResponseBody List<PatientDTO> validateUploadPatients( @RequestParam("file") MultipartFile csvFile , 
				@PathVariable("customerId") String customerId) {		
		try {
				List<String> invalidRecords  = csvutil.validateCsvFile(csvFile, env.getProperty("patient.template"));
				List<PatientDTO> patients = patientService.getPatientsFromFile(csvFile, customerId);
				invalidRecords = patientService.validatePatientFile(patients, customerId, invalidRecords);
				if(!CollectionUtils.isEmpty(invalidRecords))
					throw new ApiException(invalidRecords, ShotConstants.ErrorCodes.PATIENT_MANAGEMENT_VALIDATE_UPLOAD_ERRORS);
				
				return patients;

		} catch (ShotServiceException ex) {
			throw new ApiException(ex.getMessage());
		}
	}

	
	@GetMapping(value="/searches/", params= {"searchStr"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List < GeneralSearchPatientDTO > generalPatientsSearch(
			@PathVariable(name="customerId") String customerKey, 
			@RequestParam(name="searchStr", required=true) String searchStr) {
		System.out.println("Patient Search String: >>>>> " + searchStr);
		if (searchStr != null && searchStr.length() > 2) {
			return this.patientService.generalSearchPatients(customerKey, searchStr);
		} else {
			return null;
		}
	}
	
}

