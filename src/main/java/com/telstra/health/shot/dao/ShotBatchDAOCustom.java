package com.telstra.health.shot.dao;

import java.util.Date;
import java.util.List;

import com.telstra.health.shot.dto.AxisBatchAuditDTO;
import com.telstra.health.shot.entity.ShotBatch;
import com.telstra.health.shot.service.exception.ShotServiceException;

public interface ShotBatchDAOCustom {

	public List<ShotBatch> getShotBatches(String customerId, Date fromDate, Date toDate, boolean orderByDeliveryTime) throws ShotServiceException;
	
	public ShotBatch getShotBatch(String customerId, Integer batchId) throws ShotServiceException;
	
	public List<AxisBatchAuditDTO> getBatchHistories(Long batchId) throws ShotServiceException;

}
