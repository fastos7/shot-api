package com.telstra.health.shot.resources;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.telstra.health.shot.dto.PatientDTO;
import com.telstra.health.shot.dto.PatientHistoryDTO;
import com.telstra.health.shot.entity.Patient;

public class DummyPatients {
	
    public static List<PatientDTO> getTestPatientsDtoList(){
    	
    	List<PatientDTO> patientsDto = new ArrayList<PatientDTO>(); 
    	patientsDto.add(DummyPatients.getTestPatientDto());
     	return patientsDto;
    }
    
    public static List<Patient> getTestPatients(){
    	
    	List<Patient> patients = new ArrayList<Patient>(); 
    	patients.add(DummyPatients.getTestPatient());
     	return patients;
    }
    
    public static Patient getTestPatient(){
   	 return new Patient((long)1,"Test_Customer_Key","FirstName","SurName", 
					   null,"UR_Test","0",null,true,0,
					  null,0,null);
    }
    
    public static PatientDTO getTestPatientDto(){
    	 return new PatientDTO(1,"Test_Customer_Key","FirstName","SurName", 
    			 			   null,"UR_Test","0",null,true,0,
    			 			  null,0,null);
     }
    
    public static PatientDTO getTestPatientDto2(){
   	 return new PatientDTO(2,"Test_Customer_Key_2","FirstName2","SurName2", 
   			null,"UR_Test2","0",null,true,0,
   			null,0,null);
    }
    
    public static List<PatientHistoryDTO> getPatientHistoryDto(){
    	PatientHistoryDTO patientHistoryDto = new PatientHistoryDTO
    												("testbatchKey", 
    												  "orderKey", "1" ,
    												  null, "product", 
    												  new BigDecimal(10), "Y", new BigDecimal(10), 
    												  "assemblyInstructions", "labelInstructions", "adminRouteCode", "New");
    	 
      	  List<PatientHistoryDTO> patientHistory = new ArrayList<PatientHistoryDTO>();
      	  patientHistory.add(patientHistoryDto);
      	  return patientHistory;
      	  
    }
    

}
