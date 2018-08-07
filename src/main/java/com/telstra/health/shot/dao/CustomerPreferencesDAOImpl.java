package com.telstra.health.shot.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.telstra.health.shot.dao.exception.DataAccessException;
import com.telstra.health.shot.entity.CustomerPreference;
import com.telstra.health.shot.entity.DeliveryMechanisms;
import com.telstra.health.shot.util.ShotUtils;

/**
 * This is the repository for all customer preferences related. 
 * 
 * @author Marlon Cenita
 *
 */
@Repository
public class CustomerPreferencesDAOImpl implements CustomerPreferencesDAOCustom {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomerPreferencesDAOImpl.class);
	
	@PersistenceContext
    private EntityManager entityManager; 
	
	/*
	 * (non-Javadoc)
	 * @see com.telstra.health.shot.dao.CustomerPreferencesRepositoryCustom#getAllCustomerPreferences(java.lang.String, java.lang.String)
	 */
	@Override
	public List<CustomerPreference> searchCustomerPreferences(String customerId, 
															  String productDescription,
															  String productType,
															  String batDrugKey,
															  String batDSUKey,
															  String triKey,
															  String msoIngStkKey,
															  String batFormulation)
															throws DataAccessException {
		
		logger.debug("Trying to get customer preferences for customerId {}.",customerId);
		try {
					 														   
			CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
			CriteriaQuery<CustomerPreference> criteria = builder.createQuery(CustomerPreference.class);
			Root<CustomerPreference> customerPreferencesRoot = criteria.from(CustomerPreference.class);
			
			/* Left outer Join Clauses */
			customerPreferencesRoot.fetch("administrationRoute",JoinType.LEFT);
			Fetch<CustomerPreference,DeliveryMechanisms> deliveryMechanism = customerPreferencesRoot.fetch("deliveryMechanism",JoinType.LEFT);
			deliveryMechanism.fetch("diluent",JoinType.LEFT);
			deliveryMechanism.fetch("container",JoinType.LEFT);
			
			/* Build the condition or where clause*/
			List<Predicate> predicates = new ArrayList<Predicate>();
			
			/* Non-nullable conditions */
			predicates.add(builder.equal(customerPreferencesRoot.get("customerKey"), customerId));
			predicates.add(builder.equal(customerPreferencesRoot.get("productDescription"), productDescription));
			predicates.add(builder.equal(customerPreferencesRoot.get("productType"), productType));
			
			/* */
			if (ShotUtils.isFormulationProductType(productType) == false) {
				predicates.add(builder.equal(customerPreferencesRoot.get("administrationRoute").get("codeType"), "ADMINROUTE"));
			} else {				
				predicates.add(builder.isNull(customerPreferencesRoot.get("administrationRoute")));
			}
			
			/* Nullable conditions */
			if (ShotUtils.isEmpty(batDrugKey)) {
				predicates.add(builder.isNull(customerPreferencesRoot.get("batDrugKey")));
			} else {
				predicates.add(builder.equal(customerPreferencesRoot.get("batDrugKey"), batDrugKey));
			}
			
			if (ShotUtils.isEmpty(triKey)) {
				predicates.add(builder.isNull(customerPreferencesRoot.get("triKey")));
			} else {
				predicates.add(builder.equal(customerPreferencesRoot.get("triKey"), triKey));
			}
			
			if (ShotUtils.isEmpty(msoIngStkKey)) {
				predicates.add(builder.isNull(customerPreferencesRoot.get("msoIngStkKey")));
			} else {
				predicates.add(builder.equal(customerPreferencesRoot.get("msoIngStkKey"), msoIngStkKey));
			}
			
			if (ShotUtils.isEmpty(batFormulation)) {
				predicates.add(builder.isNull(customerPreferencesRoot.get("batFormulation")));
			} else {
				predicates.add(builder.equal(customerPreferencesRoot.get("batFormulation"), batFormulation));
			}
			
			/* Put them all together */
			criteria.select(customerPreferencesRoot)
					.where(predicates.toArray(new Predicate[predicates.size()]))
					.orderBy(builder.asc(customerPreferencesRoot.get("rank")));
			
			/* Finally run the query */
			return this.entityManager.createQuery(criteria)
									 .getResultList();
			
		} catch (Exception e) {
			logger.error("Failed to get customer preferences for customerId {}. ", customerId, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.telstra.health.shot.dao.CustomerPreferencesRepositoryCustom#getCustomerPreference(java.lang.Integer)
	 */
	@Override
	public CustomerPreference getCustomerPreference(Integer prefId) throws DataAccessException {
		
		logger.debug("Trying to get customer preferences with id : {}.",prefId);
		
		try {
			
			CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
			CriteriaQuery<CustomerPreference> criteria = builder.createQuery(CustomerPreference.class);
			Root<CustomerPreference> customerPreferencesRoot = criteria.from(CustomerPreference.class);
			
			/* Left outer Join Clauses */
			customerPreferencesRoot.fetch("administrationRoute",JoinType.LEFT);
			Fetch<CustomerPreference,DeliveryMechanisms> deliveryMechanism = customerPreferencesRoot.fetch("deliveryMechanism",JoinType.LEFT);
			deliveryMechanism.fetch("diluent",JoinType.LEFT);
			deliveryMechanism.fetch("container",JoinType.LEFT);
			
			/* Build the condition or where clause*/
			List<Predicate> predicates = new ArrayList<Predicate>();
			
			/* Where clause */
			predicates.add(builder.equal(customerPreferencesRoot.get("prefId"), prefId));
			
			
			/* Put them all together */
			criteria.select(customerPreferencesRoot)
					.where(predicates.toArray(new Predicate[predicates.size()]));
			
			/* Finally run the query */
			return (CustomerPreference ) this.entityManager.createQuery(criteria)
									 					   .getSingleResult();

		} catch (Exception e) {
			logger.error("Failed to get customer preferences with id {} . Error : {}", prefId, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.telstra.health.shot.dao.CustomerPreferencesRepositoryCustom#deleteCustomerPreferences(java.util.List)
	 */
	@Transactional
	@Override
	public void deleteCustomerPreferences(List<CustomerPreference> customerPreferences) throws DataAccessException {
		
		customerPreferences.forEach(cp -> {
			
			try {
				this.entityManager.createQuery("DELETE from CustomerPreference c where prefId = :prefId")
								  .setParameter("prefId", cp.getPrefId())
								  .executeUpdate();
			} catch (Exception e) {
				logger.error("Failed to delete customer preferences with id {} . Error : {}", cp.getPrefId(), e);
				throw new DataAccessException(e.getMessage());
			}
			
		});
	}

	/*
	 * (non-Javadoc)
	 * @see com.telstra.health.shot.dao.CustomerPreferencesDAOCustom#getMaxRank(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Integer getMaxRank(String customerId, 
							  String productDescription, 
							  String productType, 
							  String batDrugKey,
							  String batDSUKey, 
							  String triKey, 
							  String msoIngStkKey, 
							  String batFormulation) throws DataAccessException {

		if (logger.isDebugEnabled()) {
			logger.debug("Trying to get customer max rank for :");
			logger.debug("     customerId : {}",customerId);
			logger.debug("     productDescription : {}",productDescription);
			logger.debug("     productType : {}",productType);
			logger.debug("     batDrugKey : {}",batDrugKey);
			logger.debug("     batDSUKey : {}",batDSUKey);
			logger.debug("     triKey : {}",triKey);
			logger.debug("     batFormulation : {}",batFormulation);
		}
		
		try {
					 														   
			CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
			CriteriaQuery<Integer> criteria = builder.createQuery(Integer.class);
			Root<CustomerPreference> customerPreferencesRoot = criteria.from(CustomerPreference.class);
			
			/* Build the condition or where clause*/
			List<Predicate> predicates = new ArrayList<Predicate>();
			
			/* Non-nullable conditions */
			predicates.add(builder.equal(customerPreferencesRoot.get("customerKey"), customerId));
			predicates.add(builder.equal(customerPreferencesRoot.get("productDescription"), productDescription));
			predicates.add(builder.equal(customerPreferencesRoot.get("productType"), productType));
			
			/* Nullable conditions */
			if (ShotUtils.isEmpty(batDrugKey)) {
				predicates.add(builder.isNull(customerPreferencesRoot.get("batDrugKey")));
			} else {
				predicates.add(builder.equal(customerPreferencesRoot.get("batDrugKey"), batDrugKey));
			}
			
			if (ShotUtils.isEmpty(triKey)) {
				predicates.add(builder.isNull(customerPreferencesRoot.get("triKey")));
			} else {
				predicates.add(builder.equal(customerPreferencesRoot.get("triKey"), triKey));
			}
			
			if (ShotUtils.isEmpty(msoIngStkKey)) {
				predicates.add(builder.isNull(customerPreferencesRoot.get("msoIngStkKey")));
			} else {
				predicates.add(builder.equal(customerPreferencesRoot.get("msoIngStkKey"), msoIngStkKey));
			}
			
			if (ShotUtils.isEmpty(batFormulation)) {
				predicates.add(builder.isNull(customerPreferencesRoot.get("batFormulation")));
			} else {
				predicates.add(builder.equal(customerPreferencesRoot.get("batFormulation"), batFormulation));
			}
			
			/* Put them all together */
			criteria.select(builder.coalesce(builder.max(customerPreferencesRoot.get("rank")),0))
			        .where(predicates.toArray(new Predicate[predicates.size()]));
			
			/* Finally run the query */
			return (Integer)this.entityManager.createQuery(criteria)
									 .getSingleResult();
			
		} catch (Exception e) {
			logger.error("Failed to get customer preferences for customerId {}. ", customerId, e);
			throw new DataAccessException(e.getMessage());
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.telstra.health.shot.dao.CustomerPreferencesDAOCustom#updateCustomerPreference(com.telstra.health.shot.entity.CustomerPreference)
	 */
	@Transactional
	@Override
	public CustomerPreference updateCustomerPreference(CustomerPreference customerPreference)
			throws DataAccessException {
		
		return this.entityManager.merge(customerPreference);
	}

	/*
	 * (non-Javadoc)
	 * @see com.telstra.health.shot.dao.CustomerPreferencesDAOCustom#getMatchingCustomerPreference(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public CustomerPreference getMatchingCustomerPreference(String customerId, 
															String productDescription,
															String productType, 
															String batDrugKey, 
															String batDSUKey, 
															String triKey, 
															String msoIngStkKey,
															String batFormulation, 
															String dose, 
															String deliveryMechanismKey, 
															String route, 
															String volume, 
															String exact,
															String infusionDuration) throws DataAccessException,NoResultException {
		logger.debug("Trying to get customer preferences for customerId {}.",customerId);
		try {
					 														   
			CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
			CriteriaQuery<CustomerPreference> criteria = builder.createQuery(CustomerPreference.class);
			Root<CustomerPreference> customerPreferencesRoot = criteria.from(CustomerPreference.class);
			
			/* Left outer Join Clauses */
			customerPreferencesRoot.fetch("administrationRoute",JoinType.LEFT);
			Fetch<CustomerPreference,DeliveryMechanisms> deliveryMechanism = customerPreferencesRoot.fetch("deliveryMechanism",JoinType.LEFT);
			deliveryMechanism.fetch("diluent",JoinType.LEFT);
			deliveryMechanism.fetch("container",JoinType.LEFT);
			
			/* Build the condition or where clause*/
			List<Predicate> predicates = new ArrayList<Predicate>();
			
			/* Non-nullable conditions */
			predicates.add(builder.equal(customerPreferencesRoot.get("customerKey"), customerId));
			predicates.add(builder.equal(customerPreferencesRoot.get("productDescription"), productDescription));
			predicates.add(builder.equal(customerPreferencesRoot.get("productType"), productType));
			
			/* Nullable conditions */
			if (ShotUtils.isEmpty(batDrugKey)) {
				predicates.add(builder.isNull(customerPreferencesRoot.get("batDrugKey")));
			} else {
				predicates.add(builder.equal(customerPreferencesRoot.get("batDrugKey"), batDrugKey));
			}
			
			if (ShotUtils.isEmpty(triKey)) {
				predicates.add(builder.isNull(customerPreferencesRoot.get("triKey")));
			} else {
				predicates.add(builder.equal(customerPreferencesRoot.get("triKey"), triKey));
			}
			
			if (ShotUtils.isEmpty(msoIngStkKey)) {
				predicates.add(builder.isNull(customerPreferencesRoot.get("msoIngStkKey")));
			} else {
				predicates.add(builder.equal(customerPreferencesRoot.get("msoIngStkKey"), msoIngStkKey));
			}
			
			if (ShotUtils.isEmpty(batFormulation)) {
				predicates.add(builder.isNull(customerPreferencesRoot.get("batFormulation")));
			} else {
				predicates.add(builder.equal(customerPreferencesRoot.get("batFormulation"), batFormulation));
			}
			
			/* Match all preferences whose doseFrom >= dose < doseTo
			 * or all preferences which doesn't have doseFrom and doseTo
			 */
			
			predicates.add(builder.or(
					
					builder.and(builder.lessThanOrEqualTo(customerPreferencesRoot.get("doseFrom"), Double.parseDouble(dose)), 
							    builder.greaterThan(customerPreferencesRoot.get("doseTo"), Double.parseDouble(dose))), 
					
					builder.and(builder.isNull(customerPreferencesRoot.get("doseFrom")), 
								builder.isNull(customerPreferencesRoot.get("doseTo")))
					)
			);
			
			
			/* Delivery mechanism */
			if (ShotUtils.isEmpty(deliveryMechanismKey) == false) {				
				predicates.add(builder.equal(customerPreferencesRoot.get("deliveryMechanism").get("key"), deliveryMechanismKey));
			}
			
			/* Administration Route */
			if (ShotUtils.isEmpty(route) == false) {				
				predicates.add(builder.equal(customerPreferencesRoot.get("administrationRoute").get("code"), route));
			}
			
			/* Volume */
			if (ShotUtils.isEmpty(volume) == false) {				
				predicates.add(builder.equal(customerPreferencesRoot.get("volume"), Double.parseDouble(volume)));
			}
			
			/* Volume */
			if (ShotUtils.isEmpty(exact) == false) {				
				predicates.add(builder.equal(customerPreferencesRoot.get("exact"), exact.charAt(0)));
			}
			
			/* Infusion Duration */
			if (ShotUtils.isEmpty(infusionDuration) == false) {				
				predicates.add(builder.equal(customerPreferencesRoot.get("infusionDuration"), Double.parseDouble(infusionDuration)));
			}
			
			/* Put them all together */
			
			criteria.select(customerPreferencesRoot)
					.where(predicates.toArray(new Predicate[predicates.size()]))
					.orderBy(builder.asc(customerPreferencesRoot.get("rank")));
			
			return  this.entityManager.createQuery(criteria)
									  .setFirstResult(0)
									  .setMaxResults(1)
									  .getSingleResult();
			
		} catch (NoResultException e) {			
			throw new NoResultException(e.getMessage());
		} catch (Exception e) {
			logger.error("Failed to get customer preferences for customerId {}. ", customerId, e);
			throw new DataAccessException(e.getMessage());
		}
	}

}
