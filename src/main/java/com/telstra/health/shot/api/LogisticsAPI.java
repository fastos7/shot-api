package com.telstra.health.shot.api;

import static org.springframework.http.HttpStatus.OK;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.telstra.health.shot.api.exception.ApiException;
import com.telstra.health.shot.dto.DeliveryGroupDTO;
import com.telstra.health.shot.dto.DeliveryRunRequestDTO;
import com.telstra.health.shot.service.LogisticsService;

/**
 * Rest API for getting Delivery Date times of either a new Order Item or an existing order item.
 * @author osama.shakeel
 *
 */
@RestController
@RequestMapping("/api/customers/{customerKey}/logistics/")
public class LogisticsAPI {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("logisticsService")
	private LogisticsService logisticsService;

	@PostMapping(produces = "application/json")
	@ResponseStatus(OK)
	public @ResponseBody DeliveryGroupDTO getDeliveryDateTimes(
			@PathVariable String customerKey,
			@RequestBody DeliveryRunRequestDTO deliveryRunRequestDTO) {

		try {
			return logisticsService.calculateOrderDeliveryDateTimes(deliveryRunRequestDTO);
		} catch (Exception ex) {
			logger.error("Error occurred in getting Delivery Runs list", ex);
			throw new ApiException(ex.getMessage());
		}
	}
	
}
