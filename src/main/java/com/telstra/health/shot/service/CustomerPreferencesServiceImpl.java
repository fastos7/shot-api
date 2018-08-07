package com.telstra.health.shot.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.telstra.health.shot.dao.AdminstrationRouteDAO;
import com.telstra.health.shot.dao.ContainerDAO;
import com.telstra.health.shot.dao.CustomerPreferencesDAO;
import com.telstra.health.shot.dao.DeliveryMechanismDAO;
import com.telstra.health.shot.dao.DiluentDAO;
import com.telstra.health.shot.dao.UnitOfMeasureDAO;
import com.telstra.health.shot.dao.exception.DataAccessException;
import com.telstra.health.shot.dto.CustomerPreferenceDTO;
import com.telstra.health.shot.entity.AdministrationRoute;
import com.telstra.health.shot.entity.Container;
import com.telstra.health.shot.entity.CustomerPreference;
import com.telstra.health.shot.entity.DeliveryMechanisms;
import com.telstra.health.shot.entity.Diluent;
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
public class CustomerPreferencesServiceImpl implements CustomerPreferencesService {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomerPreferencesServiceImpl.class);
	
	@Autowired
	CustomerPreferencesDAO customerPreferencesRepository;
	
	@Autowired
	DiluentDAO diluentRepository;
	
	@Autowired
	ContainerDAO containerRepository;
	
	@Autowired
	DeliveryMechanismDAO deliveryMechanismRepository;
	
	@Autowired
	AdminstrationRouteDAO adminstrationRouteRepository;
	
	@Autowired
	UnitOfMeasureDAO unitOfMeasureRepository;
	
	@Autowired
    private ModelMapper modelMapper;
	
	@Autowired
    private Environment env;
	
	/*
	 * (non-Javadoc)
	 * @see com.telstra.health.shot.service.CustomerPreferencesService#getAllCustomerPreferences(java.lang.String, java.lang.String)
	 */
	@Override
	public List<CustomerPreferenceDTO> searchCustomerPreferences(String customerId, 
																 String productDescription,
																 String productType,
																 String batDrugKey,
																 String batDSUKey,
																 String triKey,
																 String msoIngStkKey,
																 String batFormulation)
															     throws ShotServiceException {
		
		try {
			List<CustomerPreference> customerPreferences 
				= this.customerPreferencesRepository.searchCustomerPreferences(customerId, 
																			   productDescription,
																			   productType,
																			   batDrugKey,
																			   batDSUKey,
																			   triKey,
																			   msoIngStkKey,
																			   batFormulation);
			
			List<CustomerPreferenceDTO> customerPreferenceDTOs 
				= customerPreferences.stream()
								     .map( customerPreference -> {
								    	if(null == customerPreference ) return null;
										else{
											
											CustomerPreferenceDTO customerPreferenceDTO 
												= modelMapper.map(customerPreference, CustomerPreferenceDTO.class);
											
										    return customerPreferenceDTO;
										}
								      }).collect(Collectors.toList());
																				    
			
			return customerPreferenceDTOs;
		} catch (DataAccessException e) {
			logger.error("Failed to get customer preferences for customerId {} and productId {}. ", customerId, e);
			throw new ShotServiceException(env.getProperty("error.customer.products.exception"));
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.telstra.health.shot.service.CustomerPreferencesService#createCustomerPreference(com.telstra.health.shot.dto.CustomerPreferenceDTO)
	 */
	@Override
	public CustomerPreferenceDTO createCustomerPreference(CustomerPreferenceDTO customerPreferenceDTO)
			throws ShotServiceException {
		
		CustomerPreference customerPreference = this.modelMapper.map(customerPreferenceDTO, CustomerPreference.class);
		
		this.validateCustomerPreference(customerPreferenceDTO, customerPreference);
		
		// Get the latest rank for the customer / product
		int rank = this.customerPreferencesRepository.getMaxRank(customerPreferenceDTO.getCustomerKey(),
																 customerPreferenceDTO.getProductDescription(),
																 customerPreferenceDTO.getProductType(),
																 customerPreferenceDTO.getBatDrugKey(),
																 customerPreferenceDTO.getBatDSUKey(),
																 customerPreferenceDTO.getTriKey(),
																 customerPreferenceDTO.getMsoIngStkKey(),
																 customerPreferenceDTO.getBatFormulation());
		 
		customerPreference.setRank(rank +1);
		customerPreference.setCreatedDate(Timestamp.from(Instant.now()));
		customerPreference.setUpdatedDate(Timestamp.from(Instant.now()));
		this.customerPreferencesRepository.save(customerPreference);
		
		/* Mapped and return the newly created Customer Preference. */
		CustomerPreferenceDTO newCustomerPreferenceDTO = this.modelMapper.map(customerPreference, CustomerPreferenceDTO.class);
		
		return newCustomerPreferenceDTO;
	}
	

	/*
	 * (non-Javadoc)
	 * @see com.telstra.health.shot.service.CustomerPreferencesService#updateCustomerPreference(com.telstra.health.shot.dto.CustomerPreferenceDTO)
	 */
	@Override
	public CustomerPreferenceDTO updateCustomerPreference(CustomerPreferenceDTO customerPreferenceDTO)
			throws ShotServiceException,com.telstra.health.shot.dao.exception.EntityNotFoundException {
		
		/* Mapped and return the newly updated Customer Preference. */
		CustomerPreferenceDTO updatedCustomerPreferenceDTO;
		try {
			/* Find the existing entity */
			CustomerPreference customerPreferenceToBeUpdated = this.customerPreferencesRepository.getOne(customerPreferenceDTO.getPrefId());
			if (customerPreferenceToBeUpdated == null) {
				logger.error("Unable to update Customer Preference with id {}. The record doest not exist anymore in the database.",customerPreferenceDTO.getPrefId());
				throw new ShotServiceException(env.getProperty("error.customer.preferences.update.record_does_not_exist"));
			}

			customerPreferenceToBeUpdated.setDoseFrom(customerPreferenceDTO.getDoseFrom());
			customerPreferenceToBeUpdated.setDoseTo(customerPreferenceDTO.getDoseTo());
			
			customerPreferenceToBeUpdated.setVolume(customerPreferenceDTO.getVolume());
			customerPreferenceToBeUpdated.setExact(customerPreferenceDTO.getExact());
			customerPreferenceToBeUpdated.setInfusionDuration(customerPreferenceDTO.getInfusionDuration());
			customerPreferenceToBeUpdated.setQuantity(customerPreferenceDTO.getQuantity());
			customerPreferenceToBeUpdated.setComments(customerPreferenceDTO.getComments());
			
			customerPreferenceToBeUpdated.setUpdatedBy(customerPreferenceDTO.getUpdatedBy());
			customerPreferenceToBeUpdated.setUpdatedDate(Timestamp.from(Instant.now()));
			
			// Delivery Mechanism
			if (customerPreferenceDTO.getDeliveryMechanism() != null) {
				DeliveryMechanisms deliveryMechanisms = this.deliveryMechanismRepository.getOne(customerPreferenceDTO.getDeliveryMechanism().getKey());
				customerPreferenceToBeUpdated.setDeliveryMechanism(deliveryMechanisms);
			} else {
				customerPreferenceToBeUpdated.setDeliveryMechanism(null);
			}
			
			// Admin Route		
			if (customerPreferenceDTO.getAdministrationRoute() != null) {
				AdministrationRoute adminRoute = adminstrationRouteRepository.findActiveAdministrationRouteByCode(customerPreferenceDTO.getAdministrationRoute().getCode());
				customerPreferenceToBeUpdated.setAdministrationRoute(adminRoute);
			} else {
				customerPreferenceToBeUpdated.setAdministrationRoute(null);
			}
			
			CustomerPreference customerPreferenceToBeNew = this.customerPreferencesRepository.updateCustomerPreference(customerPreferenceToBeUpdated);
			
			updatedCustomerPreferenceDTO = this.modelMapper.map(customerPreferenceToBeNew, CustomerPreferenceDTO.class);
			
			return updatedCustomerPreferenceDTO;
		} catch (DataAccessException e) {
			logger.error("Unable to update Customer Preference with id {}. Error : ",customerPreferenceDTO.getPrefId(),e.getMessage());
			throw new ShotServiceException(e.getMessage());
		} catch (EntityNotFoundException e) {
			logger.error("Unable to update Customer Preference with id {}. The record doest not exist anymore in the database.",customerPreferenceDTO.getPrefId());
			throw new com.telstra.health.shot.dao.exception.EntityNotFoundException(env.getProperty("error.customer.preferences.update.record_does_not_exist"));
		}		
	
	}
	
	/**
	 * This method check and validates if the <code>Diluent</code>,<code>Container</code>, <code>AdministrationRoute</code> 
	 * or <code>UnitOfMeasure</code> of the Customer Preference to be added or updated still exist in the database and still
	 * active. The <code>Diluent</code>,<code>Container</code> and <code>AdministrationRoute</code> are only mandatory 
	 * for non-formulation and therefore will raise an exception of this is not provided or does not exist in the database
	 * or not active.<p/>
	 * This is just a second level validation and UI should validate first the Customer Preference fields before calling
	 * the API to add it.   
	 * 
	 * @param customerPreferenceDTO
	 * @param customerPreference
	 * @throws ShotServiceException
	 */
	public void validateCustomerPreference(CustomerPreferenceDTO customerPreferenceDTO,CustomerPreference customerPreference) throws ShotServiceException {
		
		List<String> validationErrors = new ArrayList<>();
		
		/************************* Start of validations *************************/
		
		logger.debug("Start validating...");
		
		/*
		 * Diluent - is mandatory for products that are not "Formulations". Need to check if the chosen diluent 
		 * is present in the system and is active.  
		 */
		
		if (ShotUtils.isFormulationProductType(customerPreferenceDTO.getProductType()) == false) {			
			if (customerPreferenceDTO.getDeliveryMechanism() != null && 
				customerPreferenceDTO.getDeliveryMechanism().getDiluent() != null) {
				String diluentKey = customerPreferenceDTO.getDeliveryMechanism().getDiluent().getStockKey();
				Diluent diluent = diluentRepository.findActiveDiluentByKey(diluentKey); 
				if (diluent == null) {	
					logger.warn("Failed validation. Missing Diluent. ");
					validationErrors.add(env.getProperty("error.customer.preferences.add.diluent_does_not_exist_or_inactive"));													
				} 
			} else {
				logger.warn("Failed validation. Missing Diluent. ");
				validationErrors.add(env.getProperty("error.customer.preferences.add.diluent_does_not_exist_or_inactive"));
			}
		} 
		
		
		/*
		 * Container - This mandatory for products that are not "Formulations". Need to check if the chosen Container 
		 * is present in the system and is active.  
		 */
		if (ShotUtils.isFormulationProductType(customerPreferenceDTO.getProductType()) == false) {
			if (customerPreferenceDTO.getDeliveryMechanism() != null && 
				customerPreferenceDTO.getDeliveryMechanism().getContainer()!= null) {
				String containerKey = customerPreferenceDTO.getDeliveryMechanism().getContainer().getStockKey();
				Container container = containerRepository.findActiveContainerByKey(containerKey); 
				if ( container == null) {
					logger.warn("Failed validation. Missing Container. ");
					validationErrors.add(env.getProperty("error.customer.preferences.add.container_does_not_exist_or_inactive"));									
				} 
			} else {
				logger.warn("Failed validation. Missing Container. ");
				validationErrors.add(env.getProperty("error.customer.preferences.add.container_does_not_exist_or_inactive"));
			}
		}
		
		
		/*
		 * RoA - RoA is mandatory for products that are not "Formulations". Need to check if the chosen RoA is present 
		 * in the system and is active.  
		 */
		
		if (ShotUtils.isFormulationProductType(customerPreferenceDTO.getProductType()) == false) {
			if (customerPreferenceDTO.getAdministrationRoute() != null) {
				String roaKey = customerPreferenceDTO.getAdministrationRoute().getCode();
				AdministrationRoute adminRoute = adminstrationRouteRepository.findActiveAdministrationRouteByCode(roaKey);
				if (adminRoute == null) {					
					logger.warn("Failed validation. Missing Route of Administration. ");
					validationErrors.add(env.getProperty("error.customer.preferences.add.roa_does_not_exist_or_inactive"));													
				} else {
					customerPreference.setAdministrationRoute(adminRoute);
				}
			} else {				
				logger.warn("Failed validation. Missing Route of Administration. ");
				validationErrors.add(env.getProperty("error.customer.preferences.add.roa_does_not_exist_or_inactive"));										
			}
		}
		

		if (validationErrors.size() > 0 ) {
			String errorMessages = validationErrors.stream()
												   .reduce((msg, nextMsg) -> {
													   return nextMsg += "<br/>" + msg;
												   }).get();
			throw new ShotServiceException(errorMessages);
		}
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.telstra.health.shot.service.CustomerPreferencesService#deleteCustomerPreferences(java.util.List)
	 */
	@Override
	public void deleteCustomerPreferences(List<CustomerPreferenceDTO> customerPreferencesDTO)
			throws ShotServiceException {
		
		if (customerPreferencesDTO == null || customerPreferencesDTO.size() <= 0) {
			/* Nothing to delete */
			logger.debug("The size of customer preferences to be deledted is 0. Nothing to delete");
			return;
		}
		
		/* Convert the DTOs to entity */
		List<CustomerPreference> customerPreferencesToBedeleted = customerPreferencesDTO.stream()				
																		    .map( cpDTO -> {
																		    	if(null == cpDTO ) return null;
																				else{
																					
																					CustomerPreference customerPreference = modelMapper.map(cpDTO, CustomerPreference.class);
																					
																				    return customerPreference;
																				}
																		    }).collect(Collectors.toList());
		
		/* Delete the DTOs. */
		this.customerPreferencesRepository.deleteCustomerPreferences(customerPreferencesToBedeleted);
		
		/*
		 * Get the remaining Customer Preferences and recalculate their Ranks
		 */		
		CustomerPreferenceDTO searchKey = customerPreferencesDTO.get(0);
		List<CustomerPreference> remainingCustomerPreferences 
					= this.customerPreferencesRepository.searchCustomerPreferences(searchKey.getCustomerKey(), 
																				   searchKey.getProductDescription(),
																				   searchKey.getProductType(),
																				   searchKey.getBatDrugKey(),
																				   searchKey.getBatDSUKey(),
																				   searchKey.getTriKey(),
																				   searchKey.getMsoIngStkKey(),
																				   searchKey.getBatFormulation());
		
		/* Re-compute the ranks of the remaining Customer Preferences. */
		boolean hasChanged = this.recomputeRanks(remainingCustomerPreferences);
		
		/*
		 * Only do a save operation if the any of Customer Preferences have changed. 
		 */
		if (hasChanged) {
			this.customerPreferencesRepository.save(remainingCustomerPreferences);
		}					  
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.telstra.health.shot.service.CustomerPreferencesService#recomputeRanks(java.util.List)
	 */
	public boolean recomputeRanks(List<CustomerPreference> remainingCustomerPreferences) {
		
		/*
		 * This local variable is used to hold the previous ranks. AtomicInteger is used
		 * because normal local variables e.g. int cant be access from within 
		 * lambda expressions.
		 */
		AtomicInteger previousRank = new AtomicInteger(0);
		
		/*
		 * Flag that will determine if the ranks have changed or not.
		 */
		AtomicBoolean hasChanged = new AtomicBoolean(false);
		
		/*
		 * Scan thru all the Customer Preferences and then set the rank in order
		 * if applicable.
		 */
		remainingCustomerPreferences.forEach( (cp) ->  {		
				int rank = previousRank.incrementAndGet();
				if (cp.getRank() != rank ) {
					cp.setRank(rank);
					hasChanged.set(true);
				}
			});
		
		return hasChanged.get();
	}

	@Override
	public List<CustomerPreferenceDTO> updateCustomerPreferences(List<CustomerPreferenceDTO> customerPreferencesDTO)
			throws ShotServiceException {
		
		List<CustomerPreferenceDTO> updatedCustomerPreferences = new ArrayList<>();
		
		customerPreferencesDTO.forEach(
			customerPreferenceDTO -> {
				
				CustomerPreference customerPreferenceToBeUpdated = this.customerPreferencesRepository.getOne(customerPreferenceDTO.getPrefId());
				if (customerPreferenceToBeUpdated != null) {
					
					customerPreferenceToBeUpdated.setRank(customerPreferenceDTO.getRank());
					customerPreferenceToBeUpdated.setUpdatedBy(customerPreferenceDTO.getUpdatedBy());
					customerPreferenceToBeUpdated.setUpdatedDate(Timestamp.from(Instant.now()));
					
					CustomerPreference customerPreferenceToBeNew = this.customerPreferencesRepository.updateCustomerPreference(customerPreferenceToBeUpdated);
					
					updatedCustomerPreferences.add(this.modelMapper.map(customerPreferenceToBeNew, CustomerPreferenceDTO.class));
				}
			}
	    );
		
		return updatedCustomerPreferences;
	}

	/* (non-Javadoc)
	 * @see com.telstra.health.shot.service.CustomerPreferencesService#getMatchingCustomerPreference(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, double, java.lang.String, java.lang.String, double, java.lang.String, double)
	 */
	@Override
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
															   String infusionDuration) throws ShotServiceException,com.telstra.health.shot.dao.exception.EntityNotFoundException {
		
		try {
			CustomerPreference customerPreference 
				= this.customerPreferencesRepository.getMatchingCustomerPreference(customerId,
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
			

			CustomerPreferenceDTO customerPreferenceDTO 
				= modelMapper.map(customerPreference, CustomerPreferenceDTO.class);
											
																				    
			
			return customerPreferenceDTO;
		} catch (DataAccessException e) {
			logger.error("Failed to get matching customer preference for customerId {}.", customerId, e);
			throw new ShotServiceException("Failed to get matching customer preference.");
		} catch (NoResultException e) {			
			throw new com.telstra.health.shot.dao.exception.EntityNotFoundException(env.getProperty("error.customer.preferences.update.record_does_not_exist"));
		}	
	}

}
