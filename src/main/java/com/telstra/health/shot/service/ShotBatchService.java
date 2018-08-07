package com.telstra.health.shot.service;

import java.util.Date;
import java.util.List;

import com.telstra.health.shot.dto.AxisBatchAuditDTO;
import com.telstra.health.shot.dto.ShotBatchDTO;
import com.telstra.health.shot.entity.ShotBatch;
import com.telstra.health.shot.service.exception.ShotServiceException;

public interface ShotBatchService {
	
	/**
	 * This method is to return SHOT batches. This method is called to display batches on Week View and Day View screens.
	 * 
	 * @param customerId: get the batches for this customer.
	 * @param date: get he SHOT batches starting from this date.
	 * @param week: boolean; if true return the data for the week starting from given date
	 * @param orderByDeliveryTime: boolean; if true order the returned records by DeliveryTime; if false, order by TreatmentTime
	 * @return returns an ordered list of SHOT batches 
	 */
	public List<ShotBatchDTO> getShotBatches(String customerId, Date date, boolean week, boolean orderByDeliveryTime) throws ShotServiceException;

	public List<ShotBatch> getShotBatchesNew(String customerId, Date date, boolean week, boolean orderByDeliveryTime) throws ShotServiceException;
	
	public ShotBatchDTO getShotBatch(String customerId, Integer batchId) throws ShotServiceException;

	public List<AxisBatchAuditDTO> getBatchHistories(Long batchId) throws ShotServiceException;

}


