package com.telstra.health.shot.dao;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.telstra.health.shot.dao.exception.DataAccessException;
import com.telstra.health.shot.dto.ContainerDTO;
import com.telstra.health.shot.dto.DeliveryMechanismsDTO;
import com.telstra.health.shot.dto.DiluentDTO;
import com.telstra.health.shot.dto.ProductDTO;
import com.telstra.health.shot.entity.ProductAdministrationRoutes;
import com.telstra.health.shot.entity.UnitOfMeasure;

/**
 * 
 * TODO: Insert class explaination here.
 *
 * @author Marlon Cenita
 *
 */
@Repository
public class ProductsDAOImpl implements ProductsDAO{

    private static final Logger logger = LoggerFactory.getLogger(ProductsDAOImpl.class);
	
    @Value("${customer.preferences.product.search.max_results}")
    private Integer maxSearchResults;
    
	@PersistenceContext
    private EntityManager entityManager; 
	
	/*
	 * (non-Javadoc)
	 * @see com.telstra.health.shot.dao.ProductsDAO#searchCustomerProducts(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override	
	public List<ProductDTO> searchCustomerProducts(String customerId,
												   String resultSet,
												   String searchString) throws DataAccessException {
		
		try {
			
			/*
			 * This map will contain unique products. The uniqueness is guaranteed by the stored procedure because
			 * it is using distinct to list down the products. One the map is populated with products, each product
			 * will be retrieved from the map and store their delivery mechanisms if present. 
			 */
			Map<ProductDTO,ProductDTO> customerProductMap = new HashMap<ProductDTO,ProductDTO>();
			
			Session session = this.entityManager.unwrap(Session.class);
			session.doWork( conn -> {
				
				/*
				 * This stored procedure return 2 result sets. The first result set contains the products and second
				 * one contains the Delivery Mechanisms of each product.
				 */
				try (CallableStatement sp = conn.prepareCall("exec dbo.SP_SearchProducts ?,?,?,?")) {
					
					sp.setString(1, customerId);
					sp.setString(2, resultSet);
					sp.setString(3, searchString);
					sp.setInt(4, maxSearchResults);
					
					/*
					 * Get the product list
					 */
					boolean moreResultSets = sp.execute();
					if (moreResultSets && sp.getUpdateCount() == -1 ) {
						try (ResultSet rs = sp.getResultSet()) {
							while(rs.next()) {
								
								ProductDTO product = new ProductDTO(rs.getString("ProductDescription"),
							 									    rs.getString("EntryType"),
							 									    rs.getString("ProcessType"),
							 									    rs.getString("TargetSite"),
							 									    rs.getString("Schedule"),
							 									    rs.getString("BAT_DrugKey"),
							 									    rs.getString("BAT_DSUKey"),
							 									    rs.getString("TRI_Key"),
							 									    rs.getString("MSO_ING_STKKEy"),
							 									    rs.getString("bat_formulation"),
							 									    rs.getString("GDR_AmountUnits"),
							 									    rs.getString("GDR_Description"),
							 									    rs.getString("BAT_DrugKey2"),
							 									    rs.getString("BAT_DSUKey2"),
							 									    rs.getString("GDR2_AmountUnits"),
							 									    rs.getString("GDR2_Description"),
							 									    rs.getString("BAT_DrugKey3"),
							 									    rs.getString("BAT_DSUKey3"),
							 									    rs.getString("GDR3_AmountUnits"),
							 									    rs.getString("GDR3_Description"));
								
								customerProductMap.put(product, product);
							}
						} catch (Exception e) {
							logger.error("Failed to get customer {} products. Error : {}" ,customerId, e);
							throw new DataAccessException(e.getMessage());
						}						
					}				
 		
					/*
					 * Get the product list	+ delivery mechanisms
					 */
					moreResultSets = sp.getMoreResults();
					if (moreResultSets && sp.getUpdateCount() == -1 ) {						
														
						try (ResultSet rs = sp.getResultSet()) {
							while(rs.next()) {
								
								/*
								 * Use this product as a key to retrieve the product from the map.
								 */
								ProductDTO productKey = new ProductDTO(rs.getString("ProductDescription"),
								 									   rs.getString("EntryType"),
								 									   rs.getString("ProcessType"),
								 									   rs.getString("TargetSite"),
								 									   rs.getString("Schedule"),
								 									   rs.getString("BAT_DrugKey"),
								 									   rs.getString("BAT_DSUKey"),
								 									   rs.getString("TRI_Key"),
								 									   rs.getString("MSO_ING_STKKEy"),
								 									   rs.getString("bat_formulation"),
								 									   rs.getString("GDR_AmountUnits"),
								 									   rs.getString("GDR_Description"),
								 									   rs.getString("BAT_DrugKey2"),
								 									   rs.getString("BAT_DSUKey2"),
								 									   rs.getString("GDR2_AmountUnits"),
								 									   rs.getString("GDR2_Description"),
								 									   rs.getString("BAT_DrugKey3"),
								 									   rs.getString("BAT_DSUKey3"),
								 									   rs.getString("GDR3_AmountUnits"),
								 									   rs.getString("GDR3_Description"));
								
								ProductDTO product = customerProductMap.get(productKey);
								if (product != null) {
									
									if (rs.getString("DEL_Key") != null) {
										
										DiluentDTO diluent = new DiluentDTO(rs.getString("DEL_DiluentType"),
												rs.getString("Diluent_Description"),
												rs.getString("Diluent_Code"));
			
										ContainerDTO container = new ContainerDTO(rs.getString("DEL_ContainerType"),
																			      rs.getString("Container_Description"),
																			      rs.getString("Container_Code"));
										
										DeliveryMechanismsDTO deliveryMechanism = new DeliveryMechanismsDTO(rs.getString("DEL_Key"),
																											diluent,
																											container,
																											null);
										/*
										 * Store the Delivery mechanism for the product.
										 */
										List<DeliveryMechanismsDTO> deliveryMechanisms = product.getDeliveryMechanisms();									
										if (deliveryMechanisms != null) {
											deliveryMechanisms.add(deliveryMechanism);
										} else {
											deliveryMechanisms = new ArrayList<>();
											deliveryMechanisms.add(deliveryMechanism);
											product.setDeliveryMechanisms(deliveryMechanisms);
										}
										
									}									
								}
												                
							}	
						} catch (Exception e) {
							logger.error("Failed to get customer {} products. Error : {}" ,customerId, e);
							throw new DataAccessException(e.getMessage());
						}
										
					}
					
				} catch (Exception e) {
					logger.error("Failed to get customer {} products. Error : {}" ,customerId, e);
					throw new DataAccessException(e.getMessage());
				}
			
				 
			});
			
