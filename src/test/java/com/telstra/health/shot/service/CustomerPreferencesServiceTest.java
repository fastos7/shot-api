package com.telstra.health.shot.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import com.telstra.health.shot.dao.CustomerPreferencesDAO;
import com.telstra.health.shot.dto.CustomerPreferenceDTO;
import com.telstra.health.shot.entity.CustomerPreference;
import com.telstra.health.shot.service.CustomerPreferencesService;
import com.telstra.health.shot.service.CustomerPreferencesServiceImpl;

public class CustomerPreferencesServiceTest {
	
	@InjectMocks
	CustomerPreferencesService customerPreferencesService = new CustomerPreferencesServiceImpl();
	
	@Mock
	CustomerPreferencesDAO customerPreferencesRepository;
	
	@Mock 
	ModelMapper modelMapper;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	// Test only for negative path
	@Test
	public void Given_ValidCustomerAndProductWithNoPreferences_WhenGettingPreferences_Then_ReturnEmpty() throws Exception {
		
		String customerKey = RandomStringUtils.random(20);
		String productDescription = RandomStringUtils.random(100);
		String productType = RandomStringUtils.random(20);
		String batDrugKey = RandomStringUtils.random(20);
		String batDSUKey = RandomStringUtils.random(20);
		String triKey = RandomStringUtils.random(20);
		String msoIngStkKey = RandomStringUtils.random(20);
		String batFormulation = RandomStringUtils.random(20);
		
		List<CustomerPreference> preferences = new ArrayList<CustomerPreference>();
		
		when(customerPreferencesRepository.searchCustomerPreferences(customerKey,
																	  productDescription, 
																	  productType,
																	  batDrugKey, 
																	  batDSUKey, 
																	  triKey, 
																	  msoIngStkKey, 
																	  batFormulation))
			.thenReturn(preferences);
		
		List<CustomerPreferenceDTO> customerPreferenceDTOs 
			= this.customerPreferencesService.searchCustomerPreferences(customerKey,
																	    productDescription, 
																	    productType,
																	    batDrugKey, 
																	    batDSUKey, 
																	    triKey, 
																	    msoIngStkKey, 
																	    batFormulation);
		
		assertThat(customerPreferenceDTOs.size(),is(0));

	}
	
	@Test
	public void Given_CustomerPreferencesWithMissingFirstRank_WhenComputingRanks_ThenReturn() {
		
		CustomerPreference cp1 = new CustomerPreference();
		cp1.setRank(2);
		
		CustomerPreference cp2 = new CustomerPreference();
		cp2.setRank(3);
		
		CustomerPreference cp3 = new CustomerPreference();
		cp3.setRank(4);     
		
		List<CustomerPreference> cps = Arrays.asList(cp1,cp2,cp3);
		
		boolean hasChanged = this.customerPreferencesService.recomputeRanks(cps);
		
		assertThat(hasChanged,is(true));
		assertThat(cp1.getRank(),is(1));
		assertThat(cp2.getRank(),is(2));
		assertThat(cp3.getRank(),is(3));
		
	}
	
	@Test
	public void Given_CustomerPreferencesWithMissingMiddleRank_WhenComputingRanks_ThenReturn() {
		
		CustomerPreference cp1 = new CustomerPreference();
		cp1.setRank(1);
		
		CustomerPreference cp2 = new CustomerPreference();
		cp2.setRank(3);
		
		CustomerPreference cp3 = new CustomerPreference();
		cp3.setRank(4);     
		
		List<CustomerPreference> cps = Arrays.asList(cp1,cp2,cp3);
		
		boolean hasChanged = this.customerPreferencesService.recomputeRanks(cps);
		
		assertThat(hasChanged,is(true));		
		assertThat(cp1.getRank(),is(1));
		assertThat(cp2.getRank(),is(2));
		assertThat(cp3.getRank(),is(3));
		
	}
	
	@Test
	public void Given_CustomerPreferencesWithOneRank_WhenComputingRanks_ThenReturn() {
		
		CustomerPreference cp1 = new CustomerPreference();
		cp1.setRank(1);
		
		List<CustomerPreference> cps = Arrays.asList(cp1);
		
		boolean hasChanged = this.customerPreferencesService.recomputeRanks(cps);
		
		assertThat(hasChanged,is(false));		
		assertThat(cp1.getRank(),is(1));
		
	}

	@Test
	public void Given_CustomerPreferencesWithOrderedRank_WhenComputingRanks_ThenReturn() {
		
		CustomerPreference cp1 = new CustomerPreference();
		cp1.setRank(1);
		
		CustomerPreference cp2 = new CustomerPreference();
		cp2.setRank(2);
		
		CustomerPreference cp3 = new CustomerPreference();
		cp3.setRank(3);     
		
		List<CustomerPreference> cps = Arrays.asList(cp1,cp2,cp3);
		
		boolean hasChanged = this.customerPreferencesService.recomputeRanks(cps);
		
		assertThat(hasChanged,is(false));		
		assertThat(cp1.getRank(),is(1));
		assertThat(cp2.getRank(),is(2));
		assertThat(cp3.getRank(),is(3));
		
	}
}
