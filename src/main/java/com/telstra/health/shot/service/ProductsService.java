package com.telstra.health.shot.service;

import java.util.List;
import java.util.Optional;

import com.telstra.health.shot.dto.ProductAttributesDTO;
import com.telstra.health.shot.dto.ProductDTO;
import com.telstra.health.shot.entity.ProductAdministrationRoutes;
import com.telstra.health.shot.service.exception.ShotServiceException;

/**
 * 
 * TODO: Insert class explaination here.
 *
 * @author Marlon Cenita
 *
 */
public interface ProductsService {
	
	/**
	 * 
	 * @param customerId
	 * @param resultSet
	 * @param searchString
	 * @return
	 * @throws ShotServiceException
	 */
	public Optional<List<ProductDTO>> searchCustomerProducts(String customerId,
															 String resultSet,
															 String searchString) throws ShotServiceException;
	
	/**
	 * 
	 * @param productId
	 * @return
	 * @throws ShotServiceException
	 */
	public ProductAttributesDTO getProductAttributes(String drugKey,String drugKey2,String drugKey3) throws ShotServiceException;

	/**
	 * @param adminRoutes1
	 * @param adminRoutes2
	 * @param adminRoutes3
	 * @return
	 */
	public List<ProductAdministrationRoutes> findCommonAdministrationRoutes(
			List<ProductAdministrationRoutes> adminRoutes1, 
			List<ProductAdministrationRoutes> adminRoutes2);
	
}
