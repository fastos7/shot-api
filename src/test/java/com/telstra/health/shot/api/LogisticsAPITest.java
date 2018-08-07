package com.telstra.health.shot.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telstra.health.shot.api.LogisticsAPI;
import com.telstra.health.shot.common.enums.OrderType;
import com.telstra.health.shot.dto.DeliveryGroupDTO;
import com.telstra.health.shot.resources.LogisticsTestData;
import com.telstra.health.shot.service.LogisticsService;

@RunWith(SpringRunner.class) 
//@TestPropertySource(properties = {
//   "error.customerid=CustomerIdFailed",
//})
public class LogisticsAPITest {

	private MockMvc mockMvc; 
    
    @Autowired
    private Environment env;
    
    @InjectMocks
    private LogisticsAPI logisticsApi;
 
	@Mock
    private LogisticsService logisticsService;

	ObjectMapper mapper = new ObjectMapper(); 

	@Before
    public void setup() {
	   this.mockMvc = MockMvcBuilders.standaloneSetup(logisticsApi).build();
	   setupMockResponses();
	}

	@Test
	public void testGetAllPatients() throws Exception { 
	    	
    	this.mockMvc.perform(
    			get("/api/customers/CUS001/logistics/?orderType=0"))
			    	.andDo(print())
			        .andExpect(status().isOk())
			        //.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
    				//.andExpect(content().json(mapper.writeValueAsString(LogisticsTestData.getDeliveryGroup())));
			        ;
	} 

	private void setupMockResponses() {
//		when(logisticsService.calculateOrderDeliveryDateTimes(
//				"CUS001", OrderType.NON_FORMULATED, null, null))
//		.thenReturn(LogisticsTestData.getDeliveryGroup());
	}
}
