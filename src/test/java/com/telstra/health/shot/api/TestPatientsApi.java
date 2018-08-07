package com.telstra.health.shot.api;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telstra.health.shot.api.PatientsAPI;
import com.telstra.health.shot.api.exception.ApiExcepionMsg;
import com.telstra.health.shot.api.exception.ApiExceptionHandler;
import com.telstra.health.shot.config.ShotApiApplicationTests;
import com.telstra.health.shot.dto.PatientDTO;
import com.telstra.health.shot.resources.DummyPatients;
import com.telstra.health.shot.service.PatientService;
import com.telstra.health.shot.service.exception.ShotServiceException;
import com.telstra.health.shot.util.ShotConstants;


 
@RunWith(SpringRunner.class) 
 @TestPropertySource(properties = {
    "error.customerid=CustomerIdFailed",
})
public class TestPatientsApi {
	
	private MockMvc mockMvc; 
	    
    @Autowired
    private Environment env;
    
    @InjectMocks
    private PatientsAPI patientsApi;
 
	@Mock
    private PatientService patientService;
	
	ObjectMapper mapper = new ObjectMapper(); 
	
   @Before
    public void setup() throws ShotServiceException {
 	   
	   this.mockMvc = MockMvcBuilders
			   				.standaloneSetup(patientsApi)
			   				.setCustomArgumentResolvers( new PageableHandlerMethodArgumentResolver(new SortHandlerMethodArgumentResolver()))
			   				.setControllerAdvice(new ApiExceptionHandler())
			   				.build();	 
	   
	   setupMockResponses();
	   
    } 
    
	@Test
	public void testGetAllPatients() throws Exception { 
	    	
	    	this.mockMvc.perform(get("/api/customers/1/patients/"))
				    	.andDo(print())
				        .andExpect(status().isOk())
				        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
	    				.andExpect(content().json(mapper.writeValueAsString(DummyPatients.getTestPatientsDtoList()))); 
	} 
	  
	@Test
	public void testGetPatient() throws Exception { 
	    	
		this.mockMvc.perform(get("/api/customers/1/patients/1/"))
			    	.andDo(print())
			        .andExpect(status().isOk())
			        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(content().json(mapper.writeValueAsString(DummyPatients.getTestPatientDto()))); 
	} 
	  
	@Test
	public void testGetPatientHistory() throws Exception { 
	    	
	    	this.mockMvc.perform(get("/api/customers/1/patients/1/history"))
				    	.andDo(print())
				        .andExpect(status().isOk())
				        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
	    				.andExpect(content().json(mapper.writeValueAsString(DummyPatients.getPatientHistoryDto()))); 
	} 
	  
	  @Test
	  public void testAddPatients() throws Exception { 
	    	
		    doNothing().when(patientService).savePatients(Mockito.any(), ShotConstants.ACTION_INSERT);
		  
	    	this.mockMvc.perform( post("/api/customers/1/patients/")
	    						 .contentType(MediaType.APPLICATION_JSON_UTF8)
	    						 .content(mapper.writeValueAsString(DummyPatients.getTestPatientsDtoList())))
				    	.andDo(print())
				        .andExpect(status().isCreated());
	   } 
	  
	  @Test
	  public void testAddPatientsException() throws Exception { 
	    	
		  	doThrow(new ShotServiceException(env.getProperty("error.customerid"))).when(patientService).savePatients(Mockito.any(), ShotConstants.ACTION_INSERT);
		  
	    	this.mockMvc.perform( post("/api/customers/1/patients/")
	    						 .contentType(MediaType.APPLICATION_JSON_UTF8)
	    						 .content(mapper.writeValueAsString(DummyPatients.getTestPatientsDtoList())))
				    	.andDo(print())
				        .andExpect(status().isInternalServerError())
	    				.andExpect(content().json(mapper.writeValueAsString(new ApiExcepionMsg(HttpStatus.INTERNAL_SERVER_ERROR, env.getProperty("error.customerid")))));
	
	   } 
	  
	  @Test
	  public void testUpdatePatients() throws Exception { 
	    	
		    doNothing().when(patientService).savePatients(Mockito.any(), ShotConstants.ACTION_UPDATE);
		  
	    	this.mockMvc.perform( put("/api/customers/1/patients/")
	    						 .contentType(MediaType.APPLICATION_JSON_UTF8)
	    						 .content(mapper.writeValueAsString(DummyPatients.getTestPatientsDtoList())))
				    	.andDo(print())
				        .andExpect(status().isOk());
	   } 
	  
	  @Test
	  public void testUpdatePatientsException() throws Exception { 
	    	
		  	doThrow(new ShotServiceException(env.getProperty("error.customerid"))).when(patientService).savePatients(Matchers.anyListOf(PatientDTO.class), ShotConstants.ACTION_UPDATE);
		  
	    	this.mockMvc.perform( post("/api/customers/1/patients/")
	    						 .contentType(MediaType.APPLICATION_JSON_UTF8)
	    						 .content(mapper.writeValueAsString(DummyPatients.getTestPatientsDtoList())))
				    	.andDo(print())
				        .andExpect(status().isInternalServerError())
	    				.andExpect(content().json(mapper.writeValueAsString(new ApiExcepionMsg(HttpStatus.INTERNAL_SERVER_ERROR, env.getProperty("error.customerid")))));
	
	   } 
  
		private void setupMockResponses() throws ShotServiceException{
		   when(patientService.getAllPatients( Mockito.any(),Mockito.any(), Mockito.any(), Mockito.any(), Mockito.anyBoolean(), Mockito.any())).thenReturn(DummyPatients.getTestPatientsDtoList());
		  // when(patientService.getAllPatients( Mockito.contains(StringUtils.EMPTY),Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.any())).thenThrow(new ShotServiceException(env.getProperty("exception.customerid.null")));
		   when(patientService.getPatient(Mockito.anyLong(), Mockito.anyString())).thenReturn(DummyPatients.getTestPatientDto());
		   when(patientService.getPatientHistory(1)).thenReturn(DummyPatients.getPatientHistoryDto()); 
		}
  	
  
  
  
    
}
