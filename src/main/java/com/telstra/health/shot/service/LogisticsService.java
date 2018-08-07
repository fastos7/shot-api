package com.telstra.health.shot.service;

import java.util.List;

import com.telstra.health.shot.common.enums.OrderType;
import com.telstra.health.shot.dto.DeliveryGroupDTO;
import com.telstra.health.shot.dto.DeliveryLocationDTO;
import com.telstra.health.shot.dto.DeliveryRunRequestDTO;

public interface LogisticsService {

	DeliveryGroupDTO calculateOrderDeliveryDateTimes(
			DeliveryRunRequestDTO deliveryRunRequestDTO);
	
	List < DeliveryLocationDTO > findDeliveryLocations(String customerKey);
}
