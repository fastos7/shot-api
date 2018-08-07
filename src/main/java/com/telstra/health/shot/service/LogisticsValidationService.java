package com.telstra.health.shot.service;

import java.util.List;

import com.telstra.health.shot.dto.OrderDTO;
import com.telstra.health.shot.dto.ShotBatchPatientDTO;
import com.telstra.health.shot.dto.ShotBatchTempDTO;

public interface LogisticsValidationService {

	List < String > validateOrderDeliveryRuns(List<OrderDTO> orderDTOList);
	
	List<String> validateEditBatchDeliveryRun(ShotBatchTempDTO shotBatchDTO, ShotBatchPatientDTO patientDTO, OrderDTO orderDTO);
}
