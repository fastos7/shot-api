package com.telstra.health.shot.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.telstra.health.shot.dao.exception.DataAccessException;
import com.telstra.health.shot.entity.Trial;
import com.telstra.health.shot.service.exception.ShotServiceException;

@Repository
public class TrialsDAOImpl implements TrialsDAOCustom {
	
	private static final Logger logger = LoggerFactory.getLogger(TrialsDAOImpl.class);
	
	@PersistenceContext
    private EntityManager entityManager; 
	
	@Override
	public List<Trial> searchTrials(String customerId, String searchStr)
			throws ShotServiceException {
		
		logger.debug("Getting trials for customerId [{}] and search string [{}].",customerId,searchStr);
		
		try {
					 														   
			CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
			CriteriaQuery<Trial> criteria = builder.createQuery(Trial.class);
			Root<Trial> trialRoot = criteria.from(Trial.class);
			
			/* Left outer Join Clauses */
			@SuppressWarnings("rawtypes")
			Join triallines = (Join) trialRoot.fetch("trialLines",JoinType.LEFT);
			
			/* Build the condition or where clause*/
			List<Predicate> predicates = new ArrayList<Predicate>();
			
			/* Non-nullable conditions */
			predicates.add(builder.like(trialRoot.get("name"), "%"+searchStr+"%"));
			predicates.add(builder.equal(triallines.get("cusKey"), customerId));
			predicates.add(builder.equal(triallines.get("active"), "A"));			
			predicates.add(builder.equal(trialRoot.get("active"), "A"));
			
			/* Put them all together */
			criteria.select(trialRoot)
					.where(predicates.toArray(new Predicate[predicates.size()]))
					.orderBy(builder.asc(trialRoot.get("name")));
			
			/* Finally run the query */
			return this.entityManager.createQuery(criteria)
									 .getResultList();
			
		} catch (Exception e) {
			logger.error("Failed to get customer trials for customerId [{}] and search string [{}]. ", customerId,searchStr, e);
			throw new DataAccessException(e.getMessage());
		}
		
		
	}

}
