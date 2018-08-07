package com.telstra.health.shot.service;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.telstra.health.shot.config.ShotApiApplicationTests;
import com.telstra.health.shot.dao.PatientDAO;
import com.telstra.health.shot.dto.PatientDTO;
import com.telstra.health.shot.dto.PatientHistoryDTO;
import com.telstra.health.shot.resources.DummyPatients;
import com.telstra.health.shot.service.PatientServiceImpl;
import com.telstra.health.shot.service.exception.ShotServiceException;
import com.telstra.health.shot.util.ShotConstants;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes={ShotApiApplicationTests.class})
public class TestPatientServiceImpl {
	
	@Autowired
    @InjectMocks
    private PatientServiceImpl patientService ;
    
    @Mock
    private PatientDAO patientDao; 
     
    @Autowired
    private Environment env;
    
     
    @Before
    public void setup() throws ShotServiceException { 
    	
     } 
    
	@Test
	public void testGetAllPatients() throws Exception { 
		
	   when(patientDao.searchPatients
			   (Mockito.anyString(),Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyBoolean(), Mockito.any())).thenReturn(DummyPatients.getTestPatients());
	   
	   List<PatientDTO> patientsData = patientService.getAllPatients("1", "firstName", "surName", "ur", true, new PageRequest(0, 10));
	   
	   Assert.assertNotNull(patientsData);
	   Assert.assertEquals(patientsData, DummyPatients.getTestPatientsDtoList()); 
	   
	   try{
		   patientService.getAllPatients(null, "firstName", "surName", "ur", true, new PageRequest(0, 10)); 
	  }catch(ShotServiceException ex){
		  Assert.assertEquals(env.getProperty("error.customerid"), ex.getMessage());
	  } 
 	} 
	
	@Test
	public void testGetPatientHistory() throws Exception { 
		
	   when(patientDao.getPatientHistory(1)).thenReturn(DummyPatients.getPatientHistoryDto());
	   
	   List<PatientHistoryDTO> patientHistoryData = patientService.getPatientHistory(1);
	   
	   Assert.assertNotNull(patientHistoryData);
	   Assert.assertEquals(patientHistoryData, DummyPatients.getPatientHistoryDto());  
 	} 
	
	
	@Test
	public void testSavePatients() throws Exception { 
		
 	  when(patientDao.save(DummyPatients.getTestPatients())).thenReturn(DummyPatients.getTestPatients()); 
	  patientService.savePatients(DummyPatients.getTestPatientsDtoList(), ShotConstants.ACTION_UPDATE);
	 
 	} 
	
	@Test
	public void testSavePatientsException() throws Exception { 
		
 	  doThrow(new DataIntegrityViolationException("Violation of UNIQUE KEY constraint 'UNQ_CUSTOMER_UR'. Cannot insert duplicate key in object 'dbo.SHOT_Patients'. The duplicate key value is (1, 1234)"))
	  .when(patientDao).save(DummyPatients.getTestPatients()); 

	  try{
		  patientService.savePatients(DummyPatients.getTestPatientsDtoList(), ShotConstants.ACTION_INSERT);
	  }catch(ShotServiceException ex){
		  Assert.assertEquals("The combination (CustomerKey, UR),(1, 1234) is already in use . Please enter a different one", ex.getMessage());
	  } 
	 
 	}
	 
	@Test
	public void testGetgetPatient() throws Exception { 
		
	  when(patientDao.findOne(Mockito.anyLong(),Mockito.anyString())).thenReturn(DummyPatients.getTestPatient()); 
	  
	  PatientDTO  patientDto  =  patientService.getPatient(1, "customerKey");
 
	  Assert.assertNotNull(patientDto);
	  Assert.assertEquals(patientDto, DummyPatients.getTestPatientDto());  
	 
	}


}
