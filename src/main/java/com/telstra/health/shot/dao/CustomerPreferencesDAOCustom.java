package com.telstra.health.shot.dao;

import java.util.List;

import javax.persistence.NoResultException;

import com.telstra.health.shot.dao.exception.DataAccessException;
import com.telstra.health.shot.entity.CustomerPreference;

/**
 * 
 * TODO: Insert class explaination here.
 *
 * @author Marlon Cenita
 *
 */
public interface CustomerPreferencesDAOCustom {
	
	/**
	 * This method returns the all preferences for a given customer and product attributes. Each <code>CustomerPreference</code>
	 * entity will be joined with <code>DeliveryMechanism</code> and <code>AdminstrationRoute</code> if
	 * present. 
 
	 * @param customerId
	 * @param productDescription
	 * @param productType
	 * @param batDrugKey
	 * @param batDSUKey
	 * @param triKey
	 * @param msoIngStkKey
	 * @param batFormulation
	 * @return list of customer preferences.
	 * @throws DataAccessException
	 */
	public List<CustomerPreference> searchCustomerPreferences(String customerId, 
															  String productDescription,
															  String productType,
															  String batDrugKey,
															  String batDSUKey,
															  String triKey,
															  String msoIngStkKey,
															  String batFormulation) throws DataAccessException;
	
	/**
	 * This method returns the Customer Preference for given prefId. The <code>DeliveryMechanism</code> and 
	 * <code>AdminstrationRoute</code> will be eagerly fetched if they are present.
	 *   
	 * @param prefId
	 * @return The customer preference
	 * @throws DataAccessException
	 */
	public CustomerPreference getCustomerPreference(Integer prefId) throws DataAccessException;
	
	/**
	 * Deletes Customer Preferences one-by-one. The delete method of the <code>CrudRepository</code> was not used 
	 * because it will first load the entity and all the underlying child entities like <code>Diluent</code>,
	 * <code>Container</code> and <code>AdminstrationRoute</code> which will be a performance issue. Therefore the best
	 * approach is to use a query and delete it from <code>EntityManager</code>.<p/>
	 * 
	 * The rank of each remaining customer preferences will also be re-computed by filling in the gaps.<p/>
	 * 
	 * If during deletion an exception occurred then the transaction will be rolled back and nothing will be deleted. Its 
	 * all or nothing. 
	 * 
	 * @param customerPreferences
	 * @throws DataAccessException
	 */
	public void deleteCustomerPreferences(List<CustomerPreference> customerPreferences) throws DataAccessException;
	
	/**
	 * Get the maximum rank for a given customer and product. If there are no preferences yet then it will return 0.
	 * 
	 * @param customerId
	 * @param productDescription
	 * @param productType
	 * @param batDrugKey
	 * @param batDSUKey
	 * @param triKey
	 * @param msoIngStkKey
	 * @param batFormulation
	 * @return
	 * @throws DataAccessException
	 */
	public Integer getMaxRank(String customerId, 
							  String productDescription,
							  String productType,
							  String batDrugKey,
							  String batDSUKey,
							  String triKey,
							  String msoIngStkKey,
							  String batFormulation)throws DataAccessException;
	
	/**
	 * Updates a customer preference.
	 * 
	 * @param customerPreference
	 * @return
	 * @throws DataAccessException
	 */
	public CustomerPreference updateCustomerPreference(CustomerPreference customerPreference) throws DataAccessException;
	
	/**
	 * @param customerId
	 * @param productDescription
	 * @param productType
	 * @param batDrugKey
	 * @param batDSUKey
	 * @param triKey
	 * @param msoIngStkKey
	 * @param batFormulation
	 * @param dose
	 * @param deliveryMechanism
	 * @param route
	 * @param volume
	 * @param exact
	 * @param infusionDuration
	 * @return
	 */
	CustomerPreference getMatchingCustomerPreference(String customerId, 
													 String productDescription, 
													 String productType,
													 String batDrugKey, 
													 String batDSUKey, 
													 String triKey, 
													 String msoIngStkKey, 
													 String batFormulation, 
													 String dose,
													 String deliveryMechanism, 
													 String route, 
													 String volume, 
													 String exact, 
													 String infusionDuration) throws DataAccessException,NoResultException;
}
