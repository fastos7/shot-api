package com.telstra.health.shot.service;

import java.util.List;

import com.telstra.health.shot.api.response.BatchAPIResponse;
import com.telstra.health.shot.dto.BatchDTO;
import com.telstra.health.shot.dto.OrderDTO;
import com.telstra.health.shot.dto.OrderHistoryDTO;
import com.telstra.health.shot.dto.ShotBatchDTO;
import com.telstra.health.shot.dto.ShotBatchPatientDTO;
import com.telstra.health.shot.dto.ShotBatchTempDTO;
import com.telstra.health.shot.service.exception.ShotServiceException;

public interface OrderService {

	boolean calculateIngredients(BatchDTO batchdto, BatchAPIResponse batchAPIResponse) throws ShotServiceException;

	boolean saveOrder(List<OrderDTO> orderDto) throws ShotServiceException;

	List<OrderHistoryDTO> getOrderHistoryList(String customerId, String patientId);

	BatchDTO getBatchdetails(long batchId);

	boolean updateOrderandBatch(ShotBatchDTO shotBatchDto) throws ShotServiceException;

	BatchDTO setBatchDtoProperities(ShotBatchTempDTO shotBatchTemp, ShotBatchPatientDTO shotBatchPatients);

	boolean cancelBatch(String customerId, Long shotBatchId, String batchId, String orderId, String ordNo, int updatedBy);

	boolean updateOnHoldBatch(ShotBatchDTO shotBatchDto) throws ShotServiceException;

}
