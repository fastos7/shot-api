package com.telstra.health.shot.api;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.telstra.health.shot.api.exception.ApiException;
import com.telstra.health.shot.api.response.APIResponse;
import com.telstra.health.shot.common.enums.ShotErrors;
import com.telstra.health.shot.dto.BatchDTO;
import com.telstra.health.shot.dto.OrderDTO;
import com.telstra.health.shot.dto.OrderHistoryDTO;
import com.telstra.health.shot.dto.ShotBatchDTO;
import com.telstra.health.shot.dto.ShotBatchPatientDTO;
import com.telstra.health.shot.dto.ShotBatchTempDTO;
import com.telstra.health.shot.service.LogisticsValidationService;
import com.telstra.health.shot.service.OrderService;
import com.telstra.health.shot.service.exception.ShotServiceException;

@RestController
@RequestMapping("/api/customers/{customerId}/orders/")
public class OrdersAPI {

	private static final Logger logger = LoggerFactory.getLogger(OrdersAPI.class);
	@Autowired
	OrderService orderService;

	@Autowired
	@Qualifier("logisticsValidationService")
	LogisticsValidationService logisticsValidationService;

	@Autowired
	private Environment env;

	// this is the entry point in order to create the Order.
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity createOrder(@PathVariable String customerId, @RequestBody List<OrderDTO> orderDtoList)
			throws ShotServiceException {

		// Validate Delivery Runs for the list of orders
		List<String> validationErrors = logisticsValidationService.validateOrderDeliveryRuns(orderDtoList);
		if (!validationErrors.isEmpty()) {
			throw new ApiException(validationErrors, ShotErrors.INVALID_DELV_DATE_TIME.getErrorCode());
		}
		try {
			// Retrieve the batch details before saving. Iterate through Order DTO
			// to patient collection and then get the batch id's

			logger.info("Creating the orders into the SHOT and Axis with Order information as: {}",
					orderDtoList.toString());
			for (OrderDTO orderDto : orderDtoList) {
				List<BatchDTO> batches = new ArrayList<>();
				for (ShotBatchPatientDTO shotBatchPatients : orderDto.getPatients()) {
					for (ShotBatchTempDTO shotBatchTemp : shotBatchPatients.getBatches()) {

						BatchDTO batchDto = orderService.setBatchDtoProperities(shotBatchTemp, shotBatchPatients);
						batches.add(batchDto);
						orderDto.setBatches(batches);
					}
				}
			}
			boolean returnValue = orderService.saveOrder(orderDtoList);
			if (returnValue == true) {
				logger.info("Successfully created the Order for the customer : {} in the createOrder()", customerId);
				return new ResponseEntity(new APIResponse("Success", "Order has been created successfully"),
						HttpStatus.OK);
			}

		} catch (ShotServiceException e) {
			// TODO Auto-generated catch block
			handleSaveException(e);

		}
		return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// this function will determine the cause behind the transaction failure and
	// return the appropriate message
	private void handleSaveException(Exception ex) throws ShotServiceException {
		if (ex instanceof DataIntegrityViolationException) {
			DataIntegrityViolationException diex = (DataIntegrityViolationException) ex;
			String errorMsg = diex.getMostSpecificCause().getMessage();

			if (StringUtils.isNotEmpty(errorMsg) && errorMsg.contains(env.getProperty("contrtaint.unique.ur"))
					&& errorMsg.contains(env.getProperty("error.duplicate.key")))

				throw new ShotServiceException(String.format(env.getProperty("error.unique.order"),
						errorMsg.split(env.getProperty("error.duplicate.key"))[1]));
			else if (StringUtils.isNotEmpty(errorMsg)
					&& errorMsg.contains(env.getProperty("error.order.duplicate.key"))) {

				throw new ShotServiceException(String.format(env.getProperty("error.unique.order"),
						errorMsg.split(env.getProperty("error.order.duplicate.key"))[1]));
			} else if (StringUtils.isNotEmpty(errorMsg) && errorMsg.contains(env.getProperty("error.order.input"))) {
				throw new ShotServiceException(String.format(env.getProperty("error.order.badinput")));
			} else if (StringUtils.isNotEmpty(errorMsg)
					&& errorMsg.contains(env.getProperty("error.order.invalidinput"))) {
				throw new ShotServiceException(String.format(env.getProperty("error.order.missinginput"),
						errorMsg.split(env.getProperty("error.order.invalidinput"))[1]));
			} else
				throw new ShotServiceException(env.getProperty("error.order.save"));
		} else if (ex instanceof ShotServiceException) {
			throw (ShotServiceException) ex;
		} else {
			throw new ShotServiceException(env.getProperty("error.order.save"));
		}

	}

	@GetMapping(value = "/history/", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<OrderHistoryDTO> getOrderHistory(@PathVariable("customerId") String customerId,
			@RequestParam(name = "patientId", required = false) String patientId) {

		return this.orderService.getOrderHistoryList(customerId, patientId);
	}

}
