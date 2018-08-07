package com.telstra.health.shot.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.jdbc.ReturningWork;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.telstra.health.shot.api.exception.ApiException;
import com.telstra.health.shot.api.response.BatchAPIResponse;
import com.telstra.health.shot.common.enums.BatchStatus;
import com.telstra.health.shot.common.enums.ShotErrors;
import com.telstra.health.shot.dao.BatchDAO;
import com.telstra.health.shot.dao.OrderDAO;
import com.telstra.health.shot.dao.ShotBatchDAO;
import com.telstra.health.shot.dao.ShotTempBatchDAO;
import com.telstra.health.shot.dto.BatchDTO;
import com.telstra.health.shot.dto.OrderDTO;
import com.telstra.health.shot.dto.OrderHistoryDTO;
import com.telstra.health.shot.dto.ShotBatchDTO;
import com.telstra.health.shot.dto.ShotBatchPatientDTO;
import com.telstra.health.shot.dto.ShotBatchTempDTO;
import com.telstra.health.shot.entity.AxisIngredient;
import com.telstra.health.shot.entity.Batch;
import com.telstra.health.shot.entity.Order;
import com.telstra.health.shot.entity.ShotBatch;
import com.telstra.health.shot.entity.ShotTempBatch;
import com.telstra.health.shot.entity.ShotTempIngredient;
import com.telstra.health.shot.service.exception.ShotServiceException;
import com.telstra.health.shot.util.ShotConstants;

import java.text.ParseException;

@Service
public class OrderServiceImpl implements OrderService, ReturningWork<Integer> {

	private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Autowired
	OrderDAO orderDao;

	@Autowired
	ShotTempBatchDAO shotTempBatchDao;

	@Autowired
	ShotBatchDAO shotBatchDao;

	@Autowired
	BatchDAO batchDao;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	@Qualifier("logisticsValidationService")
	LogisticsValidationService logisticsValidationService;

	// This function will be called when user tries to add the batch to the order.
	// The purpose of the function is to calculate the ingredients which will be
	// used in order to create a batch.
	@Override
	@Transactional
	public boolean calculateIngredients(BatchDTO batchDto, BatchAPIResponse batchAPIResponse)
			throws ShotServiceException {
		try {
			batchDto = orderDao.calculateIngredients(batchDto);
			// Insert this orderDto object into the SHOT temp tables
			ShotTempBatch shotTempBatch = modelMapper.map(batchDto, ShotTempBatch.class);
			// Additional fields needs to be saved
			shotTempBatch.setBatdrug2Key(batchDto.getStkKey2());
			shotTempBatch.setBatDsu2key(batchDto.getBatDsukey2());
			shotTempBatch.setBatdrug3Key(batchDto.getStkKey3());
			shotTempBatch.setBatDsu3key(batchDto.getBatDsukey3());
			shotTempBatch.setBatCsoverride(batchDto.isBatClosedSystem());

			// set the current date/time for the last update
			shotTempBatch.setBatLastUpdWhen(convertToTimestamp(""));
			// set the list of ingredients for the axis batch(1:n)
			for (ShotTempIngredient shotTempIngredient : shotTempBatch.getIngredient()) {
				shotTempIngredient.setShotTempBatch((shotTempBatch));
			}

			shotTempBatch = shotTempBatchDao.save(shotTempBatch);
			// building the API response
			batchAPIResponse.setBatchId(shotTempBatch.getBatkey());
			batchAPIResponse.setShotSideMessage(batchDto.getShotSideMessage());
			batchAPIResponse.setShotCancelSave(batchDto.getShotCancelsave());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("OrderServiceImpl.calculateIngredients() : Failed to calculate the ingredients  : ",
					e.getCause());
			throw new ShotServiceException(e.getMessage());
		}
		return true;

	}

