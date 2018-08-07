package com.telstra.health.shot.api;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.telstra.health.shot.api.CustomerProductsAPI;
import com.telstra.health.shot.dto.ProductDTO;
import com.telstra.health.shot.service.ProductsService;

public class CustomerProductsAPITest {
	
	protected MockMvc mockMvc;
	
	@Mock
	ProductsService productsService;
	
	@InjectMocks 
	CustomerProductsAPI customerProductsApi;
	
	@Before
	public void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		
		this.mockMvc = MockMvcBuilders
					   .standaloneSetup(customerProductsApi)
					   .build();
	}

	@Test
	public void Given_ValidCustomer_WhenGettingProducts_Then_ReturnProducts() throws Exception {
		
		String customerId = "518020PSVDB02";
		String resultSet  = "CustomerPreference"; 
		
		ProductDTO product1 = new ProductDTO("Alemtuzumab",
											 "Standard",
											 "AutoBatch",
											 "73020PSVDB02",
											 "4",
											 "796MEL103",
											 null,null,null,null,"mg",
											 "Alemtuzumab",
											 "SomeKey2",
											 "SomeDSUKey2",
											 "mg",
											 "SomeDescription3",
											 "SomeKey3",
											 "SomeDSUKey3",
											 "mg",
											 "SomeDescription3");
		
		Optional<List<ProductDTO>> productList = Optional.of(Arrays.asList(product1));
		
		when(productsService.searchCustomerProducts(customerId,resultSet,product1.getProductDescription()))
			.thenReturn(productList);

		this.mockMvc.perform(get("/api/customers/"+customerId+"/products/searches")
							 .param("searchStr", product1.getProductDescription())
							 .param("resultSet", resultSet))		
					.andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))		
					.andExpect(jsonPath("$[0].productDescription", is("Alemtuzumab")))
					.andExpect(jsonPath("$[0].entryType", is("Standard")))
					.andExpect(jsonPath("$[0].processType", is("AutoBatch")))
					.andExpect(jsonPath("$[0].targetSite", is("73020PSVDB02")))
					.andExpect(jsonPath("$[0].schedule", is("4")))
					.andExpect(jsonPath("$[0].batDrugKey", is("796MEL103")))
					.andExpect(jsonPath("$[0].batDSUKey", nullValue()))
					.andExpect(jsonPath("$[0].triKey", nullValue()))
					.andExpect(jsonPath("$[0].msoIngStkKey", nullValue()))
					.andExpect(jsonPath("$[0].batFormulation", nullValue()))
					.andExpect(jsonPath("$[0].unitOfMeasure", is("mg")))
					.andExpect(jsonPath("$[0].genericDrugDescription", is("Alemtuzumab")))
					.andExpect(jsonPath("$[0].batDrugKey2", is("SomeKey2")))
					.andExpect(jsonPath("$[0].batDSUKey2", is("SomeDSUKey2")))
					.andExpect(jsonPath("$[0].unitOfMeasure2", is("mg")))
					.andExpect(jsonPath("$[0].genericDrugDescription2", is("SomeDescription3")))
					.andExpect(jsonPath("$[0].batDrugKey3", is("SomeKey3")))
					.andExpect(jsonPath("$[0].batDSUKey3", is("SomeDSUKey3")))
					.andExpect(jsonPath("$[0].unitOfMeasure3", is("mg")))
					.andExpect(jsonPath("$[0].genericDrugDescription3", is("SomeDescription3")))
					;
		
		verify(productsService,times(1)).searchCustomerProducts(customerId,resultSet,product1.getProductDescription());
		verifyNoMoreInteractions(productsService);
	}
	
	@Test
	public void Given_CustomerWithNoProducts_WhenGettingProducts_Then_ReturnEmptyProducts() throws Exception {
		
		String customerId = "CustomerWithNoProducts";
		String resultSet  = "CustomerPreference"; 
		
		Optional<List<ProductDTO>> productList = Optional.of(new ArrayList<ProductDTO>());
		
		when(productsService.searchCustomerProducts(customerId,resultSet,""))
			.thenReturn(productList);

		this.mockMvc.perform(get("/api/customers/"+customerId+"/products/searches")
							 .param("searchStr", "")
							 .param("resultSet", resultSet))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.length()",is(0)));
		
		verify(productsService,times(1)).searchCustomerProducts(customerId,resultSet,"");
		verifyNoMoreInteractions(productsService);
	}
	
}
