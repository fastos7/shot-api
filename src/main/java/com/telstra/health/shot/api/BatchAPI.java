package com.telstra.health.shot.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.telstra.health.shot.api.exception.ApiException;
import com.telstra.health.shot.api.response.APIResponse;
import com.telstra.health.shot.api.response.BatchAPIResponse;
import com.telstra.health.shot.dto.BatchDTO;
import com.telstra.health.shot.dto.ShotBatchDTO;
import com.telstra.health.shot.service.OrderService;
import com.telstra.health.shot.service.exception.ShotServiceException;

@RestController
@RequestMapping("/api/customers/{customerId}/order/batch")
public class BatchAPI {

	private static final Logger logger = LoggerFactory.getLogger(BatchAPI.class);

	@Autowired
	OrderService orderService;

	// this is the entry point for the Batch API in order to determine the
	// ingredients for a batch
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/ingredients/", method = RequestMethod.POST)
	public ResponseEntity calculateIngredients(@PathVariable String customerId, @RequestBody BatchDTO batchDto) {

		try {
			logger.info("Calculating the ingredient list for : {} for the batch: {}. ", customerId,
					batchDto.toString());
			BatchAPIResponse batchAPIResponse = new BatchAPIResponse();
			// setting the customer key so that can be used while determining the charge for
			// ingredients
			batchDto.setBatCusKey(customerId);
			boolean returnValue = orderService.calculateIngredients(batchDto, batchAPIResponse);
			if (returnValue == true) {
				logger.info("Successfully created the batch: {} for customer : {} in the calculateIngredients()",
						customerId, batchAPIResponse.getBatchId());
				return new ResponseEntity(
						new BatchAPIResponse(batchAPIResponse.getBatchId(), batchAPIResponse.getErrorCode(),
								batchAPIResponse.getShotSideMessage(), batchAPIResponse.getShotCancelSave()),
						HttpStatus.OK);
			}

		}

		catch (ShotServiceException e) {
			// TODO Auto-generated catch block
			logger.error("Error in calculating the ingredients for the batch for customer {} . Error : {}", customerId,
					e.getMessage());
			throw new ApiException(e.getMessage(), e.getErrorCode());
		}
		return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/cancel/{shotBacthId}", method = RequestMethod.POST)
	public ResponseEntity cancelBatch(@PathVariable String customerId, @PathVariable Long shotBacthId,
			@RequestBody ShotBatchDTO shotBatchDto) throws ShotServiceException {
		try {
			boolean updateReturn = orderService.cancelBatch(customerId, shotBacthId, shotBatchDto.getBatchId(),
					shotBatchDto.getOrderId(), shotBatchDto.getOrdNo(), shotBatchDto.getUpdatedBy());
			if (updateReturn == true) {
				logger.info(
						"Successfully cancelled the SHOT batch for the customer : {} with shot batch id :{} in the cancelOrder()",
						customerId, shotBatchDto.getShotBatchId());
				return new ResponseEntity(new APIResponse("Success", "Shot Batch has been cancelled successfully"),
						HttpStatus.OK);
			}

		} catch (ShotServiceException e) {
			// TODO Auto-generated catch block
			logger.error(
					"Error in cancelling the SHOT batch for customer {},with shot batch id :{} in the cancelOrder() due to the following Error : {}",
					customerId, shotBatchDto.getShotBatchId(), e.getMessage());
			throw new ApiException(e.getMessage(), e.getErrorCode());
		}
		return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{shotBacthId}", method = RequestMethod.PUT)
	public ResponseEntity updateBatch(@PathVariable String customerId, @RequestBody ShotBatchDTO shotBatch)
			throws ShotServiceException {
		try {
			boolean updateReturn = orderService.updateOrderandBatch(shotBatch);
			if (updateReturn == true) {
				logger.info("Successfully updated the SHOT batch for the customer : {} in the updateBatch()",
						customerId);
				return new ResponseEntity(new APIResponse("Success", "Order has been updated successfully"),
						HttpStatus.OK);
			}
			
		} catch (ShotServiceException e) {
			// TODO Auto-generated catch block
			logger.error("Error in updating the SHOT batch for customer {}, Error : {}", customerId, e.getMessage());
			throw new ApiException(e.getMessage(), e.getErrorCode());
		}
		return new ResponseEntity(new APIResponse("Failure", "Unable to update the order"),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{batchId}", method = RequestMethod.POST)
	public ResponseEntity updateOnHoldBatch(@PathVariable String customerId, @RequestBody ShotBatchDTO shotBatch)
			throws ShotServiceException {
		try {
			boolean updateReturn = orderService.updateOnHoldBatch(shotBatch);
			if (updateReturn == true) {
				logger.info("Successfully updated the Axis batch {} for the customer : {} in the updateOnHoldBatch()",
						shotBatch.getBatchId(), customerId);
				return new ResponseEntity(new APIResponse("Success", "Axis batch On hold has been updated successfully"),
						HttpStatus.OK);
			}
			
		} catch (ShotServiceException e) {
			// TODO Auto-generated catch block
			logger.error("Error in updating the Axis On hold batch: {} for customer {}, Error : {}", shotBatch.getBatchId(),customerId, e.getMessage());
			throw new ApiException(e.getMessage(), e.getErrorCode());
		}
		return new ResponseEntity(new APIResponse("Failure", "Unable to update the On hold batch"),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
