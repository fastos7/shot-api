package com.telstra.health.shot.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;

import com.telstra.health.shot.dao.ProductsDAO;
import com.telstra.health.shot.dao.exception.DataAccessException;
import com.telstra.health.shot.dto.ProductDTO;
import com.telstra.health.shot.entity.AdministrationRoute;
import com.telstra.health.shot.entity.ProductAdministrationRoutes;
import com.telstra.health.shot.service.exception.ShotServiceException;

public class ProductsServiceImplTest {
		
	@InjectMocks
	ProductsService productsService = new ProductsServiceImpl();
	
	@Mock
	ProductsDAO productsDAO;
	
	@Mock 
	ModelMapper modelMapper;
	
	@Mock
	Environment env;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void Given_ValidCustomer_WhenGettingProducts_Then_ReturnProducts() throws Exception {
		
		String customerId = "518020PSVDB02";
		String resultSet  = "CustomerPreference";
		
		
		ProductDTO productDTO = new ProductDTO("Alemtuzumab",
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
										
		List<ProductDTO> dummyProductList = Arrays.asList(productDTO);
		
		when(productsDAO.searchCustomerProducts(customerId,resultSet,productDTO.getProductDescription()))
			.thenReturn(dummyProductList);
		
		Optional<List<ProductDTO>> productList = this.productsService.searchCustomerProducts(customerId,resultSet,productDTO.getProductDescription());
		
		assertThat(productList.get(),notNullValue());
		assertThat(productList.get().get(0).getProductDescription(),is("Alemtuzumab"));
		assertThat(productList.get().get(0).getEntryType(),is("Standard"));
		assertThat(productList.get().get(0).getProcessType(),is("AutoBatch"));
		assertThat(productList.get().get(0).getTargetSite(),is("73020PSVDB02"));
		assertThat(productList.get().get(0).getSchedule(),is("4"));
		assertThat(productList.get().get(0).getBatDrugKey(),is("796MEL103"));
		assertThat(productList.get().get(0).getBatDSUKey(),nullValue());
		assertThat(productList.get().get(0).getTriKey(),nullValue());
		assertThat(productList.get().get(0).getMsoIngStkKey(),nullValue());
		assertThat(productList.get().get(0).getBatFormulation(),nullValue());
		assertThat(productList.get().get(0).getUnitOfMeasure(),is("mg"));
		assertThat(productList.get().get(0).getGenericDrugDescription(),is("Alemtuzumab"));
		assertThat(productList.get().get(0).getBatDrugKey2(),is("SomeKey2"));
		assertThat(productList.get().get(0).getBatDSUKey2(),is("SomeDSUKey2"));
		assertThat(productList.get().get(0).getUnitOfMeasure2(),is("mg"));
		assertThat(productList.get().get(0).getGenericDrugDescription2(),is("SomeDescription3"));
		assertThat(productList.get().get(0).getBatDrugKey3(),is("SomeKey3"));
		assertThat(productList.get().get(0).getBatDSUKey3(),is("SomeDSUKey3"));
		assertThat(productList.get().get(0).getUnitOfMeasure3(),is("mg"));
		assertThat(productList.get().get(0).getGenericDrugDescription3(),is("SomeDescription3"));
		
	}

	@Test
	public void Given_CustomerWithNoProducts_WhenGettingProducts_Then_ReturnEmptyProducts() throws Exception {
		
		String customerId = "518020PSVDB02";
		String resultSet  = "CustomerPreference";
		
		List<ProductDTO> dummyProductList = new ArrayList<ProductDTO>();
		
		when(productsDAO.searchCustomerProducts(customerId,resultSet,""))
			.thenReturn(dummyProductList);
		
		Optional<List<ProductDTO>> productList = this.productsService.searchCustomerProducts(customerId,resultSet,"");
		
		assertThat(productList.isPresent(),is(true));
		assertThat(productList.get().size(),is(0));
		
	}	
	
	@Test
	public void Given_Customer_WhenGettingProductsEncounterException_Then_ReturnException() throws Exception {
		
		String customerId = "518020PSVDB02";
		String resultSet  = "CustomerPreference";
		
		String errorMessage = "Failed to get customer products. Please try again.";
		
		when(env.getProperty("error.customer.products.exception"))
			.thenReturn("Failed to get customer products. Please try again.");
		
		doThrow(new DataAccessException("Some exception"))
			.when(productsDAO).searchCustomerProducts(customerId,resultSet,"");
		
		try {
			this.productsService.searchCustomerProducts(customerId,resultSet,"");
		} catch (ShotServiceException e) {
			assertThat(e.getMessage(),is(errorMessage));
		}
		
		
	}	
	
	@Test
	public void Given_ListOfAdministrationRoutes_test1_WhenGettingCommon_ShouldReturnCommon() {
		
		
		List<ProductAdministrationRoutes> adminRoutes1 = null;
		
		
		List<ProductAdministrationRoutes> adminRoutes2 = null;
		
		
		List<ProductAdministrationRoutes> commonAdministrationRoutes = this.productsService.findCommonAdministrationRoutes(adminRoutes1,adminRoutes2);
		
		assertThat(commonAdministrationRoutes,is(nullValue()));
		
	}
	
	@Test
	public void Given_ListOfAdministrationRoutes_test2_WhenGettingCommon_ShouldReturnCommon() {
		
		
		List<ProductAdministrationRoutes> adminRoutes1 = null;
		
		AdministrationRoute route2 = new AdministrationRoute();
		route2.setCode("TACE");
		route2.setCodeType("ADMINROUTE");
		route2.setDescription("INTRA-ARTERIAL (TACE)");
		
		AdministrationRoute route3 = new AdministrationRoute();
		route3.setCode("V");
		route3.setCodeType("ADMINROUTE");
		route3.setDescription("Intravenous");
		
		ProductAdministrationRoutes par20 = new ProductAdministrationRoutes(); 
		par20.setKey("KEY20");
		par20.setAdministrationRoute(route2);
		
		ProductAdministrationRoutes par21 = new ProductAdministrationRoutes();
		par21.setKey("KEY21");
		par21.setAdministrationRoute(route3);
		
		List<ProductAdministrationRoutes> adminRoutes2 = new ArrayList<>();
		adminRoutes2.add(par20);
		adminRoutes2.add(par21);
		
		
		List<ProductAdministrationRoutes> commonAdministrationRoutes = this.productsService.findCommonAdministrationRoutes(adminRoutes1,adminRoutes2);
		
		assertThat(commonAdministrationRoutes.size(),is(2));
		
		assertThat(commonAdministrationRoutes.get(0),is(notNullValue()));
		assertThat(commonAdministrationRoutes.get(0).getAdministrationRoute(),is(notNullValue()));
		assertThat(commonAdministrationRoutes.get(0).getAdministrationRoute().getCode(),is("TACE"));
		assertThat(commonAdministrationRoutes.get(0).getAdministrationRoute().getCodeType(),is("ADMINROUTE"));
		assertThat(commonAdministrationRoutes.get(0).getAdministrationRoute().getDescription(),is("INTRA-ARTERIAL (TACE)"));
		
		assertThat(commonAdministrationRoutes.get(1),is(notNullValue()));
		assertThat(commonAdministrationRoutes.get(1).getAdministrationRoute(),is(notNullValue()));
		assertThat(commonAdministrationRoutes.get(1).getAdministrationRoute().getCode(),is("V"));
		assertThat(commonAdministrationRoutes.get(1).getAdministrationRoute().getCodeType(),is("ADMINROUTE"));
		assertThat(commonAdministrationRoutes.get(1).getAdministrationRoute().getDescription(),is("Intravenous"));
		
	}
	
	@Test
	public void Given_ListOfAdministrationRoutes_test3_WhenGettingCommon_ShouldReturnCommon() {
		
		
		AdministrationRoute route2 = new AdministrationRoute();
		route2.setCode("TACE");
		route2.setCodeType("ADMINROUTE");
		route2.setDescription("INTRA-ARTERIAL (TACE)");
		
		AdministrationRoute route3 = new AdministrationRoute();
		route3.setCode("V");
		route3.setCodeType("ADMINROUTE");
		route3.setDescription("Intravenous");
		
		ProductAdministrationRoutes par20 = new ProductAdministrationRoutes(); 
		par20.setKey("KEY20");
		par20.setAdministrationRoute(route2);
		
		ProductAdministrationRoutes par21 = new ProductAdministrationRoutes();
		par21.setKey("KEY21");
		par21.setAdministrationRoute(route3);
		
		List<ProductAdministrationRoutes> adminRoutes1 = new ArrayList<>();
		adminRoutes1.add(par20);
		adminRoutes1.add(par21);
		
		List<ProductAdministrationRoutes> adminRoutes2 = null;
		
		
		List<ProductAdministrationRoutes> commonAdministrationRoutes = this.productsService.findCommonAdministrationRoutes(adminRoutes1,adminRoutes2);
		
		assertThat(commonAdministrationRoutes.size(),is(2));
		
		assertThat(commonAdministrationRoutes.get(0),is(notNullValue()));
		assertThat(commonAdministrationRoutes.get(0).getAdministrationRoute(),is(notNullValue()));
		assertThat(commonAdministrationRoutes.get(0).getAdministrationRoute().getCode(),is("TACE"));
		assertThat(commonAdministrationRoutes.get(0).getAdministrationRoute().getCodeType(),is("ADMINROUTE"));
		assertThat(commonAdministrationRoutes.get(0).getAdministrationRoute().getDescription(),is("INTRA-ARTERIAL (TACE)"));
		
		assertThat(commonAdministrationRoutes.get(1),is(notNullValue()));
		assertThat(commonAdministrationRoutes.get(1).getAdministrationRoute(),is(notNullValue()));
		assertThat(commonAdministrationRoutes.get(1).getAdministrationRoute().getCode(),is("V"));
		assertThat(commonAdministrationRoutes.get(1).getAdministrationRoute().getCodeType(),is("ADMINROUTE"));
		assertThat(commonAdministrationRoutes.get(1).getAdministrationRoute().getDescription(),is("Intravenous"));
		
	}
	
	@Test
	public void Given_ListOfAdministrationRoutes_test4_WhenGettingCommon_ShouldReturnCommon() {
		
		
		AdministrationRoute route1 = new AdministrationRoute();
		route1.setCode("B");
		route1.setCodeType("ADMINROUTE");
		route1.setDescription("Bladder");
		
		AdministrationRoute route2 = new AdministrationRoute();
		route2.setCode("TACE");
		route2.setCodeType("ADMINROUTE");
		route2.setDescription("INTRA-ARTERIAL (TACE)");
		
		AdministrationRoute route3 = new AdministrationRoute();
		route3.setCode("V");
		route3.setCodeType("ADMINROUTE");
		route3.setDescription("Intravenous");
		
		/* Begin Administration Routes List 1 */
		
		ProductAdministrationRoutes par10 = new ProductAdministrationRoutes(); 
		par10.setKey("KEY11");
		par10.setAdministrationRoute(route1);
		
		ProductAdministrationRoutes par11 = new ProductAdministrationRoutes();
		par11.setKey("KEY12");
		par11.setAdministrationRoute(route2);
		
		List<ProductAdministrationRoutes> adminRoutes1 = new ArrayList<>();
		adminRoutes1.add(par10);
		adminRoutes1.add(par11);
		
		/* End Administration Routes List 1 */
		
		/* Begin Administration Routes List 2 */
		
		ProductAdministrationRoutes par20 = new ProductAdministrationRoutes(); 
		par20.setKey("KEY20");
		par20.setAdministrationRoute(route2);
		
		ProductAdministrationRoutes par21 = new ProductAdministrationRoutes();
		par21.setKey("KEY21");
		par21.setAdministrationRoute(route3);
		
		List<ProductAdministrationRoutes> adminRoutes2 = new ArrayList<>();
		adminRoutes2.add(par20);
		adminRoutes2.add(par21);
		
		/* End Administration Routes List 2 */
		
		/* Begin Administration Routes List 3 */
		
		ProductAdministrationRoutes par30 = new ProductAdministrationRoutes(); 
		par30.setKey("KEY30");
		par30.setAdministrationRoute(route2);
		
		List<ProductAdministrationRoutes> adminRoutes3 = new ArrayList<>();
		adminRoutes3.add(par30);
		
		/* End Administration Routes List 3 */
		
		List<ProductAdministrationRoutes> commonAdministrationRoutes = this.productsService.findCommonAdministrationRoutes(adminRoutes1,adminRoutes2);
		
		assertThat(commonAdministrationRoutes.size(),is(1));
		assertThat(commonAdministrationRoutes.get(0),is(notNullValue()));
		assertThat(commonAdministrationRoutes.get(0).getAdministrationRoute(),is(notNullValue()));
		assertThat(commonAdministrationRoutes.get(0).getAdministrationRoute().getCode(),is("TACE"));
		assertThat(commonAdministrationRoutes.get(0).getAdministrationRoute().getCodeType(),is("ADMINROUTE"));
		assertThat(commonAdministrationRoutes.get(0).getAdministrationRoute().getDescription(),is("INTRA-ARTERIAL (TACE)"));
	}
}
