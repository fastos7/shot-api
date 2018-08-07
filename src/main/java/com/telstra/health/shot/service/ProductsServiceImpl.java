package com.telstra.health.shot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.telstra.health.shot.dao.ProductsDAO;
import com.telstra.health.shot.dao.exception.DataAccessException;
import com.telstra.health.shot.dto.ProductAdministrationRoutesDTO;
import com.telstra.health.shot.dto.ProductAttributesDTO;
import com.telstra.health.shot.dto.ProductDTO;
import com.telstra.health.shot.entity.ProductAdministrationRoutes;
import com.telstra.health.shot.service.exception.ShotServiceException;
import com.telstra.health.shot.util.ShotUtils;

/**
 * 
 * TODO: Insert class explaination here.
 *
 * @author Marlon Cenita
 *
 */
@Service
public class ProductsServiceImpl implements ProductsService {

	private static final Logger logger = LoggerFactory.getLogger(ProductsServiceImpl.class);
	
	@Autowired
	ProductsDAO productsDAO;
	
	@Autowired
    private ModelMapper modelMapper;
	
	@Autowired
    private Environment env;
	 
	/*
	 * (non-Javadoc)
	 * @see com.telstra.health.shot.service.ProductsService#searchCustomerProducts(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<List<ProductDTO>> searchCustomerProducts(String customerId,
															 String resultSet,
															 String searchString) throws ShotServiceException{
		
		try {
			List<ProductDTO> productDTOList = this.productsDAO.searchCustomerProducts(customerId,resultSet,searchString);
									 
			return Optional.of(productDTOList);
		} catch (DataAccessException e) {
			logger.error("Failed to get customer products : Error : {} " , e.getMessage());
			throw new ShotServiceException(env.getProperty("error.customer.products.exception"));
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.telstra.health.shot.service.ProductsService#getProductAttributes(java.lang.String, boolean)
	 */
	@Override
	public ProductAttributesDTO getProductAttributes(String drugKey,String drugKey2,String drugKey3) throws ShotServiceException {
		
		List<ProductAdministrationRoutes> commonProductAdministrationRoutes = new ArrayList<>();
		
		
		// Get product available administration routes
		List<ProductAdministrationRoutes> productAdministrationRoutes = null;
		if (ShotUtils.isEmpty(drugKey) == false) {
			productAdministrationRoutes = this.productsDAO.getAdministrationRoutes(drugKey);
		}		
		
		List<ProductAdministrationRoutes> productAdministrationRoutes2 = null;
		if (ShotUtils.isEmpty(drugKey2) == false) {
			productAdministrationRoutes2 = this.productsDAO.getAdministrationRoutes(drugKey2);			
		}
		commonProductAdministrationRoutes = this.findCommonAdministrationRoutes(productAdministrationRoutes, productAdministrationRoutes2);
		
		List<ProductAdministrationRoutes> productAdministrationRoutes3 = null;
		if (ShotUtils.isEmpty(drugKey3) == false) {
			productAdministrationRoutes3 = this.productsDAO.getAdministrationRoutes(drugKey3);			
		}
		commonProductAdministrationRoutes = this.findCommonAdministrationRoutes(commonProductAdministrationRoutes, productAdministrationRoutes3);
		
		List<ProductAdministrationRoutesDTO> productAdministrationRoutesDTO = commonProductAdministrationRoutes.stream()
																										 .map( par -> {
																											   if(null == par ) return null;
																												else{
																													
																													ProductAdministrationRoutesDTO productAdministrationRouteDTO = modelMapper.map(par, ProductAdministrationRoutesDTO.class);
																													
																												    return productAdministrationRouteDTO;
																												}
																										    }).collect(Collectors.toList());	
		
		return new ProductAttributesDTO(productAdministrationRoutesDTO);
	}

	/* (non-Javadoc)
	 * @see com.telstra.health.shot.service.ProductsService#findCommonAdministrationRoutes(java.util.List, java.util.List, java.util.List)
	 */
	@Override
	public List<ProductAdministrationRoutes> findCommonAdministrationRoutes(List<ProductAdministrationRoutes> adminRoutes1, 
																			List<ProductAdministrationRoutes> adminRoutes2) {
		
		if (adminRoutes1 == null && adminRoutes2 == null) {
			return null;
		} else if (adminRoutes1 == null && adminRoutes2 != null) {
			return adminRoutes2;
		} else if (adminRoutes1 != null && adminRoutes2 == null) {
			return adminRoutes1;
		} else {
			
			List<ProductAdministrationRoutes> commonAdministrationRoutes = null;

			commonAdministrationRoutes = adminRoutes1.stream()
													 .filter( administrationRoute -> {
														 return adminRoutes2.stream().anyMatch( b -> administrationRoute.getAdministrationRoute().getCode().equals(b.getAdministrationRoute().getCode()) &&
																 							         administrationRoute.getAdministrationRoute().getCodeType().equals(b.getAdministrationRoute().getCodeType()) &&
																 							         administrationRoute.getAdministrationRoute().getDescription().equals(b.getAdministrationRoute().getDescription())  );
													 }).collect(Collectors.toList());
			
			return commonAdministrationRoutes;
		}		
	}

}