	@Override
	public Integer execute(Connection arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	// This function will be called once User clicks on the SUBMIT Order.
	// The purpose of the function is to save the order into the SHOT and Axis
	// tables.
	@Override
	@Transactional
	public boolean saveOrder(List<OrderDTO> orderDtoList) throws ShotServiceException {

		try {
			List<Order> orderList = new ArrayList<>();
			// iterate through the order collection

			for (OrderDTO orderDto : orderDtoList) {

				List<ShotBatch> shotBatchList = new ArrayList<>();
				// Generate the key for each of the order in the collection
				String orderKey = orderDao.GenerateKey("ORD", orderDto.getOrdEntity(), orderDto.getCreatedBy());
				// if there is no PO number then generate one based on YYDDMM format
				if (orderDto.getOrdNo().isEmpty()) {
					generatePONumber(orderDto);
				}
				// first try to save the data into the Shot tables then in the Axis
				ShotBatch shotBatch = modelMapper.map(orderDto, ShotBatch.class);
				// insert the data for the flat structure table which SHOT_BATCH table.
				for (Batch axisBatch : shotBatch.getBatches()) {
					ShotBatch shotBatchObject = new ShotBatch();
					String batchKey = orderDao.GenerateKey("Bat", orderDto.getOrdEntity(), orderDto.getCreatedBy());
					shotBatchObject.setOrderId(orderKey);
					shotBatchObject.setBatchId(batchKey);
					shotBatchObject.setOrdNo(orderDto.getOrdNo());
					shotBatchObject.setDose(axisBatch.getBatDose());
					shotBatchObject.setDoseUnit(axisBatch.getXxxDoseunits());
					shotBatchObject.setSpecifiedVolume(axisBatch.getBatSpecifiedVolume());
					shotBatchObject.setQuantity((int) axisBatch.getBatQuantity());
					shotBatchObject.setExact(axisBatch.getBatExact());
					shotBatchObject.setProductDescription(axisBatch.getProductDescription());
					// current date/time for the created date/time
					shotBatchObject.setCreatedDate(convertToTimestamp(""));
					// delivery date time conversion from String to Timestamp
					shotBatchObject.setOrdDeliveryDate(convertToTimestamp(orderDto.getOrdDeliveryDate()));
					shotBatchObject.setOrdCuskey(shotBatch.getOrdCuskey());
					shotBatchObject.setCreatedBy(shotBatch.getCreatedBy());
					shotBatchObject.setOrdDeliveryLocationName(shotBatch.getOrdDeliveryLocationName());
					shotBatchObject.setExact(axisBatch.getBatExact());
					shotBatchObject.setOrdBillTo(shotBatch.getOrdBillTo());
					shotBatchObject.setDeliveryMechanismDescription(axisBatch.getDeliveryMechanismDescription());
					shotBatchObject.setInfusionDuration(axisBatch.getInfusionDuration());
					shotBatchObject.setPatientFirstName(axisBatch.getBatPatFirstName());
					shotBatchObject.setPatientLastName(axisBatch.getBatPatLastName());
					shotBatchObject.setPatientUr(axisBatch.getBatPatientUrNo());
					shotBatchObject.setPatientId(axisBatch.getPatientId());
					shotBatchObject.setPatientDob(axisBatch.getPatientDob());
					shotBatchObject.setClosedSystem(axisBatch.getClosedSystem());
					shotBatchObject.setComments(axisBatch.getBatComments());
					shotBatchObject.setDeliveryMechanismKey(axisBatch.getBatDelMechKey());
					shotBatchObject.setBatAdminrouteCode(axisBatch.getBatAdminRouteCode());
					shotBatchObject.setExpiryDate(axisBatch.getBatExpiryDate());
					shotBatchObject.setRouteName(axisBatch.getRouteName());
					shotBatchObject.setBatAdminrouteCode(axisBatch.getBatAdminRouteCode());
					shotBatchObject.setDeliveryMechanismKey(axisBatch.getBatDelMechKey());
					shotBatchObject.setOrdDeliveryLocation(shotBatch.getOrdDeliveryLocation());
					shotBatchObject.setOrdDeliveryLocationName(shotBatch.getOrdDeliveryLocationName());
					shotBatchObject.setTreatmentDateTime(axisBatch.getBatTreatDate());
					shotBatchObject.setStatus(axisBatch.getStatus());
					// Multi-drug attributes
					shotBatchObject.setProductDescription2(axisBatch.getProductDescription2());
					shotBatchObject.setProductDescription3(axisBatch.getProductDescription3());
					shotBatchObject.setDose2(axisBatch.getBatDose2());
					shotBatchObject.setDose3(axisBatch.getBatDose3());
					shotBatchObject.setDoseUnit2(axisBatch.getXxxDoseunits2());
					shotBatchObject.setDoseUnit3(axisBatch.getXxxDoseunits3());
					shotBatchObject.setBatEntKey(axisBatch.getBatEntKey());
					// storing the send/date in SHOT table
					shotBatchObject.setBatDispatchDatetime(orderDto.getOrdSendDate());
					shotBatchObject.setIsDeliveryRunRestricted(axisBatch.getIsDeliveryRunRestricted());
					shotBatchObject.setHasDeliveryRunIncentive(axisBatch.getHasDeliveryRunIncentive());
					shotBatchList.add(shotBatchObject);
				}

				List<ShotBatch> shotBatches = (List<ShotBatch>) shotBatchDao.save(shotBatchList);
				if (shotBatches != null) {
					Order axisOrder = createAxisOrderandBatch(orderDto, orderKey, shotBatches, false);
					if (axisOrder != null) {
						orderList.add(axisOrder);
					}
				}
			}

			List<Order> ordersEntity = (List<Order>) orderDao.save(orderList);
			if (ordersEntity != null) {
				return true;
			}
		} catch (Exception ex) {
			logger.error("OrderServiceImpl.saveOrder() : Failed to create the orders  : ", ex.getCause());
		}
		return false;
	}

	// this function is responsible for the generating a PO number
	private void generatePONumber(OrderDTO orderDto) {

		String pattern = "YYMMdd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date = simpleDateFormat.format(new java.util.Date());
		orderDto.setOrdNo(date);
	}

	@Override
	public List<OrderHistoryDTO> getOrderHistoryList(String customerId, String patientId) {
		return this.orderDao.getOrderHistoryList(customerId, patientId);
	}

	// this function is responsible for getting the batch details
	@Override
	public BatchDTO getBatchdetails(long batchId) {
		ShotTempBatch shotTempBatch = shotTempBatchDao.findOne(batchId);
		BatchDTO batchDto = convertToDto(shotTempBatch);
		return batchDto;

	}

	// this function is responsible for the converting to DTO
	private BatchDTO convertToDto(ShotTempBatch shotTempBatch) {
		if (null == shotTempBatch)
			return null;
		else {
			BatchDTO batchDto = modelMapper.map(shotTempBatch, BatchDTO.class);
			return batchDto;
		}
	}

	// this function is responsible for converting the date into Time stamp
	private Timestamp convertToTimestamp(String stringDate) {
		try {
			String currentDateTime;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (stringDate.isEmpty()) {
				Date date = new Date();
				currentDateTime = dateFormat.format(date);
			} else {
				Date date = dateFormat.parse(stringDate);
				currentDateTime = dateFormat.format(date);
			}
			Date parsedDate = dateFormat.parse(currentDateTime);
			Timestamp timestamp = new Timestamp(parsedDate.getTime());
			return timestamp;
		} catch (ParseException e) {
			logger.error("Exception in creating date/Time in CurrentDateTime() :" + e.getMessage());
			return null;
		}
	}

	@Override
	@Transactional
	public boolean updateOrderandBatch(ShotBatchDTO shotBatchDto) throws ShotServiceException {
		try {
			// check the axis batch status and alert the user
			boolean isBatchStatusSame = checkShotBatchStatus(shotBatchDto.getShotBatchId(), shotBatchDto.getStatus());
			if (isBatchStatusSame == false) {
				throw new ShotServiceException(shotBatchDto.getBatchId(),
						ShotConstants.ErrorCodes.ORDER_MANAGEMENT_UPDATE_SHOTBATCH_STATUS);
			}
			// update the comments in the AXis batch and SHOT tables
			if (shotBatchDto.getChangeType().equalsIgnoreCase("C")) {
				// shotBatch.setUpdatedDate(convertToTimestamp(""));
				ShotBatch existingShotBatch = shotBatchDao.findOne(shotBatchDto.getShotBatchId());
				// find the shot batch if it's available then only update the batch for the SHOT
				// batchID, customer with the new comments
				if (existingShotBatch != null) {

					this.orderDao.updateBatchComments(existingShotBatch.getCustomerId(),
							existingShotBatch.getShotBatchId(), shotBatchDto.getComments(), shotBatchDto.getBatchId(),
							convertToTimestamp(""));
					// update the last updated properties in the ORDER table
					Order updatedOrderEntity = updateExistingAxisOrder(shotBatchDto.getUpdatedBy(),
							shotBatchDto.getOrderId());
					if (updatedOrderEntity != null) {
						return true;
					}
				}
			} else // go through the upsert operation depending on the Status
			{

				String[] arrOrderBatchKey = generateNewOrderandBatch(shotBatchDto);
				if (arrOrderBatchKey.length > 1) {
					boolean updatedBatchSuccessful = updateExistingAxisBatch(shotBatchDto.getBatchId(),
							arrOrderBatchKey[1], shotBatchDto.getUpdatedBy(), "", shotBatchDto.getStatus());
					// pass the new generated order and Batch Keys.
					if (updatedBatchSuccessful == true) {
						boolean updatedShotBatch = updateExistingShotBatch(shotBatchDto, arrOrderBatchKey[0],
								arrOrderBatchKey[1]);
						if (updatedShotBatch == true) {
							boolean ingredientsDeletedSuccessfully = updateBatchIngredients(shotBatchDto.getBatchId());
							return ingredientsDeletedSuccessfully;
						}
					}

				}
			}

		} catch (ShotServiceException ex) {
			logger.error(
					"OrderServiceImpl.updateOrderandBatch() : Failed to update the SHOT Batch: {} due to updated axis batch {} status, : ",
					shotBatchDto.getShotBatchId(), shotBatchDto.getBatchId(), ex.getMessage());
			throw new ShotServiceException(ex.getErrorCode(), "", ex.getMessage());
		} catch (Exception ex) {
			logger.error("OrderServiceImpl.updateOrderandBatch() : Failed to update the SHOT Batch: {}, error   : ",
					shotBatchDto.getShotBatchId(), ex.getMessage());
			throw new ShotServiceException(ex.getMessage());
		}
		return false;
	}

	private String[] generateNewOrderandBatch(ShotBatchDTO shotBatchDto) {
		try {
			List<Order> orderList = new ArrayList<>();
			List<ShotBatch> shotBatchList = new ArrayList<>();
			String orderKey = null;
			OrderDTO orderDto = new OrderDTO();
			Order axisOrder = new Order();
			BatchDTO batchDto = modelMapper.map(shotBatchDto, BatchDTO.class);
			String[] arrOrderandBatchKey = new String[2];
			boolean isBatchFieldsMappingSuccess = mappingShotToAxisBatchfields(shotBatchDto, batchDto);
			if (isBatchFieldsMappingSuccess == true) {
				BatchAPIResponse batchAPIResponse = new BatchAPIResponse();
				boolean isTempBatchCreated = calculateIngredients(batchDto, batchAPIResponse);
				if (isTempBatchCreated == true) {
					orderDto = mappingShotToAxisOrderfields(shotBatchDto, batchAPIResponse.getBatchId());
					// construct the order object before submitting the order
					if (shotBatchDto.getChangeType().equalsIgnoreCase("O")) {
						if (orderDto != null) {
							List<OrderDTO> orderDtoList = new ArrayList<>();
							orderDtoList.add(orderDto);
							// TODO :- Revisit the logistics
							// Validate Delivery Runs for the list of orders
							/*
							 * List<String> validationErrors =
							 * logisticsValidationService.validateOrderDeliveryRuns(orderDtoList); if
							 * (!validationErrors.isEmpty()) { throw new ApiException(validationErrors,
							 * ShotErrors.INVALID_DELV_DATE_TIME.getErrorCode()); }
							 */
							// update the order status if required.
							boolean updateOrderStatus = updateOrderStatus(shotBatchDto.getOrderId(),
									shotBatchDto.getUpdatedBy());
							// this is for Order level update
							orderKey = orderDao.GenerateKey("ORD", orderDto.getOrdEntity(), orderDto.getCreatedBy());
							ShotBatch shotBatch = modelMapper.map(shotBatchDto, ShotBatch.class);
							shotBatchList.add(shotBatch);
							// this will insert a record for order and batch in Axis
							axisOrder = createAxisOrderandBatch(orderDto, orderKey, shotBatchList, true);
							if (axisOrder != null) {
								orderList.add(axisOrder);
								List<Order> ordersEntity = (List<Order>) orderDao.save(orderList);
								if (ordersEntity != null) {
									arrOrderandBatchKey[0] = ordersEntity.get(0).getOrdKey();
									arrOrderandBatchKey[1] = ordersEntity.get(0).getBatches().get(0).getBatkey();
									return arrOrderandBatchKey;
								}
							}
						}
					} else if (shotBatchDto.getChangeType().equalsIgnoreCase("B")) {

						// this is only for UPSERT for Batch, ingredients but order will be an update.
						Order ordUpdtEntity = updateExistingAxisOrder(shotBatchDto.getUpdatedBy(),
								shotBatchDto.getOrderId());
						if (ordUpdtEntity != null) {
							ShotBatch shotBatch = modelMapper.map(shotBatchDto, ShotBatch.class);
							shotBatchList.add(shotBatch);
							// need to set these properties on the orderDTO as they require during the order
							// key generation
							orderDto.setCreatedBy(ordUpdtEntity.getOrdLastUpdBy());
							orderDto.setOrdEntity(ordUpdtEntity.getOrdEntKey());
							// this will insert a record for batch and ingredients in Axis
							String newBatchKey = createAxisNewBatch(ordUpdtEntity, shotBatchList, orderDto);
							if (newBatchKey != null) {
								arrOrderandBatchKey[0] = shotBatchList.get(0).getOrderId();
								arrOrderandBatchKey[1] = newBatchKey;
								return arrOrderandBatchKey;
							}

						}

					}
				}
			}
		} catch (Exception ex) {
			logger.error("OrderServiceImpl.createAxisBatch() : Failed to create/update the axis Order or batch  : ",
					ex.getCause());
			throw new ShotServiceException(ex.getMessage());
		}
		return null;

	}

	// This purpose of this function is to map the properties in accordance of the
	// BATCH table
	// so that calculate ingredients SP would be called to generate the new batch
	// information.
	private boolean mappingShotToAxisBatchfields(ShotBatchDTO shotBatchDto, BatchDTO batchDto) {
		try {
			batchDto.setBatDose(shotBatchDto.getDose().doubleValue());
			batchDto.setBatQuantity(shotBatchDto.getQuantity());
			batchDto.setBatExact(shotBatchDto.getExact());
			batchDto.setEntRiva("Y");
			batchDto.setBatAdminRouteCode(shotBatchDto.getRouteId());
			batchDto.setBatSpecifiedVolume(shotBatchDto.getSpecifiedVolume().doubleValue());
			batchDto.setBatComments(shotBatchDto.getComments());
			// set the action to "I" for the new Batch
			batchDto.setBatLastUpdAction("I");
			batchDto.setBatClosedSystem(shotBatchDto.isClosedSystem());
			if (shotBatchDto.getDeliveryDate() != null) {
				// concatenate the date and time
				String deliveryDateTime = shotBatchDto.getDeliveryDate().concat(" ")
						.concat(shotBatchDto.getDeliveryTime());
				batchDto.setBatDeliveryDatetime(deliveryDateTime);
			}
			if (shotBatchDto.getTreatmentDate() != null) {
				String treatmentDateTime = shotBatchDto.getTreatmentDate().concat(" ")
						.concat(shotBatchDto.getTreatmentTime());
				batchDto.setBatTreatDate(treatmentDateTime);
			}
			batchDto.setBatCusKey(shotBatchDto.getCustomerId());
			batchDto.setBatDelMechKey(shotBatchDto.getDeliveryMechanismKey());
			return true;

		} catch (Exception ex) {
			logger.error("OrderServiceImpl.mappingShotToAxisProperties() : Failed to mapped the batch fields  : ",
					ex.getCause());
			throw new ShotServiceException(ex.getMessage());
		}
	}

	// This purpose of this function is to map the properties in accordance of the
	// BATCH table
	// so that calculate ingredients SP would be called to generate the new batch
	// information.
	private OrderDTO mappingShotToAxisOrderfields(ShotBatchDTO shotBatchDto, Long tempBatchId) {
		try {
			List<ShotBatchTempDTO> shotBatchTempDTO = new ArrayList<>();
			List<BatchDTO> batches = new ArrayList<>();
			List<ShotBatchPatientDTO> shotBatchPatientListDTO = new ArrayList<>();
			ShotBatchPatientDTO shotBatchPatientDto = modelMapper.map(shotBatchDto, ShotBatchPatientDTO.class);
			ShotBatchTempDTO shotBatchTempDto = new ShotBatchTempDTO();
			shotBatchTempDto.setBatchId(tempBatchId);
			shotBatchTempDto.setClosedSystem(shotBatchDto.isClosedSystem());
			// few properties which needs to be mapped manually
			shotBatchTempDto.setProductDescription(shotBatchDto.getProductDescription());
			shotBatchTempDto.setStatus(shotBatchDto.getStatus());
			shotBatchTempDTO.add(shotBatchTempDto);
			shotBatchPatientDto.setBatches(shotBatchTempDTO);
			shotBatchPatientListDTO.add(shotBatchPatientDto);

			OrderDTO orderDto = modelMapper.map(shotBatchDto, OrderDTO.class);
			orderDto.setPatients(shotBatchPatientListDTO);
			// Get the Batch details and add it on the ORDER DTO object.
			BatchDTO batchDto = setBatchDtoProperities(shotBatchTempDto, shotBatchPatientDto);
			if (shotBatchDto.getTreatmentDate() != null) {
				String treatmentDateTime = shotBatchDto.getTreatmentDate().concat(" ")
						.concat(shotBatchDto.getTreatmentTime());
				batchDto.setBatTreatDate(treatmentDateTime);
			}
			batchDto.setBatComments(shotBatchDto.getComments());
			batchDto.setBatEntKey(shotBatchDto.getBatEntKey());
			batchDto.setBatDeliveryLocation(shotBatchDto.getDeliveryLocationId());

			batches.add(batchDto);
			orderDto.setBatches(batches);
			// if we are creating a new order then set the new attributes on orderDto
			// otherwise just return the Batch and ingredients list.
			if (shotBatchDto.getChangeType().equalsIgnoreCase("O")) {
				orderDto.setOrdCusKey(shotBatchDto.getCustomerId());
				orderDto.setOrdLastUpdAction("I");
				orderDto.setOrdStatus("New");
				orderDto.setOrdSendDate(shotBatchDto.getBatDispatchDatetime());
				// concatenate the strings to form the date/time before mapping to the entity
				String deliveryDateTime = shotBatchDto.getDeliveryDate().concat(" ")
						.concat(shotBatchDto.getDeliveryTime());
				orderDto.setOrdDeliveryDate(deliveryDateTime);
				orderDto.setCreatedBy(shotBatchDto.getUpdatedBy());
				orderDto.setOrdDeliveryLocation(shotBatchDto.getDeliveryLocationId());
				orderDto.setOrdEntity(shotBatchDto.getBatEntKey());
				orderDto.setOrdDeliveryLocationName(shotBatchDto.getOrdDeliverylocation());
			}

			return orderDto;

		} catch (Exception ex) {
			logger.error("OrderServiceImpl.mappingShotToAxisProperties() : Failed to mapped the batch fields  : ",
					ex.getCause());
			throw new ShotServiceException(ex.getMessage());
		}

	}

	private boolean updateExistingShotBatch(ShotBatchDTO shotBatchDto, String orderKey, String batchKey) {
		try {
			ShotBatch shotBatch = modelMapper.map(shotBatchDto, ShotBatch.class);
			// concatenate the strings to form the date/time before mapping to the entity
			if (shotBatchDto.getDeliveryDate() != null) {
				String deliveryDateTime = shotBatchDto.getDeliveryDate().concat(" ")
						.concat(shotBatchDto.getDeliveryTime());
				shotBatch.setOrdDeliveryDate(convertToTimestamp(deliveryDateTime));
			}
			if (shotBatchDto.getTreatmentDate() != null) {
				String treatmentDateTime = shotBatchDto.getTreatmentDate().concat(" ")
						.concat(shotBatchDto.getTreatmentTime());
				shotBatch.setTreatmentDateTime(convertToTimestamp(treatmentDateTime));
			}

			// last updated date/time to the current date/time
			shotBatch.setUpdatedDate(convertToTimestamp(""));
			shotBatch.setOrderId(orderKey);
			shotBatch.setBatchId(batchKey);
			ShotBatch existingShotBatch = shotBatchDao.findOne(shotBatch.getShotBatchId());
			// find the shot batch if it's available then only update it the batch
			if (existingShotBatch != null) {

				shotBatch.setCreatedDate(existingShotBatch.getCreatedDate());
				// additional properties which are not part of the model mapper
				shotBatch.setOrdDeliveryLocation(existingShotBatch.getOrdDeliveryLocation());
				shotBatch.setOrdDeliveryLocationName(existingShotBatch.getOrdDeliveryLocationName());
				if (shotBatchDto.getStatus().equalsIgnoreCase(BatchStatus.ON_HOLD.getStatus())) {
					shotBatch.setStatus(BatchStatus.ON_HOLD.getStatus());
				} else {
					shotBatch.setStatus("Submitted");
				}
				shotBatch.setUpdatedBy(shotBatchDto.getUpdatedBy());
				shotBatch.setUpdatedDate(convertToTimestamp(""));
				shotBatch.setBatchId(batchKey);
				ShotBatch updateShotBatch = shotBatchDao.save(shotBatch);
				if (updateShotBatch != null) {
					return true;
				}
			}
		} catch (Exception ex) {
			logger.error("OrderServiceImpl.updateExistingShotBatch() : Failed to update the SHOT Batch record  : ",
					ex.getCause());
			throw new ShotServiceException(ex.getMessage());
		}
		return false;
	}

	// The purpose of the function is to set the Batch DTO properties so that while
	// submitting the
	// order object it can adhere the CREATE order structure workflow..
	public BatchDTO setBatchDtoProperities(ShotBatchTempDTO shotBatchTemp, ShotBatchPatientDTO shotBatchPatients) {
		try {
			BatchDTO batchDto = getBatchdetails(shotBatchTemp.getBatchId());
			batchDto.setPatientDob(shotBatchPatients.getPatientDob());
			batchDto.setBatPatFirstName(shotBatchPatients.getPatientFirstName());
			batchDto.setBatPatLastName(shotBatchPatients.getPatientLastName());
			batchDto.setPatientId(shotBatchPatients.getPatientId());
			batchDto.setBatPatientUrNo(shotBatchPatients.getPatientUr());
			batchDto.setStatus(shotBatchTemp.getStatus());
			batchDto.setBatTreatDate(shotBatchTemp.getTreatmentDateTime());
			batchDto.setProductDescription(shotBatchTemp.getProductDescription());
			batchDto.setBatClosedSystem(shotBatchTemp.isClosedSystem());
			batchDto.setInfusionDuration(shotBatchTemp.getInfusionDuration());
			batchDto.setDeliveryMechanismDescription(shotBatchTemp.getDeliveryMechanismDescription());
			batchDto.setBatComments(shotBatchTemp.getComments());
			batchDto.setRouteName(shotBatchTemp.getRouteName());
			batchDto.setIsDeliveryRunRestricted(shotBatchTemp.getIsDeliveryRunRestricted());
			batchDto.setHasDeliveryRunIncentive(shotBatchTemp.getHasDeliveryRunIncentive());
			batchDto.setProductDescription2(shotBatchTemp.getProductDescription2());
			batchDto.setProductDescription3(shotBatchTemp.getProductDescription3());
			return batchDto;
		} catch (Exception ex) {
			logger.error("OrderServiceImpl.setBatchDtoProperities() : Failed to mapped to the BatchDTO  : ",
					ex.getCause());
			throw new ShotServiceException(ex.getMessage());
		}

	}

	// this function is responsible for creating a new ORDER, Batch and Ingredients
	// in the Axis flow.
	private Order createAxisOrderandBatch(OrderDTO orderDto, String orderKey, List<ShotBatch> shotBatches,
			boolean orderUpsertOperation) {
		try {
			// Index counter for iterating through each of the shotbatch collection
			int batchIndex = 0;
			Order axisOrder = new Order();
			axisOrder = modelMapper.map(orderDto, Order.class);
			// set the order date/time which is current date/time(order created date)
			axisOrder.setOrdDate(convertToTimestamp(""));
			axisOrder.setOrdKey(orderKey);
			axisOrder.setOrdEntKey(orderDto.getOrdEntity());
			axisOrder.setOrdDeliveryDate(convertToTimestamp(orderDto.getOrdDeliveryDate()));
			axisOrder.setOrdLastUpdBy(orderDto.getCreatedBy());

			for (Batch batch : axisOrder.getBatches()) {

				if (orderUpsertOperation == true) {
					String batchKey = orderDao.GenerateKey("Bat", orderDto.getOrdEntity(), orderDto.getCreatedBy());
					batch.setBatkey(batchKey);
				} else {
					batch.setBatkey(shotBatches.get(batchIndex).getBatchId());
				}
				// set the batch status as Order status
				batch.setBatStatus(axisOrder.getOrdStatus().toUpperCase());
				// set the entity key otherwise it will not populate in axis
				batch.setBatEntKey(axisOrder.getOrdEntKey());
				batch.setBatDeliveryLocation(axisOrder.getOrdDeliveryLocation());
				batch.setBatBillToCus(axisOrder.getOrdBillTo());
				batch.setBatEnteredBy(orderDto.getCreatedBy());
				batch.setHasDeliveryRunIncentive(orderDto.getBatches().get(batchIndex).getHasDeliveryRunIncentive());
				batch.setBatLastUpdBy(orderDto.getCreatedBy());
				batch.setBatDateEntered(convertToTimestamp(""));
				if (batch.getStatus().equalsIgnoreCase("On Hold")) {
					batch.setBatOnhold("Y");
					batch.setBatPriority("CNFRQD");
				}
				// set the list of ingredients for the axis batch(1:n)
				for (AxisIngredient ingredientlist : batch.getIngredient()) {
					// update the ingredient list by last update by
					ingredientlist.setIngLastupdby(orderDto.getCreatedBy());
					ingredientlist.setBatch(batch);
				}
				// set the shot batch for the axis
				batch.setShotBatch(shotBatches.get(batchIndex));
				batch.setOrder(axisOrder);
				batchIndex++;
			}
			// set the last updated date/time to the current date/time
			axisOrder.setOrdlastUpdWhen(convertToTimestamp(""));
			// axis status is "N" for new order
			char status = orderDto.getOrdStatus().charAt(0);
			String orderStatus = Character.toString(status);
			axisOrder.setOrdStatus(orderStatus);
			// set the send date/time which is in String to timestamp before saving
			axisOrder.setOrdSendDate(convertToTimestamp(orderDto.getOrdSendDate()));
			return axisOrder;
		} catch (Exception ex) {
			logger.error("OrderServiceImpl.createAxisOrderandBatch() : Failed to insert the axis order record  : ",
					ex.getCause());
			throw new ShotServiceException(ex.getMessage());
		}

	}

	// this function will find the list of the ingredients for the Batch in the
	// ING_ingredient
	// table and delete them so that unused stock can be released.
	private boolean updateBatchIngredients(String batchKey) {
		try {
			return orderDao.updateBatchIngredients(batchKey);
		} catch (Exception ex) {
			logger.error(
					"OrderServiceImpl.updateBatchIngredients() : Failed to delete the axis batch key ingredients record  : ",
					ex.getCause());
			throw new ShotServiceException(ex.getMessage());
		}

	}

	// this function is responsible for updating the fields for the existing
	// batch record
	private boolean updateExistingAxisBatch(String axisBatchId, String newBatchKey, int updatedBy,
			String rejectReasonCode, String shotStatus) {
		try {
			Batch batchEntity = batchDao.findOne(axisBatchId);
			if (batchEntity != null) {

				if (shotStatus.equalsIgnoreCase(BatchStatus.ON_HOLD.toString())) {
					String axisBatchStatus = batchEntity.getBatStatus();
					if (axisBatchStatus.equalsIgnoreCase(BatchStatus.COMPOUNDED.toString())
							|| axisBatchStatus.equalsIgnoreCase(BatchStatus.COMPOUNDED.toString())
							|| axisBatchStatus.equalsIgnoreCase(BatchStatus.COMPOUNDED.toString())
							|| axisBatchStatus.equalsIgnoreCase(BatchStatus.COMPOUNDED.toString())) {
						batchEntity.setStatus(BatchStatus.QUARANTINE.toString());
					}

				}
				if (shotStatus.equalsIgnoreCase("Cancelled") == false) {
					// set the replacement key only for the previous batch.
					String mappedBatchStatus = axisBatchStatusMapping(batchEntity.getBatStatus());
					batchEntity.setBatStatus(mappedBatchStatus);
				}
				batchEntity.setBatReplacementbatkey(newBatchKey);
				// batch update by and when needs to be added as well.
				batchEntity.setBatLastUpdBy(updatedBy);
				batchEntity.setBatLastUpdWhen(convertToTimestamp(""));
				batchEntity.setBatLastUpdAction("U");
				if (rejectReasonCode.isEmpty() == false) {
					// Reject reason as Customer cancelled
					batchEntity.setBatRejectReason(rejectReasonCode);
				}
				batchDao.save(batchEntity);
				if (batchEntity != null) {
					return true;
				}
			}

		} catch (Exception ex) {
			logger.error("OrderServiceImpl.updateExistingAxisBatch() : Failed to update the axis batch record  : ",
					ex.getCause());
			throw new ShotServiceException(ex.getMessage());
		}
		return false;
	}

	// this function is responsible for the updating the existing Axis order during
	// UPSERT operation
	// parameter: send the updatedBy id and orderKey
	private Order updateExistingAxisOrder(int ordLastUpdatedBy, String orderKey) {
		try {
			Order orderEntity = orderDao.findOne(orderKey);
			if (orderEntity != null) {
				orderEntity.setOrdLastUpdAction("U");
				orderEntity.setOrdLastUpdBy(ordLastUpdatedBy);
				orderEntity.setOrdlastUpdWhen(convertToTimestamp(""));
				Order orderUpdtEntity = orderDao.save(orderEntity);
				if (orderUpdtEntity != null) {
					return orderUpdtEntity;
				}
			}

		} catch (Exception ex) {
			logger.error("OrderServiceImpl.updateExistingOrder() : Failed to update the axis order record  : ",
					ex.getCause());
			throw new ShotServiceException(ex.getMessage());
		}
		return null;

	}

	// this function is responsible for creating a new Batch and ingredients only in
	// Axis.
	// this will be used for the update workflow where UPSERT operation for Batch
	private String createAxisNewBatch(Order axisOrder, List<ShotBatch> shotBatches, OrderDTO orderDto) {

		try {
			Batch newBatchEntity = null;
			// Index counter for iterating through each of the shotbatch collection
			int batchIndex = 0;
			for (BatchDTO batchDto : orderDto.getBatches()) {
				newBatchEntity = modelMapper.map(batchDto, Batch.class);
				String batchKey = orderDao.GenerateKey("Bat", orderDto.getOrdEntity(), orderDto.getCreatedBy());
				newBatchEntity.setBatkey(batchKey);
				for (AxisIngredient ingredientlist : newBatchEntity.getIngredient()) {
					// update the ingredient list by last update by
					ingredientlist.setIngLastupdby(orderDto.getCreatedBy());
					// set the ingredients to the batch
					ingredientlist.setBatch(newBatchEntity);
				}
				newBatchEntity.setBatDateEntered(convertToTimestamp(""));
				newBatchEntity.setBatEnteredBy(orderDto.getCreatedBy());
				newBatchEntity.setBatStatus("NEW");
				newBatchEntity.setBatBillToCus(shotBatches.get(batchIndex).getCustomerId());
				// if the shot status in "On hold" then set the flag in the new batch as well.
				if (shotBatches.get(batchIndex).getStatus().equalsIgnoreCase("On Hold")) {
					newBatchEntity.setBatOnhold("Y");
					newBatchEntity.setBatPriority("CNFRQD");
				}
				// set the shot batch for the axis
				newBatchEntity.setShotBatch(shotBatches.get(batchIndex));
				newBatchEntity.setOrder(axisOrder);
				batchIndex++;
			}
			Batch batchEntity = batchDao.save(newBatchEntity);
			if (batchEntity != null) {
				return batchEntity.getBatkey();
			}
		} catch (Exception ex) {
			logger.error("OrderServiceImpl.createAxisBatch() : Failed to create axis Batch record  : ", ex.getCause());
			throw new ShotServiceException(ex.getMessage());
		}
		return null;

	}

	// the purpose of the function is to check the shot batch status. If the status
	// send from the UI is not same
	// as the batch status then display an alert to the user.
	private boolean checkShotBatchStatus(Long currentShotBatchKey, String currentShotBatchStatus) {
		try {
			ShotBatch shotBatch = shotBatchDao.findOne(currentShotBatchKey);
			if (shotBatch != null) {
				if (shotBatch.getStatus().equalsIgnoreCase(currentShotBatchStatus)) {
					return true;
				}
			}
		} catch (Exception ex) {
			logger.error("OrderServiceImpl.checkShotBatchStatus() : Failed to retreive the SHOT Batch :{} status  : ",
					currentShotBatchKey, ex.getCause());

		}
		return false;

	}

	@Transactional
	public boolean cancelBatch(String customerId, Long shotBatchId, String batchId, String orderId, String ordNo,
			int updatedBy) {
		try {
			// set the reason as "Customer cancelled" for a given Batch
			boolean batchUpdateSuccessful = updateExistingAxisBatch(batchId, "", updatedBy, "RR15", "Cancelled");
			if (batchUpdateSuccessful == true) {
				boolean updateSuccessful = updateExisitingShotBatchProperties(shotBatchId, updatedBy, "Cancelled");
				if (updateSuccessful == true) {
					// we just need to ensure if we need to update the Order status
					updateOrderStatus(orderId, updatedBy);
				}
				return updateSuccessful;
			}
			return batchUpdateSuccessful;

		} catch (Exception ex) {
			logger.error("OrderServiceImpl.cancelBatch() : Failed to cancel the SHOT batch  : ", ex.getCause());
			throw new ShotServiceException(ex.getMessage());
		}
	}

	// this function is responsible for the mapping the axis batch status with the
	// corresponding XStatus
	private String axisBatchStatusMapping(String currentStatus) {
		if (currentStatus.equalsIgnoreCase(BatchStatus.NEW.toString())) {
			return BatchStatus.NEW.getStatus();
		} else if (currentStatus.equalsIgnoreCase(BatchStatus.ENTRYCONF.toString())) {
			return BatchStatus.ENTRYCONF.getStatus();
		} else if (currentStatus.equalsIgnoreCase(BatchStatus.PICKING.toString())) {
			return BatchStatus.PICKING.getStatus();
		} else if (currentStatus.equalsIgnoreCase(BatchStatus.PICKED.toString())) {
			return BatchStatus.PICKED.getStatus();
		}
		return currentStatus;
	}

	// this function is responsible for set the fields for updating On hold batch
	// status
	@Override
	@Transactional
	public boolean updateOnHoldBatch(ShotBatchDTO shotBatchDto) throws ShotServiceException {
		try {
			if (shotBatchDto.getStatus().equalsIgnoreCase(BatchStatus.ON_HOLD.getStatus())) {
				Batch batchExisitingDao = batchDao.findOne(shotBatchDto.getBatchId());
				if (batchExisitingDao != null) {
					batchExisitingDao.setBatOnhold(null);
					batchExisitingDao.setBatLastUpdAction("U");
					batchExisitingDao.setBatLastUpdBy(shotBatchDto.getUpdatedBy());
					batchExisitingDao.setBatPriority(null);
					batchExisitingDao.setBatLastUpdWhen(convertToTimestamp(""));
					boolean updatedExisitingBatch = updateExisitingShotBatchProperties(shotBatchDto.getShotBatchId(),
							shotBatchDto.getUpdatedBy(), "Submitted");
					return updatedExisitingBatch;
				}
			}

		} catch (Exception ex) {
			logger.error("OrderServiceImpl.updateOnHoldBatch() : Failed to set the fields for axis batch  : ",
					ex.getCause());
			throw new ShotServiceException(ex.getMessage());
		}
		return false;

	}

	private boolean updateExisitingShotBatchProperties(Long existingShotBatchId, int userId, String newStatus)
			throws ShotServiceException {
		try {
			ShotBatch existingShotBatch = shotBatchDao.findOne(existingShotBatchId);
			if (existingShotBatch != null) {
				// set the status accordingly in the SHOT batch
				existingShotBatch.setStatus(newStatus);
				existingShotBatch.setUpdatedBy(userId);
				existingShotBatch.setUpdatedDate(convertToTimestamp(""));
				ShotBatch updateShotBatch = shotBatchDao.save(existingShotBatch);
				if (updateShotBatch != null) {
					return true;
				}
			}
		} catch (Exception ex) {
			logger.error(
					"OrderServiceImpl.updateExisitingShotBatchProperties() : Failed to update the shot batch properties  : ",
					ex.getCause());
			throw new ShotServiceException(ex.getMessage());
		}
		return false;
	}

	// this function is responsible for updating the Order status to Closed only if
	// the corresponding batches are in either quarantine or invoiced status
	// OR if the order doesn't have any batches.
	private boolean updateOrderStatus(String orderKey, int lastUpdtBy) throws ShotServiceException {
		try {
			boolean updateOrderStatus = orderDao.updateOrderStatus(orderKey);
			if (updateOrderStatus == true) {
				Order orderExistingEntity = orderDao.findOne(orderKey);
				if (orderExistingEntity != null) {
					// D signifies order to be closed.
					orderExistingEntity.setOrdStatus("D");
					orderExistingEntity.setOrdLastUpdAction("U");
					orderExistingEntity.setOrdLastUpdBy(lastUpdtBy);
					orderExistingEntity.setOrdlastUpdWhen(convertToTimestamp(""));
					Order orderEntity = orderDao.save(orderExistingEntity);
					if (orderEntity != null) {
						return true;
					}
				}
			}
		} catch (Exception ex) {
			logger.error("OrderServiceImpl.updateOrderStatus() : Failed to update the Order status  : ", ex.getCause());
			throw new ShotServiceException(ex.getMessage());
		}
		return false;

	}

}
