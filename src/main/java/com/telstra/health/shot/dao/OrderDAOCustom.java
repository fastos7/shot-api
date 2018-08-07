package com.telstra.health.shot.dao;

import java.sql.Timestamp;
import java.util.List;

import com.telstra.health.shot.dao.exception.DataAccessException;
import com.telstra.health.shot.dto.BatchDTO;
import com.telstra.health.shot.dto.OrderHistoryDTO;
import com.telstra.health.shot.dto.ShotBatchDTO;

public interface OrderDAOCustom {

	BatchDTO calculateIngredients(BatchDTO batchdto) throws DataAccessException;

	List<OrderHistoryDTO> getOrderHistoryList(String customerId, String patientId);

	String GenerateKey(String entityName, String entityKey, int userId);

	boolean updateBatchComments(String customerID, Long shotBatchId, String newComments, String batchId,
			Timestamp updateDateTime);

	boolean updateBatchIngredients(String batchKey);

	boolean updateOrderStatus(String orderKey);

}
