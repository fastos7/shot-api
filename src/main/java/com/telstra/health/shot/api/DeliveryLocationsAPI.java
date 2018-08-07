package com.telstra.health.shot.api;

import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.telstra.health.shot.dto.DeliveryLocationDTO;
import com.telstra.health.shot.service.LogisticsService;

/**
 * Rest API for getting Delivery Date times of either a new Order Item or an existing order item.
 * @author osama.shakeel
 *
 */
@RestController
@RequestMapping("/api/customers/{customerKey}/deliveryLocations/")
public class DeliveryLocationsAPI {

	@Autowired
	@Qualifier("logisticsService")
	private LogisticsService logisticsService;

	@GetMapping
	@ResponseStatus(OK)
	public @ResponseBody List < DeliveryLocationDTO > getDeliveryLocations(
			@PathVariable String customerKey) {
		return logisticsService.findDeliveryLocations(customerKey);
	}
}
