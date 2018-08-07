package com.telstra.health.shot.api;

import static com.telstra.health.shot.util.TestUtil.asJsonString;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.telstra.health.shot.api.CustomerPreferencesAPI;
import com.telstra.health.shot.dto.AdministrationRouteDTO;
import com.telstra.health.shot.dto.ContainerDTO;
import com.telstra.health.shot.dto.CustomerPreferenceDTO;
import com.telstra.health.shot.dto.DeliveryMechanismsDTO;
import com.telstra.health.shot.dto.DiluentDTO;
import com.telstra.health.shot.service.CustomerPreferencesService;
import com.telstra.health.shot.util.TestUtil;

public class CustomerPreferencesAPITest {

	protected MockMvc mockMvc;

	@Mock
	CustomerPreferencesService customerPreferencesService;
	
	@InjectMocks
	CustomerPreferencesAPI customerProductPreferencesApi;

	@Before	
	public void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);

		this.mockMvc = MockMvcBuilders.standaloneSetup(customerProductPreferencesApi)
									  .build();
	}

	@Test
	public void Given_ValidCustomerAndProduct_WhenSearchingPreferences_Then_ReturnCustomerPreferences() throws Exception {
		
		
		DiluentDTO diluentDTO = new DiluentDTO(RandomStringUtils.random(20),
											   RandomStringUtils.random(100),
											   RandomStringUtils.random(20));
		
		ContainerDTO containerDTO = new ContainerDTO(RandomStringUtils.random(20),
												     RandomStringUtils.random(100),
												     RandomStringUtils.random(20));
		
		AdministrationRouteDTO adminRouteDTO = new AdministrationRouteDTO(RandomStringUtils.random(50),
																		  RandomStringUtils.random(50),
																		  RandomStringUtils.random(50));
		
		CustomerPreferenceDTO preference = new CustomerPreferenceDTO();
		preference.setPrefId(1);
		preference.setCustomerKey(RandomStringUtils.random(20));
		preference.setCustomerName(RandomStringUtils.random(100));
		preference.setProductDescription(RandomStringUtils.random(100));
		preference.setProductType(RandomStringUtils.random(20));
		preference.setBatDrugKey(RandomStringUtils.random(20));
		preference.setBatDSUKey(RandomStringUtils.random(20));
		preference.setTriKey(RandomStringUtils.random(20));
		preference.setMsoIngStkKey(RandomStringUtils.random(20));
		preference.setBatFormulation(RandomStringUtils.random(20));
		preference.setDoseFrom(10.0);
		preference.setDoseTo(11.0);
		preference.setUnitOfMeasure("mg");
		preference.setDeliveryMechanism(new DeliveryMechanismsDTO(RandomStringUtils.random(20),diluentDTO,containerDTO,RandomStringUtils.random(20)));
		preference.setVolume(10.0);
		preference.setExact('t');
		preference.setQuantity(10);
		preference.setAdministrationRoute(adminRouteDTO);
		preference.setInfusionDuration(1.0);
		preference.setRank(1);
		preference.setComments(RandomStringUtils.random(100));
		preference.setCreatedBy(1);
		preference.setCreatedDate(Timestamp.from(Instant.now()));
		preference.setUpdatedBy(1);
		preference.setUpdatedDate(Timestamp.from(Instant.now()));
		
		List<CustomerPreferenceDTO> preferences = Arrays.asList(preference);
		
		String customerKey = RandomStringUtils.random(20);
		String productDescription = RandomStringUtils.random(100);
		String productType = RandomStringUtils.random(20);
		String batDrugKey = RandomStringUtils.random(20);
		String batDSUKey = RandomStringUtils.random(20);
		String triKey = RandomStringUtils.random(20);
		String msoIngStkKey = RandomStringUtils.random(20);
		String batFormulation = RandomStringUtils.random(20);
		
		when(customerPreferencesService.searchCustomerPreferences(customerKey,
																  productDescription, 
																  productType,
																  batDrugKey, 
																  batDSUKey, 
																  triKey, 
																  msoIngStkKey, 
																  batFormulation))
			.thenReturn(preferences);
		
		this.mockMvc.perform(get("/api/customers/"+customerKey+"/preferences/searches/")
							.param("productDescription", productDescription)
							.param("productType", productType)
							.param("batDrugKey", batDrugKey)
							.param("batDSUKey", batDSUKey)
							.param("triKey", triKey)
							.param("msoIngStkKey", msoIngStkKey)
							.param("batFormulation", batFormulation)
							.contentType(MediaType.APPLICATION_JSON_UTF8))
					.andDo(print())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))					
					.andExpect(status().isOk())					
					.andExpect(jsonPath("$.length()", is(1)))
					.andExpect(jsonPath("$[0].prefId", is(preference.getPrefId())))
					.andExpect(jsonPath("$[0].customerKey", is(preference.getCustomerKey())))
					.andExpect(jsonPath("$[0].customerName", is(preference.getCustomerName())))
					.andExpect(jsonPath("$[0].productDescription", is(preference.getProductDescription())))					
					.andExpect(jsonPath("$[0].productType", is(preference.getProductType())))					
					.andExpect(jsonPath("$[0].batDrugKey", is(preference.getBatDrugKey())))
					.andExpect(jsonPath("$[0].batDSUKey", is(preference.getBatDSUKey())))
					.andExpect(jsonPath("$[0].triKey", is(preference.getTriKey())))
					.andExpect(jsonPath("$[0].msoIngStkKey", is(preference.getMsoIngStkKey())))
					.andExpect(jsonPath("$[0].batFormulation", is(preference.getBatFormulation())))					
					.andExpect(jsonPath("$[0].doseFrom", is(preference.getDoseFrom())))
					.andExpect(jsonPath("$[0].doseTo", is(preference.getDoseTo())))
					.andExpect(jsonPath("$[0].unitOfMeasure", is(preference.getUnitOfMeasure())))					
					.andExpect(jsonPath("$[0].deliveryMechanism.key", is(preference.getDeliveryMechanism().getKey())))
					.andExpect(jsonPath("$[0].deliveryMechanism.diluent.stockKey", is(preference.getDeliveryMechanism().getDiluent().getStockKey())))
					.andExpect(jsonPath("$[0].deliveryMechanism.diluent.stockDescription", is(preference.getDeliveryMechanism().getDiluent().getStockDescription())))
					.andExpect(jsonPath("$[0].deliveryMechanism.diluent.stockCode", is(preference.getDeliveryMechanism().getDiluent().getStockCode())))
					.andExpect(jsonPath("$[0].deliveryMechanism.container.stockKey", is(preference.getDeliveryMechanism().getContainer().getStockKey())))
					.andExpect(jsonPath("$[0].deliveryMechanism.container.stockDescription", is(preference.getDeliveryMechanism().getContainer().getStockDescription())))
					.andExpect(jsonPath("$[0].deliveryMechanism.container.stockCode", is(preference.getDeliveryMechanism().getContainer().getStockCode())))					
					.andExpect(jsonPath("$[0].volume", is(preference.getVolume())))
					.andExpect(jsonPath("$[0].exact", is(String.valueOf(preference.getExact()))))
					.andExpect(jsonPath("$[0].quantity", is(preference.getQuantity())))
					.andExpect(jsonPath("$[0].administrationRoute.code", is(preference.getAdministrationRoute().getCode())))
					.andExpect(jsonPath("$[0].administrationRoute.codeType", is(preference.getAdministrationRoute().getCodeType())))
					.andExpect(jsonPath("$[0].administrationRoute.description", is(preference.getAdministrationRoute().getDescription())))
					.andExpect(jsonPath("$[0].infusionDuration", is(preference.getInfusionDuration())))
					.andExpect(jsonPath("$[0].rank", is(preference.getRank())))
					.andExpect(jsonPath("$[0].comments", is(preference.getComments())))
					.andExpect(jsonPath("$[0].createdBy", is(preference.getCreatedBy())))
					.andExpect(jsonPath("$[0].createdDate", is(TestUtil.formatTimestampInUTC(preference.getCreatedDate()))))
					.andExpect(jsonPath("$[0].updatedBy", is(preference.getUpdatedBy())))
					.andExpect(jsonPath("$[0].updatedDate", is(TestUtil.formatTimestampInUTC(preference.getUpdatedDate()))));
		
		
		verify(customerPreferencesService,times(1)).searchCustomerPreferences(customerKey,
																			  productDescription, 
																			  productType,
																			  batDrugKey, 
																			  batDSUKey, 
																			  triKey, 
																			  msoIngStkKey, 
																			  batFormulation);
		verifyNoMoreInteractions(customerPreferencesService);
		
	}
	
	@Test
	public void Given_ValidCustomerAndProductButNoPreferences_WhenGettingPreferences_Then_ReturnEmptyCustomerPreferences() throws Exception {
		
		String customerKey = RandomStringUtils.random(20);
		String productDescription = RandomStringUtils.random(100);
		String productType = RandomStringUtils.random(20);
		String batDrugKey = RandomStringUtils.random(20);
		String batDSUKey = RandomStringUtils.random(20);
		String triKey = RandomStringUtils.random(20);
		String msoIngStkKey = RandomStringUtils.random(20);
		String batFormulation = RandomStringUtils.random(20);
		
		List<CustomerPreferenceDTO> preferences = new ArrayList<CustomerPreferenceDTO>();
		
		when(customerPreferencesService.searchCustomerPreferences(customerKey,
																  productDescription, 
																  productType,
																  batDrugKey, 
																  batDSUKey, 
																  triKey, 
																  msoIngStkKey, 
																  batFormulation))
			.thenReturn(preferences);
		
		this.mockMvc.perform(get("/api/customers/"+customerKey+"/preferences/searches/")
							.param("productDescription", productDescription)
							.param("productType", productType)
							.param("batDrugKey", batDrugKey)
							.param("batDSUKey", batDSUKey)
							.param("triKey", triKey)
							.param("msoIngStkKey", msoIngStkKey)
							.param("batFormulation", batFormulation)
							.contentType(MediaType.APPLICATION_JSON_UTF8))
					.andDo(print())										
					.andExpect(status().isNotFound());
		
		verify(customerPreferencesService,times(1)).searchCustomerPreferences(customerKey,
																			  productDescription, 
																			  productType,
																			  batDrugKey, 
																			  batDSUKey, 
																			  triKey, 
																			  msoIngStkKey, 
																			  batFormulation);
		verifyNoMoreInteractions(customerPreferencesService);
		
	}
	
	@Test
	public void Given_ValidCustomerAndProduct_WhenCreatingPreferences_Then_CreatePrefernce() throws Exception {
		
//		CustomerPreferenceDTO customerPreferenceTobeCreated= new CustomerPreferenceDTO();
//		CustomerPreferenceDTO customerPreferenceCreated= new CustomerPreferenceDTO();
//		customerPreferenceCreated.setPrefId(1);
//		
//		when(customerPreferencesService.createCustomerPreference(customerPreferenceTobeCreated))
//			.thenReturn(customerPreferenceCreated);
//		  
//		this.mockMvc.perform(post("/api/customers/1/products/1/preferences/")
//							 .content(asJsonString(customerPreferenceTobeCreated))
//							 .contentType(MediaType.APPLICATION_JSON_UTF8))
//					.andDo(print())
//					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))					
//					.andExpect(status().isCreated())
//					.andExpect(jsonPath("$.prefId", is(customerPreferenceCreated.getPrefId())));					
		
		
	}
	
}
