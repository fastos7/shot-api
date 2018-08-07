package com.telstra.health.shot.api;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.telstra.health.shot.api.exception.ApiException;
import com.telstra.health.shot.dto.AxisBatchAuditDTO;
import com.telstra.health.shot.dto.ShotBatchDTO;
import com.telstra.health.shot.service.ShotBatchService;
import com.telstra.health.shot.service.exception.ShotServiceException;

@RestController
@RequestMapping("/api/customers/{customerId}/batches/")
public class ShotBatchAPI {
	
	private static final Logger logger = LoggerFactory.getLogger(ShotBatchAPI.class);
	
	@Autowired
	ShotBatchService shotBatchService;

	/**
	 * API to get list of SHOT batches to be displayed in Week or Day view
	 * <p/>
	 * URI :
	 * <code>/shot/api/customers/{customerId}/batches?view=week,date=2017-12-28,orderBy=treatmentTime</code>
	 * <p/>
	 * METHOD : <code>HTTP GET</code>
	 * <p/>
	 * When successful it will return the list of SHOT batches along will all the
	 * details for a week or day based on Treatment Time or Delivery Time
	 * 
	 * @param customerId:
	 *            the customer for which the batches to be returned
	 * @param date:
	 *            a specific date. Mandatory. For view="day", the records for the
	 *            given date are returned. For view="week", the records for the week
	 *            starting for the given date are returned.
	 * @param view:
	 *            one of the two specific values "day" or "week". Optional. Default
	 *            = "week".
	 * @param orderBy:
	 *            one of the two specific values "deliveryTime" or "treatmentTime".
	 *            Optional. Default = "deliveryTime"
	 * @return list of batches
	 */
	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<ShotBatchDTO> getShotBatches(@PathVariable("customerId") String customerId,
			@RequestParam(name = "date", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
			@RequestParam(name = "view", required = false, defaultValue = "week") String view,
			@RequestParam(name = "orderBy", required = false, defaultValue = "deliveryTime") String orderBy) {

		logger.debug("Getting [{}] batches for [{}] for customer [{}] ordered by [{}].", view, date, customerId,
				orderBy);

		try {
			
			boolean orderByDeliveryTime = orderBy.equals("deliveryTime") ? true : false;
			boolean weekView = view.equals("week") ? true : false;
			List<ShotBatchDTO> shotBatches = this.shotBatchService.getShotBatches(customerId, date, weekView, orderByDeliveryTime);
	
			if(shotBatches == null || shotBatches.isEmpty())
				logger.info("No batches found for [{}] for customer [{}]", date, customerId);
			else
				logger.info("[{}] batches found for [{}] for customer [{}]", shotBatches.size(), date, customerId);
			
			return shotBatches;
			
		} catch (ShotServiceException e) {
			logger.error("Error getting batches for [{}] for customer [{}]", date, customerId, e.getMessage());
			throw new ApiException(e.getMessage());
		}
	}
	
	/**
	 * API to get details of SHOT batch
	 * <p/>
	 * URI :
	 * <code>/shot/api/customers/{customerId}/batches/{batchId}</code>
	 * <p/>
	 * METHOD : <code>HTTP GET</code>
	 * <p/>
	 * When successful it will return the ShotBatchDTO
	 * 
	 * @param customerId:
	 *            the customer for which the batch to be returned
	 * @param batchId:
	 *            
	 * 
	 * @return ShotBatchDTO
	 */
	@RequestMapping(value    = "/{batchId}", 
			        method   = RequestMethod.GET, 
			        produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<ShotBatchDTO> getShotBatch(@PathVariable("customerId") String customerId,
												                   @PathVariable("batchId") Integer batchId) {

		logger.debug("Getting batch details for customer [{}] and batchId [{}].", customerId, batchId);

		try {
			
			ShotBatchDTO shotBatch = this.shotBatchService.getShotBatch(customerId, batchId);
	
			if (shotBatch != null) {
				return new ResponseEntity<ShotBatchDTO>(shotBatch,HttpStatus.OK);
			} else {
				return new ResponseEntity<ShotBatchDTO>(HttpStatus.NOT_FOUND);
			}
			
		} catch (ShotServiceException e) {
			logger.error("Error getting batch details for customer [{}] and batchId [{}].", customerId, batchId , e.getMessage());
			throw new ApiException(e.getMessage());
		}
	}
	
	/**
	 * API to get audit history of AXIS batch
	 * <p/>
	 * URI :
	 * <code>/shot/api/customers/{customerId}/batches/{batchId}/histories/</code>
	 * <p/>
	 * METHOD : <code>HTTP GET</code>
	 * <p/>
	 * When successful it will return the AxisBatchAuditDTO
	 * 
	 * @param customerId:
	 *            the customer for which the batch to be returned
	 * @param batchId:
	 *            
	 * 
	 * @return List<AxisBatchAuditDTO>
	 */
	@RequestMapping(value    = "/{batchId}/histories", 
			        method   = RequestMethod.GET, 
			        produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<List<AxisBatchAuditDTO>> getBatchHistories(@PathVariable("customerId") String customerId,
												                                   @PathVariable("batchId") Long batchId) {

		logger.info("Getting batch histories for customer [{}] and batchId [{}].", customerId, batchId);

		try {
			
			List<AxisBatchAuditDTO> batchHistories = this.shotBatchService.getBatchHistories(batchId);
	
			if (batchHistories != null && batchHistories.size() > 0) {
				logger.info("Retrieved [{}] histories for customer [{}] and batchId [{}].",batchHistories.size(), customerId, batchId);
				return new ResponseEntity<List<AxisBatchAuditDTO>>(batchHistories,HttpStatus.OK);
			} else {
				logger.info("No histories retrieved for customer [{}] and batchId [{}].", customerId, batchId);
				return new ResponseEntity<List<AxisBatchAuditDTO>>(HttpStatus.NOT_FOUND);
			}
			
		} catch (ShotServiceException e) {
			logger.error("Error getting batch histories for customer [{}] and batchId [{}].", customerId, batchId , e.getMessage());
			throw new ApiException(e.getMessage());
		}
	}
}
