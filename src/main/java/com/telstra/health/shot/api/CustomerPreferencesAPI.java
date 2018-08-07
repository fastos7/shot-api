package com.telstra.health.shot.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.telstra.health.shot.api.exception.ApiException;
import com.telstra.health.shot.dto.CustomerPreferenceDTO;
import com.telstra.health.shot.service.CustomerPreferencesService;
import com.telstra.health.shot.service.exception.ShotServiceException;

/**
 * 
 * RestController for all Customer Preference related APIs.
 *
 * @author Marlon Cenita
 *
 */
@RestController
@RequestMapping("/api/customers/{customerId}/preferences/")
public class CustomerPreferencesAPI {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomerPreferencesAPI.class);
	
	@Autowired
	CustomerPreferencesService customerPreferencesService;
	
	/**
	 * API to get the list of preferences for a given customer.
	 * <p/>
	 * URI : <code>/shot/api/customers/{customerId}/preferences/searches?productDescription={productDescription}&productType={productType}&batDrugKey={batDrugKey}&batDSUKey={batDSUKey}&triKey={triKey}&msoIngStkKey={msoIngStkKey}&batFormulation={batFormulation}</code>
	 * <p/>
	 * METHOD : <code>HTTP GET</code>
	 * <p/>
	 * If the given product of a customer doesnt have any preferences then
	 * it will return 404 NOT_FOUND 
	 * 
	 * @param customerId 
	 * @return List of preferences.
	 */
	@RequestMapping(value="/searches",
					method=RequestMethod.GET,
					produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<CustomerPreferenceDTO>> searchCustomerPreferences(
			@PathVariable("customerId") 							   String customerId,
			@RequestParam(name="productDescription", 	required=true) String productDescription,
			@RequestParam(name="productType", 		 	required=true) String productType,
			@RequestParam(name="batDrugKey", 		 	required=true) String batDrugKey,
			@RequestParam(name="batDSUKey", 			required=true) String batDSUKey,
			@RequestParam(name="triKey", 				required=true) String triKey,
			@RequestParam(name="msoIngStkKey", 			required=true) String msoIngStkKey,
			@RequestParam(name="batFormulation", 		required=true) String batFormulation){
		
		logger.info("Getting all preferences for customer [{customerId}] with the following parameters : "
				  + "productDescription=[{}], productType=[{}],batDrugKey=[{}],batDSUKey=[{}],triKey=[{}],"
				  + "msoIngStkKey=[{}],batFormulation=[{}]."
				,productDescription,productType,batDrugKey,batDSUKey,triKey,msoIngStkKey,batFormulation);
		
		if (customerId == null || customerId.equals("")) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		try {
			
			List<CustomerPreferenceDTO> customerPreferences 
				= this.customerPreferencesService.searchCustomerPreferences(customerId,
																			productDescription,
																			productType,
																			batDrugKey,
																			batDSUKey,
																			triKey,
																			msoIngStkKey,
																			batFormulation);
			
			if (customerPreferences == null || customerPreferences.size() <= 0) {
				logger.info("No preferences found for customer [{}] .",customerId);
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}	
			logger.info("Found [{}] preferences for customer [{}] .",customerPreferences.size(),customerId);
			return new ResponseEntity<List<CustomerPreferenceDTO>>(customerPreferences,HttpStatus.OK);
		} catch (ShotServiceException e) {
			logger.error("Error getting preferences for customer {} . Error : {}",customerId,e.getMessage());
			throw new ApiException(e.getMessage());
		}
		
	}
	
	/**
	 * API to create customer preferences.
	 * <p/>
	 * URI : <code>/shot/api/customers/{customerId}/preferences/</code>
	 * <p/>
	 * METHOD : <code>HTTP POST</code>
	 * <p/>
	 * When successful it will return status 201 - CREATED. Otherwise it will return status 500.
	 * 
	 * @param customerPreferenceDTO
	 * @param customerId
	 * @return The newly created customer preference with the new Id.
	 */
	@RequestMapping(value="/",
					method=RequestMethod.POST,
					consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,
					produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<CustomerPreferenceDTO> createCustomerPreference(@RequestBody CustomerPreferenceDTO customerPreferenceDTO,
																		  @PathVariable("customerId") String customerId) {
		
		logger.info("Creating customer preference [{}] for customer [{}] .",customerPreferenceDTO,customerId);
		
		CustomerPreferenceDTO savedCustomerPreferenceDTO;
		try {
			savedCustomerPreferenceDTO = this.customerPreferencesService.createCustomerPreference(customerPreferenceDTO);
			logger.info("Successfully created customer preference [{}] for customer [{}] .",savedCustomerPreferenceDTO.toString(),customerId);
			return new ResponseEntity<CustomerPreferenceDTO>(savedCustomerPreferenceDTO,HttpStatus.CREATED);
		} catch (ShotServiceException e) {
			logger.error("Error creating preferences for customer {}. Error : {}",customerId,e.getMessage());
			throw new ApiException(e.getMessage());
		}		
	}
	
	/**
	 * API to Update customer preference.
	 * <p/>
	 * URI : <code>/shot/api/customers/{customerId}/preferences/{prefId}/</code>
	 * <p/>
	 * METHOD : <code>HTTP PUT</code>
	 * <p/>
	 * When successful it will return status 200 - OK. Otherwise it will return status 500.
	 *
	 * @param customerPreferenceDTO
	 * @param customerId 
	 * @param prefId
	 * @return The updated customer preference in DTO form.
	 */
	@RequestMapping(value="/{prefId}/",
					method=RequestMethod.PUT,
					consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,
					produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<CustomerPreferenceDTO> updateCustomerPreference(@RequestBody CustomerPreferenceDTO customerPreferenceDTO,
																	  	  @PathVariable("customerId") String customerId,		 															      
		 															      @PathVariable("prefId") String prefId) {
	
		logger.info("Updating customer preference [{}] for customer [{}] .",customerPreferenceDTO,customerId);		
		
		CustomerPreferenceDTO savedCustomerPreferenceDTO;
		try {
			savedCustomerPreferenceDTO = this.customerPreferencesService.updateCustomerPreference(customerPreferenceDTO);			
			logger.info("Successfully updated customer preference [{}] for customer [{}] .",savedCustomerPreferenceDTO,customerId);
			return new ResponseEntity<CustomerPreferenceDTO>(savedCustomerPreferenceDTO,HttpStatus.OK);
		} catch (com.telstra.health.shot.dao.exception.EntityNotFoundException e) {
			logger.error("Error updating customer preference : {}. Error : {}",prefId,e.getMessage());
			return new ResponseEntity<CustomerPreferenceDTO>(HttpStatus.NOT_FOUND);
		}  catch (ShotServiceException e) {
			logger.error("Error updating customer preference : {}. Error : {}",prefId,e.getMessage());
			throw new ApiException(e.getMessage());
		}
	}
	
	/**
	 * API to Update customer preferences.
	 * <p/>
	 * URI : <code>/shot/api/customers/{customerId}/preferences/</code>
	 * <p/>
	 * METHOD : <code>HTTP PUT</code>
	 * <p/>
	 * When successful it will return status 200 - OK. Otherwise it will return status 500.
	 *
	 * @param customerPreferenceDTO
	 * @param customerId 
	 * @param prefId
	 * @return The updated customer preference in DTO form.
	 */
	@RequestMapping(value="/",
					method=RequestMethod.PUT,
					consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,
					produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<CustomerPreferenceDTO>> updateCustomerPreferences(@RequestBody List<CustomerPreferenceDTO> customerPreferencesDTO,
																	  	  @PathVariable("customerId") String customerId) {
		
		logger.info("Updating customer preferences for customer [{}]:",customerId);
		customerPreferencesDTO.forEach(cp -> {
			logger.info("            {}",cp.getPrefId());
		});
		
		List<CustomerPreferenceDTO> updatedCustomerPreferencesDTO;
		try {
			updatedCustomerPreferencesDTO = this.customerPreferencesService.updateCustomerPreferences(customerPreferencesDTO);
			return new ResponseEntity<List<CustomerPreferenceDTO>>(updatedCustomerPreferencesDTO,HttpStatus.OK);
		} catch (ShotServiceException e) {
			logger.error("Error updating customer preferences.  Error : {}",e.getMessage());
			throw new ApiException(e.getMessage());
		}
	}
	
	/**
	 * API to delete a list customer preferences.
	 * <p/>
	 * URI : <code>/shot/api/customers/{customerId}/preferences/</code>
	 * <p/>
	 * METHOD : <code>HTTP DELETE</code>
	 * <p/>
	 * When successful it will return status 204 - NO_CONTENT. Otherwise it will return status 500. 
	 * 
	 * @param customerPreferencesDTO List of customer preference which should only contain their prefId.
	 * @param customerId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/",
					method=RequestMethod.DELETE,
					consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> deleteCustomerPreferences(@RequestBody List<CustomerPreferenceDTO> customerPreferencesDTO,
													   @PathVariable("customerId") String customerId) {
		
		logger.info("Deleting customer preferences for customer [{}]:",customerId);
		customerPreferencesDTO.forEach(cp -> {
			logger.info("            {}",cp.getPrefId());
		});
		
		
		try {
			this.customerPreferencesService.deleteCustomerPreferences(customerPreferencesDTO);
			logger.info("Successfully deleted customer preferences for customer [{}]",customerId);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		} catch (ShotServiceException e) {
			logger.error("Error deleting customer preferences. Error : {}",e.getMessage());
			throw new ApiException(e.getMessage());
		}
	}
	
	/**
	 * API to get the list of preferences for a given customer.
	 * <p/>
	 * URI : <code>/shot/api/customers/{customerId}/preferences/searches?productDescription={productDescription}&productType={productType}&batDrugKey={batDrugKey}&batDSUKey={batDSUKey}&triKey={triKey}&msoIngStkKey={msoIngStkKey}&batFormulation={batFormulation}</code>
	 * <p/>
	 * METHOD : <code>HTTP GET</code>
	 * <p/>
	 * If the given product of a customer doesnt have any preferences then
	 * it will return 404 NOT_FOUND 
	 * 
	 * @param customerId 
	 * @return List of preferences.
	 */
	@RequestMapping(value="/matches",
					method=RequestMethod.GET,
					produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<CustomerPreferenceDTO> getMatchingCustomerPreference(
			@PathVariable("customerId") 							   String customerId,
			@RequestParam(name="productDescription", 	required=true) String productDescription,
			@RequestParam(name="productType", 		 	required=true) String productType,
			@RequestParam(name="batDrugKey", 		 	required=true) String batDrugKey,
			@RequestParam(name="batDSUKey", 			required=true) String batDSUKey,
			@RequestParam(name="triKey", 				required=true) String triKey,
			@RequestParam(name="msoIngStkKey", 			required=true) String msoIngStkKey,
			@RequestParam(name="batFormulation", 		required=true) String batFormulation,
			@RequestParam(name="dose", 					required=true) String dose,
			@RequestParam(name="deliveryMechanism", 	required=true) String deliveryMechanism,
			@RequestParam(name="route", 				required=true) String route,
			@RequestParam(name="volume", 				required=true) String volume,
			@RequestParam(name="exact", 				required=true) String exact,
			@RequestParam(name="infusionDuration", 		required=true) String infusionDuration){
		
		logger.info("Getting applicable preferences for customer [{customerId}] with the following parameters : "
				  + "productDescription=[{}], productType=[{}],batDrugKey=[{}],batDSUKey=[{}],triKey=[{}],"
				  + "msoIngStkKey=[{}],batFormulation=[{}],dose=[{}],deliveryMechanism=[{}],route=[{}],volume=[{}],"
				  + "exact=[{}],infusionDuration=[{}]."
				,productDescription,productType,batDrugKey,batDSUKey,triKey,msoIngStkKey,batFormulation,dose,
				deliveryMechanism,route,volume,exact,infusionDuration);
			
		
		if (customerId == null || customerId.equals("")) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		try {
			
			CustomerPreferenceDTO customerPreference  
				= this.customerPreferencesService.getMatchingCustomerPreference(customerId,
																			    productDescription,
																			    productType,
																			    batDrugKey,
																			    batDSUKey,
																			    triKey,
																			    msoIngStkKey,
																			    batFormulation,
																			    dose,
																			    deliveryMechanism,
																			    route,
																			    volume,
																			    exact,
																			    infusionDuration);
			
			if (customerPreference == null) {
				logger.info("No matching preference found for customer [{}] .",customerId);
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}	
			logger.info("Found matching preference [{}] for customer id [{}].",customerPreference,customerId);
			return new ResponseEntity<CustomerPreferenceDTO>(customerPreference,HttpStatus.OK);
		} catch (ShotServiceException e) {
			logger.error("Error getting preferences for customer {} . Error : {}",customerId,e.getMessage());
			throw new ApiException(e.getMessage());
		} catch (com.telstra.health.shot.dao.exception.EntityNotFoundException e) {			
			return new ResponseEntity<CustomerPreferenceDTO>(HttpStatus.NOT_FOUND);
		}
		
	}
}