			/*
			 * Return the products and their corresponding Delivery Mechanisms.
			 */
			return new ArrayList<ProductDTO>(customerProductMap.values());

			
		} catch(Exception ex){
			logger.error("Failed to get customer {} products. Error : {}" ,customerId, ex);
			throw new DataAccessException(ex.getMessage());
		} 
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.telstra.health.shot.dao.ProductsDAO#getAdministrationRoutes(java.lang.String)
	 */
	@Override
	public List<ProductAdministrationRoutes> getAdministrationRoutes(String productId) throws DataAccessException {
		
		logger.debug("Trying to get Administration Routes for productID {}.",productId);
		try {
			
			
			String query = "  from ProductAdministrationRoutes par"
						 + " LEFT JOIN FETCH par.stock as gdr"
						 + " LEFT JOIN FETCH par.administrationRoute as routes"
					     + " where gdr.key = :productId"
					     + "   and gdr.type = 'GDR'"
					     + "   and gdr.active = 'A'"
					     + "   and par.active = 'A'"
					     + "   and routes.codeType = 'ADMINROUTE'"
					     + "   and routes.active = 'A'"
					     + " order by routes.description";
			
			@SuppressWarnings("unchecked")
			List<ProductAdministrationRoutes> productAdministrationRoutes = this.entityManager.createQuery(query)
																			 .setParameter("productId", productId)
					 														 .getResultList();  
			
			logger.debug("Found {} Administration Routes for productID {} .", productAdministrationRoutes.size(), productId);
			
			return productAdministrationRoutes;
		} catch (Exception e) {
			logger.error("Failed to get Administration Routes for productID {}. Error : {}", productId, e.getMessage());
			throw new DataAccessException(e.getMessage());
		}
	}


	/*
	 * (non-Javadoc)
	 * @see com.telstra.health.shot.dao.ProductsDAO#getUnitOfMeasures(java.lang.String)
	 */
	@Override
	public List<UnitOfMeasure> getUnitOfMeasures(String productId) throws DataAccessException {
		
		logger.debug("Trying to get unit of measures for productID {}.",productId);
		try {
			
			
			String query = "  from UnitOfMeasure uom"
						 + " LEFT JOIN FETCH uom.stock as gdr"
					     + " where gdr.key = :productId"
					     + "   and gdr.type = 'GDR'"
					     + "   and gdr.active = 'A'"
					     + "   and uom.type = 'AMOUNTUNITS'"					     					     
					     + " order by uom.description";
			
			@SuppressWarnings("unchecked")
			List<UnitOfMeasure> unitOfMeasures = this.entityManager.createQuery(query)
																   .setParameter("productId", productId)
		 														   .getResultList();  
			
			return unitOfMeasures;
		} catch (Exception e) {
			logger.error("Failed to get Unit of measures for productID {} . Error : {}", productId, e.getMessage());
			throw new DataAccessException(e.getMessage());
		}
		
	}
	
	

}

