package com.telstra.health.shot.service;

import java.util.List;

import com.telstra.health.shot.dto.CustomerPreferenceDTO;
import com.telstra.health.shot.entity.CustomerPreference;
import com.telstra.health.shot.service.exception.ShotServiceException;

/**
 * Service for Customer Preference.
 * 
 * @author Marlon Cenita
 *
 */
public interface CustomerPreferencesService {

	/**
	 *  Method to get all preferences for a given customer and product.
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
	 * @throws ShotServiceException
	 */
	public List<CustomerPreferenceDTO> searchCustomerPreferences(String customerId,
																 String productDescription,
																 String productType,
																 String batDrugKey,
																 String batDSUKey,
																 String triKey,
																 String msoIngStkKey,
																 String batFormulation) throws ShotServiceException;
	
	/**
	 * This method is to create Customer Preferences. If the preference to be created contains <code>Diluent</code>,<code>Container</code>,
	 * <code>AdministrationRoute</code> or <code>UnitOfMeasure</code>, these values needs to be check against the database 
	 * whether they exist and active and find the corresponding entity. It will also get the maximum + 1 rank for the preferences
	 * of a customer and product and set this to the preference.<p/>
	 * Do note that customer preference does not impose uniqueness therefore this method can be called many times and will
	 * not throw any exception even exact same values already exists in the table.
	 * 
	 * @param customerPreferenceDTO
	 * @return the newly created preference in DTO form.
	 * @throws ShotServiceException
	 */
	public CustomerPreferenceDTO createCustomerPreference(CustomerPreferenceDTO customerPreferenceDTO) throws ShotServiceException;
	
	/**
	 * This method is to update Customer Preferences. If the preference to be updated contains <code>Diluent</code>,<code>Container</code>,
	 * <code>AdministrationRoute</code> or <code>UnitOfMeasure</code>, these values needs to be check against the database 
	 * whether they exist and active and find the corresponding entity.  
	 * 
	 * @param customerPreferenceDTO
	 * @return The updated preference in DTO form.
	 * @throws ShotServiceException
	 */
	public CustomerPreferenceDTO updateCustomerPreference(CustomerPreferenceDTO customerPreferenceDTO) 
			throws ShotServiceException,com.telstra.health.shot.dao.exception.EntityNotFoundException;

	/**
	 * Deleted a list of Customer Preferences. The list of DTOs to be deleted should only contain their ID. These Ids will
	 * then be used to delete the preferences one-by-one. <p/>
	 * 
	 * Then the ranks of the remaining customer preferences will then be computed and saved.
	 * 
	 * @param customerPreferencesDTO
	 * @throws ShotServiceException
	 */
	public void deleteCustomerPreferences(List<CustomerPreferenceDTO> customerPreferencesDTO) throws ShotServiceException;
	
	/**
	 * This method will re-compute the ranks of the a given customer preferences filling in any gaps. It will return true
	 * if the ranks needs to change or not. This flag will be used to either save the customer preferences or not. 
	 * 
	 * @param remainingCustomerPreferences
	 * @return true if there are any changes on the ranks. false otherwise.
	 */
	public boolean recomputeRanks(List<CustomerPreference> remainingCustomerPreferences);

	public List<CustomerPreferenceDTO> updateCustomerPreferences(List<CustomerPreferenceDTO> customerPreferencesDTO) throws ShotServiceException;

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
	public CustomerPreferenceDTO getMatchingCustomerPreference(String customerId, 
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
															   String infusionDuration) throws ShotServiceException,com.telstra.health.shot.dao.exception.EntityNotFoundException;
}
