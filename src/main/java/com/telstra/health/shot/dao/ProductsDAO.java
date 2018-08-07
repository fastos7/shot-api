package com.telstra.health.shot.dao;

import java.util.List;

import com.telstra.health.shot.dao.exception.DataAccessException;
import com.telstra.health.shot.dto.ProductDTO;
import com.telstra.health.shot.entity.ProductAdministrationRoutes;
import com.telstra.health.shot.entity.UnitOfMeasure;

/**
 * This is the Data Access Object for Customer's products / drugs. 
 * 
 * @author Marlon Cenita
 *
 */
public interface ProductsDAO  { 
	
	/**
	 * This method calls the stored procedure <code>dbo.sp_SearchCustomerProducts</code> to retrieve the products/drugs
	 * and their corresponding Delivery Mechanisms for the specified customer and a given search criteria. The stored
	 * procedure returns 2 result sets. The first result set contains the products and the second result set contains 
	 * the delivery mechanisms for each product.
	 * <p/>
	 * If the search criteria is empty, then it will return the top N 
	 * products ordered by <code>productDescription</code> column. The property <code>customer.preferences.product.search.max_results</code>
	 * controls the number of records (top N) return by this method.
	 * <p/> 
	 * Since the result of the stored procedure is not an entity<i>(does not have an id)</i>, the result needs to be
	 * manually mapped to the DTO object. The <code>BeanPropertyRowMapper</code> can't be used to automatically map
	 * the result to the DTO object as the mapper requires the target object to have setter methods which violates 
	 * best practices for DTO which states that it should be immutable.
	 * <p/>
	 * Lastly, in order to be able to acquire the <code>ResultSet</code> object which is important to explicitly 
	 * retrieve the raw columns from stored procedure, native <code>CallableStatement</code> was used instead of JPA.
	 * 
	 * @param customerId
	 * @param resultSet
	 * @param searchString
	 * @return
	 * @throws DataAccessException
	 */
	public List<ProductDTO> searchCustomerProducts(String customerId,
												   String resultSet,
												   String searchString) throws DataAccessException;
	
	/**
	 * Retrieve the available Administration Routes for a given product. Do note that Administration routes are only
	 * applicable to product types "Standard","Consignment Trial" and "Clinical Trial". 
	 * 
	 * @param productId
	 * @return available Administration Routes for a given product.
	 * @throws DataAccessException
	 */
	public List<ProductAdministrationRoutes> getAdministrationRoutes(String productId) throws DataAccessException;

	/**
	 * Get the available Unit Of measures for a given product.
	 * 
	 * @param productId
	 * @return
	 * @throws DataAccessException
	 */
	public List<UnitOfMeasure> getUnitOfMeasures(String productId) throws DataAccessException;
	
}
