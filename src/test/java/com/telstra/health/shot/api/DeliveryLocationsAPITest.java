package com.telstra.health.shot.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

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
import com.telstra.health.shot.api.DeliveryLocationsAPI;
import com.telstra.health.shot.dto.DeliveryLocationDTO;
import com.telstra.health.shot.resources.LogisticsTestData;
import com.telstra.health.shot.service.LogisticsService;

@RunWith(SpringRunner.class)
public class DeliveryLocationsAPITest {

	private MockMvc mockMvc; 
    
    @InjectMocks
    private DeliveryLocationsAPI api;
 
	@Mock
    private LogisticsService logisticsService;

	ObjectMapper mapper = new ObjectMapper(); 

	@Before
    public void setup() {
	   this.mockMvc = MockMvcBuilders.standaloneSetup(api).build();
	   //setupMockResponses();
	}

	@Test
	public void testGetAllDeliveryLocationsByCustomer() throws Exception {
		List < DeliveryLocationDTO > dtoList = Arrays.asList(new DeliveryLocationDTO("CUS01", "Bellarat ER"));
		this.mockMvc.perform(
    			get("/api/customers/CUS01/deliveryLocations/"))
			    	.andDo(print())
			        .andExpect(status().isOk())
			        //.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
    				//.andExpect(content().json(mapper.writeValueAsString(LogisticsTestData.getDeliveryDateTimes())));
			        ;
	}
}
