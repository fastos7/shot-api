package com.telstra.health.shot.service.impl;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.telstra.health.shot.common.enums.BatchStatus;
import com.telstra.health.shot.common.enums.OrderType;
import com.telstra.health.shot.common.enums.ShotErrors;
import com.telstra.health.shot.dao.FacilityDAO;
import com.telstra.health.shot.dao.LogisticsDAO;
import com.telstra.health.shot.dto.DeliveryRunRequestDTO;
import com.telstra.health.shot.dto.OrderDTO;
import com.telstra.health.shot.dto.ShotBatchPatientDTO;
import com.telstra.health.shot.dto.ShotBatchTempDTO;
import com.telstra.health.shot.entity.Logistic;
import com.telstra.health.shot.entity.reference.Facility;
import com.telstra.health.shot.service.LogisticsValidationService;
import com.telstra.health.shot.service.exception.ShotServiceException;
import com.telstra.health.shot.util.ShotUtils;

@Service("logisticsValidationService")
@Transactional
public class LogisticsValidationServiceImpl extends LogisticsServiceImpl implements LogisticsValidationService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private LogisticsDAO logisticsDAO;

	@Autowired
	private FacilityDAO facilityDAO;

	@Autowired
	private MessageSource messageSource;

	private DateTimeFormatter delvDateFormatter = DateTimeFormatter.ofPattern("EE, dd MMM yyyy HH:mm");

	/**
	 * Validates the delivery runs of a list of Orders being submitted. 
	 * This validation should be used when creating an Order.
	 * 
	 * @param orderDtoList
	 *            List of Orders being submitted.
	 * @return List of validation errors, if any
	 */
	public List<String> validateOrderDeliveryRuns(List<OrderDTO> orderDTOList) {
		List<String> errors = new ArrayList<>();

		// Extract DeliveryRunQtyMap
		Map<String, Map<String, Integer>> entityDelvRunQtyMap = this.extractDeliveryRunQtyMap(orderDTOList);

		// Sort Orders list
		orderDTOList.sort((order1, order2) -> {
			String ord1Str = new StringBuilder(order1.getOrdEntity()).append("_").append(order1.getOrdDeliveryDate())
					.append("_").append(order1.getOrdDeliveryLocation()).toString();
			String ord2Str = new StringBuilder(order2.getOrdEntity()).append("_").append(order2.getOrdDeliveryDate())
					.append("_").append(order2.getOrdDeliveryLocation()).toString();
			return ord1Str.compareTo(ord2Str);
		});

		// Iterate over each Order and validate its batches
		for (OrderDTO orderDTO : orderDTOList) {
			errors.addAll(this.validateOrderDeliveryRun(orderDTO, entityDelvRunQtyMap));
		}
		return errors;
	}

	/**
	 * Validates the delivery run of a Batch in Edit or Off Hold scenarios. 
	 * @param shotBatchDTO
	 * @param patientDTO
	 * @param orderDTO
	 * @return List of validation errors, if any
	 */
	public List<String> validateEditBatchDeliveryRun(ShotBatchTempDTO shotBatchDTO, ShotBatchPatientDTO patientDTO, OrderDTO orderDTO) {
		List<String> errors = new ArrayList<>();
		Map<String, Map<String, Integer>> entityDelvRunQtyMap = new HashMap<>();

		DeliveryRunRequestDTO requestDTO = new DeliveryRunRequestDTO();
		requestDTO.setCustomerKey(orderDTO.getOrdCusKey());
		requestDTO.setEntityKey(orderDTO.getOrdEntity());
		requestDTO.setDeliveryRunQtyMap(null);
		requestDTO.setQuantity(shotBatchDTO.getQuantity());
		requestDTO.setOrderType(OrderType.getByProductType(shotBatchDTO.getProductType()));
		
		try {
			this.validateBatchDeliveryRun(shotBatchDTO, patientDTO, orderDTO, requestDTO, entityDelvRunQtyMap);
			logger.info(
					"Delivery Run: {} for Batch with Product: {}, Quantity: {}, to be Delivered to: {} validated Successfully.",
					orderDTO.getOrdDeliveryDate(), shotBatchDTO.getProductDescription(),
					shotBatchDTO.getQuantity(), orderDTO.getOrdDeliveryLocationName());
		} catch (ShotServiceException ex) {
			logger.error(ex.getMessage());
			errors.add(ex.getMessage());
		}
		return errors;
	}

	/**
	 * Validates the delivery runs of an Order being submitted.
	 * 
	 * @param orderDTO
	 *            An Order being submitted.
	 * @return errors if any Delivery Run is invalid for all batches in this Order.
	 */
	public List<String> validateOrderDeliveryRun(OrderDTO orderDTO,
			Map<String, Map<String, Integer>> entityDelvRunQtyMap) {
		List<String> errors = new ArrayList<>();
		
		if (!ShotUtils.isEmpty(orderDTO.getOrdDeliveryDate())) {
			String entity = orderDTO.getOrdEntity();
			Map<String, Integer> delvRunQtyMap = entityDelvRunQtyMap.get(entity);

			DeliveryRunRequestDTO requestDTO = new DeliveryRunRequestDTO();
			requestDTO.setCustomerKey(orderDTO.getOrdCusKey());
			requestDTO.setEntityKey(entity);
			requestDTO.setDeliveryRunQtyMap(delvRunQtyMap);

			// Validate each Patient/Non Patient Batch
			for (ShotBatchPatientDTO shotBatchPatient : orderDTO.getPatients()) {
				for (ShotBatchTempDTO shotBatchDTO : shotBatchPatient.getBatches()) {

					// Validate only Submitted Batches
					if (BatchStatus.SUBMITTED.getStatus().equalsIgnoreCase(shotBatchDTO.getStatus())) {
						requestDTO.setQuantity(shotBatchDTO.getQuantity());
						requestDTO.setOrderType(OrderType.getByProductType(shotBatchDTO.getProductType()));

						try {
							this.validateBatchDeliveryRun(shotBatchDTO, shotBatchPatient, orderDTO, requestDTO, entityDelvRunQtyMap);
							logger.info(
									"Delivery Run: {} for Batch with Product: {}, Quantity: {}, to be Delivered to: {} validated Successfully.",
									orderDTO.getOrdDeliveryDate(), shotBatchDTO.getProductDescription(),
									shotBatchDTO.getQuantity(), orderDTO.getOrdDeliveryLocationName());
						} catch (ShotServiceException ex) {
							logger.error(ex.getMessage());
							errors.add(ex.getMessage());
						}
					}
				}
			}
		}
		return errors;
	}

	/**
	 * Validate the Delivery Run of the give Shot Batch.
	 * @param shotBatchDTO
	 * @param orderDTO
	 * @param requestDTO
	 * @param entityDelvRunQtyMap
	 */
	public void validateBatchDeliveryRun(ShotBatchTempDTO shotBatchDTO, ShotBatchPatientDTO patientDTO, OrderDTO orderDTO,
			DeliveryRunRequestDTO requestDTO, Map<String, Map<String, Integer>> entityDelvRunQtyMap) {

		// Get Manu. Facility instance
		Facility facility = facilityDAO.getOne(requestDTO.getEntityKey());

		// Get Facility local date and time
		ZonedDateTime orderDateTime = facility.getCurrentLocalFacilityTime();
		OrderDate originalOrderDate = new OrderDate(orderDateTime);

		// Get Order Logistics reference data
		List<Logistic> logistics = logisticsDAO.findByCustomerAndOrderTypeAndFacility(requestDTO.getCustomerKey(),
				OrderType.valueOf(requestDTO.getOrderType()), facility);

		// Get batch dispatch Date
		ZonedDateTime dispatchDateTime = ShotUtils.parseJSDate(orderDTO.getOrdSendDate(), orderDateTime.getZone());

		// If current order date time has not exceeded the Dispatch Date Time
		if (!orderDateTime.isAfter(dispatchDateTime)) {
			if (logistics != null && !logistics.isEmpty()) {
				for (Logistic logistic : logistics) {

					// Validate batch's delivery run against the Logistics configuration
					if (logistic.isDispatchDay(dispatchDateTime)
							&& dispatchDateTime.toLocalTime().getHour() == logistic.getDispatchTime()) {
						originalOrderDate.addIncentiveDay(logistic.getIncentiveDayOffset());

						if (!this.isWithinCutoff1Time(orderDateTime, dispatchDateTime, logistic)) {
							if (this.isWithinCutoff2Time(orderDateTime, dispatchDateTime, logistic, requestDTO)) {
								if (this.isWithinCutoff2Qty(orderDateTime, dispatchDateTime, logistic, requestDTO)) {

									// If Batch was not originally Restricted then mark it as Restricted and update
									// Delv Run Quantity Map
									if (!Boolean.TRUE.equals(shotBatchDTO.getIsDeliveryRunRestricted())) {
										shotBatchDTO.setIsDeliveryRunRestricted(true);

										Map<String, Integer> delvRunQtyMap = entityDelvRunQtyMap
												.get(requestDTO.getEntityKey());
										if (delvRunQtyMap == null) {
											delvRunQtyMap = new HashMap<>();
											entityDelvRunQtyMap.put(requestDTO.getEntityKey(), delvRunQtyMap);
										}
										Integer qty = delvRunQtyMap.get(orderDTO.getOrdSendDate());
										qty = qty != null ? qty : 0;
										qty += shotBatchDTO.getQuantity();
										delvRunQtyMap.put(orderDTO.getOrdSendDate(), qty);
									}
								} else {
									// ERROR: Batch Quantity has exceeded Qty Threshold. Batch cannot be included.
									logger.error(
											"Batch Quantity has exceeded the Cutoff2 Quantity Threshold for Delivery Run: {} for Batch with Product: {}, Quantity: {}, to be Delivered to: {}",
											orderDTO.getOrdDeliveryDate(), shotBatchDTO.getProductDescription(), shotBatchDTO.getQuantity(),
											orderDTO.getOrdDeliveryLocationName());
									throw new ShotServiceException(this.getErrorMessage(patientDTO, orderDTO.getOrdDeliveryDate(),
											shotBatchDTO.getProductDescription(), shotBatchDTO.getQuantity(), orderDTO.getOrdDeliveryLocationName()));
								}
							} else {
								// ERROR: Order Time is now past Cutoff2 Time. Batch cannot be included.
								logger.error(
										"Order Date Time is past the Cutoff2 Date Time for Delivery Run: {} for Batch with Product: {}, Quantity: {}, to be Delivered to: {}",
										orderDTO.getOrdDeliveryDate(), shotBatchDTO.getProductDescription(), shotBatchDTO.getQuantity(),
										orderDTO.getOrdDeliveryLocationName());
								throw new ShotServiceException(this.getErrorMessage(patientDTO, orderDTO.getOrdDeliveryDate(),
										shotBatchDTO.getProductDescription(), shotBatchDTO.getQuantity(), orderDTO.getOrdDeliveryLocationName()));
							}
						} else {
							shotBatchDTO.setIsDeliveryRunRestricted(false);
						}
						if (isWithinIncentiveCutoffTime(originalOrderDate, dispatchDateTime,
								logistic.getIncentiveDayOffset(), logistic.getIncentiveTimeOffset(), null)) {
							shotBatchDTO.setHasDeliveryRunIncentive(true);
						} else {
							shotBatchDTO.setHasDeliveryRunIncentive(false);
						}
						break;
					}
				}
			}
		} else {
			// ERROR: Order Date is now past the Dispatch Date. Batch cannot be included.
			logger.error(
					"Order Date Time is past the Dispatch Date for Delivery Run: {} for Batch with Product: {}, Quantity: {}, to be Delivered to: {}",
					orderDTO.getOrdDeliveryDate(), shotBatchDTO.getProductDescription(), shotBatchDTO.getQuantity(),
					orderDTO.getOrdDeliveryLocationName());
			throw new ShotServiceException(this.getErrorMessage(patientDTO, orderDTO.getOrdDeliveryDate(),
					shotBatchDTO.getProductDescription(), shotBatchDTO.getQuantity(), orderDTO.getOrdDeliveryLocationName()));
		}
	}

	/**
	 * Creates a Map of Compounding Facilities' total Delivery Run Quantities.
	 * 
	 * @param orderDTOList
	 * @return
	 */
	protected Map<String, Map<String, Integer>> extractDeliveryRunQtyMap(List<OrderDTO> orderDTOList) {
		Map<String, Map<String, Integer>> entityDelvRunQtyMap = new HashMap<>();

		for (OrderDTO orderDTO : orderDTOList) {

			String entity = orderDTO.getOrdEntity();
			Map<String, Integer> delvRunQtyMap = entityDelvRunQtyMap.get(entity);

			// If no Delv Run Qty mapping exists for the Entity then create one
			if (delvRunQtyMap == null) {
				delvRunQtyMap = new HashMap<>();
				entityDelvRunQtyMap.put(entity, delvRunQtyMap);
			}
			String dispatchDate = orderDTO.getOrdSendDate();
			Integer qty = delvRunQtyMap.get(dispatchDate);
			qty = qty != null ? qty : 0;
			for (ShotBatchPatientDTO shotBatchPatients : orderDTO.getPatients()) {
				for (ShotBatchTempDTO shotBatch : shotBatchPatients.getBatches()) {

					// If Batch is Submitted and not On-Hold
					if (BatchStatus.SUBMITTED.getStatus().equalsIgnoreCase(shotBatch.getStatus())) {
						// Add Quantity in case the batch is Restricted
						if (Boolean.TRUE.equals(shotBatch.getIsDeliveryRunRestricted())) {
							qty += shotBatch.getQuantity();
						} else {
							// Double check in case the batch has now gotten restricted 
							// based on the current time.
							if (this.hasBatchGottenRestricted(shotBatch, orderDTO)) {
								shotBatch.setIsDeliveryRunRestricted(true);
								qty += shotBatch.getQuantity();
							}
						}
					}
				}
			}
			delvRunQtyMap.put(dispatchDate, qty);
		}

		return entityDelvRunQtyMap;
	}

	/**
	 * Checks if a previously un-restricted batch
	 * @param shotBatchDTO
	 * @param orderDTO
	 * @return
	 */
	private boolean hasBatchGottenRestricted(ShotBatchTempDTO shotBatchDTO, OrderDTO orderDTO) {
		// Get the Facility instance and Order Date time wrt to Facility
		Facility facility = facilityDAO.getOne(orderDTO.getOrdEntity());
		ZonedDateTime orderDateTime = facility.getCurrentLocalFacilityTime();
		
		// Get batch dispatch Date
		ZonedDateTime dispatchDateTime = ShotUtils.parseJSDate(orderDTO.getOrdSendDate(), orderDateTime.getZone());
				
		// Get Order Logistics reference data
		List<Logistic> logistics = logisticsDAO.findByCustomerAndOrderTypeAndFacility(orderDTO.getOrdCusKey(),
				OrderType.valueOf(OrderType.getByProductType(shotBatchDTO.getProductType())), facility);

		for (Logistic logistic : logistics) {

			// Validate batch's delivery run against the Logistics configuration
			if (logistic.isDispatchDay(dispatchDateTime)
					&& dispatchDateTime.toLocalTime().getHour() == logistic.getDispatchTime()) {
				return this.isWithinCutoff2Time(orderDateTime, dispatchDateTime, logistic, null);
			}
		}
		return false;
	}

	/**
	 * Formulate delivery run error message to be returned for the given Batch details.
	 * @param patientDTO
	 * @param deliveryDateTime
	 * @param productDesc
	 * @param quantity
	 * @param deliveryLocation
	 * @return Batch Delivery Run error message.
	 */
	private String getErrorMessage(ShotBatchPatientDTO patientDTO, String deliveryDateTime, String productDesc,
			Integer quantity, String deliveryLocation) {
		LocalDateTime delvDateTime = ShotUtils.parseJSDate(deliveryDateTime);
		String delvDateTimeStr = this.delvDateFormatter.format(delvDateTime);
		String patientInfoStr = "No Patient";
		if (!ShotUtils.isEmpty(patientDTO.getPatientFirstName())) {
			patientInfoStr = new StringBuilder(patientDTO.getPatientLastName()).append(", ")
					.append(patientDTO.getPatientFirstName()).toString();
		}
		return this.messageSource.getMessage(ShotErrors.INVALID_DELV_DATE_TIME.getMsgKey(),
				new Object[] { patientInfoStr, productDesc, deliveryLocation, delvDateTimeStr }, null);
	}
}
