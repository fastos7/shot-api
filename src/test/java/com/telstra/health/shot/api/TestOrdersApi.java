package com.telstra.health.shot.api;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telstra.health.shot.api.OrdersAPI;
import com.telstra.health.shot.api.exception.ApiExcepionMsg;
import com.telstra.health.shot.api.exception.ApiExceptionHandler;
import com.telstra.health.shot.dao.exception.DataAccessException;
import com.telstra.health.shot.dto.PatientDTO;
import com.telstra.health.shot.resources.DummyOrders;
import com.telstra.health.shot.resources.DummyPatients;
import com.telstra.health.shot.service.OrderService;
import com.telstra.health.shot.service.PatientService;
import com.telstra.health.shot.service.exception.ShotServiceException;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:global.properties")
public class TestOrdersApi {
	
	private MockMvc mockMvc;
	
	@Mock
	private Environment env;
	
	
	@InjectMocks 
	private OrdersAPI ordersApi;
	
	@Mock
    private OrderService orderService;
	
	ObjectMapper mapper = new ObjectMapper(); 
	
	
	@Before
    public void setup() throws ShotServiceException {
 	   
	   this.mockMvc = MockMvcBuilders
			   				.standaloneSetup(ordersApi)
			   				.setCustomArgumentResolvers( new PageableHandlerMethodArgumentResolver(new SortHandlerMethodArgumentResolver()))
			   				.setControllerAdvice(new ApiExceptionHandler())
			   				.build();	 
	   
	   setupMockResponses();
	   
    } 
    
	@Test
	public void testSaveOrder() throws Exception { 
	    	
		//doThrow(new ShotServiceException(env.getProperty("error.orderId"))).when(orderService).saveOrder(Mockito.any());
		  
		String jsonInString = mapper.writeValueAsString(DummyOrders.getTestOrderDto());
		//System.out.println(jsonInString);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/api/order/")
				.accept(MediaType.APPLICATION_JSON).content(jsonInString)
				.contentType(MediaType.APPLICATION_JSON);
	 	  
    	  	
    	MvcResult result = this.mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

	} 
	
	
	private void setupMockResponses() throws ShotServiceException{
		   when(orderService.saveOrder(Mockito.any())).   
				   thenReturn(true);
		  
		   
		}
	
	
	public void testSaveOrderException() throws Exception { 
	    	
		/*doThrow(new DataAccessException(env.getProperty("error.orderId"))).when(orderService).saveOrder(Mockito.any());
		  
		String jsonInString = mapper.writeValueAsString(DummyOrders.getTestOrderDto());
		//System.out.println(jsonInString);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/api/order/")
				.accept(MediaType.APPLICATION_JSON).content(jsonInString)
				.contentType(MediaType.APPLICATION_JSON);
	 	  
    	  	
    	MvcResult result = this.mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());*/
		String str = env.getProperty("contrtaint.unique.ur");
		
		String strh =System.getenv("contrtaint.unique.ur");
		
		String stre1 =env.getProperty("error.order.save");
		doThrow(new DataIntegrityViolationException("Cannot insert duplicate key row in object 'dbo.ORD_Order' with unique index 'ORD_UNIQUE_IX'. The duplicate key value is (10SYD101, <NULL>, 986667).")).when(orderService).saveOrder(Mockito.any());
		  
    	this.mockMvc.perform( post("/api/order/")
    						 .contentType(MediaType.APPLICATION_JSON_UTF8)
    						 .content(mapper.writeValueAsString(DummyOrders.getTestOrderDto())))
			    	.andDo(print())
			        .andExpect(status().isInternalServerError())
    				.andExpect(content().json(mapper.writeValueAsString(new ApiExcepionMsg(HttpStatus.INTERNAL_SERVER_ERROR, env.getProperty("error.order.save")))));

	} 

}
