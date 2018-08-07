package com.telstra.health.shot.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.telstra.health.shot.dao.exception.DataAccessException;
import com.telstra.health.shot.dto.AxisBatchAuditDTO;
import com.telstra.health.shot.entity.ShotBatch;
import com.telstra.health.shot.service.exception.ShotServiceException;

@Repository
public class ShotBatchDAOImpl implements ShotBatchDAOCustom {
	
	private static final Logger logger = LoggerFactory.getLogger(ShotBatchDAOImpl.class);
	
	@PersistenceContext
    private EntityManager entityManager; 

	@Override
	public List<ShotBatch> getShotBatches(String customerId, Date fromDate, Date toDate, boolean orderByDeliveryTime)
			throws ShotServiceException {
		try {
			
			String fetchBy = orderByDeliveryTime ? "DeliveryDateTime" : "TreatmentDateTime";

			String query = " FROM ShotBatch "
					     + " WHERE CustomerId = :customerId AND "
					     + fetchBy + " BETWEEN :fromDate AND :toDate "
					     + " ORDER BY " + fetchBy + ", BatchId";
			
			@SuppressWarnings("unchecked")
			List<ShotBatch> shotBatches = this.entityManager.createQuery(query)																			 
																			 .setParameter("customerId", customerId)
																			 .setParameter("fromDate", fromDate)
																			 .setParameter("toDate", toDate)
					 														 .getResultList();  
			
			return shotBatches;
		} catch (Exception e) {
			logger.error("Failed to get batches for customerId [{}].", customerId, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	@Override
	public ShotBatch getShotBatch(String customerId, Integer batchId) throws ShotServiceException {
		try {
			
			String query = " FROM ShotBatch "
					     + " WHERE CustomerId = :customerId AND "
					     + "       ShotBatchId = :shotBatchId";
			
			ShotBatch shotBatch = (ShotBatch) this.entityManager.createQuery(query)																			 
                                                                .setParameter("customerId", customerId)														    
													            .setParameter("shotBatchId", batchId)
													            .getSingleResult();
			
			return shotBatch;
		} catch (Exception e) {
			logger.error("Failed to get batches for customerId [{}].", customerId, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AxisBatchAuditDTO> getBatchHistories(Long batchId) throws ShotServiceException {
		
		logger.debug("Trying to get batch histories for batch [{}].",batchId);
		
		try {
			
			String query ="SELECT new com.telstra.health.shot.dto.AxisBatchAuditDTO("					 
						 + "		aba.axisBatchAuditIdentity.batKey,"
						 + "		aba.axisBatchAuditIdentity.batLastUpdWhen,"
						 + "		aba.shotBatchKey,"
						 + "		user.userId,"
						 + "		user.firtName,"
						 + "		user.lastName,"
						 + "		user.system,"
						 + "		aba.batQuantity,"
						 + "		aba.batDescription,"
						 + "		aba.batDose,"						 
						 + "		doseUnit.description,"						 
						 + "		aba.batDose2,"
						 + "		doseUnit2.description,"						 
						 + "		aba.batDose3,"
						 + "		doseUnit3.description,"						 
						 + "		aba.batSpecifiedVolume,"						 
						 + "		aba.batExact,"
						 + "		route.description,"
						 + "		aba.batTreatDate,"
						 + "		aba.batStatus,"
						 + "		aba.batShipDate,"
						 + "		aba.batComments) "
						 + "FROM AxisBatchAudit aba "
				 		 + "	LEFT JOIN aba.administrationRoute route "
						 + "	LEFT JOIN aba.batDoseUnits doseUnit  "
						 + "	LEFT JOIN aba.batDoseUnit2 doseUnit2  "
						 + "	LEFT JOIN aba.batDoseUnit3 doseUnit3  "
						 + "	LEFT JOIN aba.batLastUpdBy user  "
						 + "WHERE aba.shotBatchKey = :batchId "
						 + " 	AND (route IS NULL OR route.codeType = 'ADMINROUTE') "
						 + " 	AND (doseUnit IS NULL OR doseUnit.codeType = 'AMOUNTUNITS')"
						 + " 	AND (doseUnit2 IS NULL OR doseUnit2.codeType = 'AMOUNTUNITS')"
						 + " 	AND (doseUnit3 IS NULL OR doseUnit3.codeType = 'AMOUNTUNITS')"
						 + " ORDER BY aba.axisBatchAuditIdentity.batLastUpdWhen DESC";
				
			return this.entityManager.createQuery(query)
									 .setParameter("batchId", batchId)
									 .getResultList();
			
		} catch (Exception e) {
			logger.error("Failed to get batch histories for batch [{}]. ", batchId, e);
			throw new DataAccessException(e.getMessage());
		}
		
		
	}
}
