package com.telstra.health.shot.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.jdbc.ReturningWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.telstra.health.shot.common.enums.BatchStatus;
import com.telstra.health.shot.dao.exception.DataAccessException;
import com.telstra.health.shot.dto.BatchDTO;
import com.telstra.health.shot.dto.ExpiryDTO;
import com.telstra.health.shot.dto.IngredientsDTO;
import com.telstra.health.shot.dto.OrderDTO;
import com.telstra.health.shot.dto.OrderHistoryDTO;
import com.telstra.health.shot.dto.ShotBatchDTO;
import com.telstra.health.shot.entity.AxisIngredient;
import com.telstra.health.shot.entity.Batch;
import com.telstra.health.shot.service.exception.ShotServiceException;
import com.telstra.health.shot.util.ShotUtils;

@Repository
public class OrderDAOImpl implements OrderDAOCustom {

	private static final Logger logger = LoggerFactory.getLogger(OrderDAOImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Value("${orders.batches.history.topN}")
	private Integer lastNHistoryBatches;

	@SuppressWarnings("unchecked")
	// @Override
	public BatchDTO calculateIngredients(BatchDTO batchDto) throws DataAccessException {

		Session session = null;
		try {

			session = (Session) entityManager.getDelegate();
			// fetches the data from the DB and return it
			ReturningWork<BatchDTO> work = new ReturningWork<BatchDTO>() {

				// to call the stored procedure
				@SuppressWarnings("rawtypes")
				@Override
				public BatchDTO execute(Connection con) throws SQLException {

					BatchDTO batchDTO = new BatchDTO();
					CallableStatement call = null;
					// we need to set the complete string otherwise finalvolume wouldn't be
					// calculated appropriately.
					String batExact = batchDto.getBatExact().equals("t") ? "true" : "false";

					if (batchDto.getBatMsoIngStkKey() == null) {

						logger.info("Calling SP Calc Ingredients with the params {}", batchDto.toString());
						call = con.prepareCall(
								"{?= call SP_CalcIngredNew(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

						call.registerOutParameter(1, Types.INTEGER);

						call.setString(2, batchDto.getStkKey());
						call.setString(3, batchDto.getBatDelMechKey());
						call.setDouble(4, batchDto.getBatDose());
						call.setInt(5, batchDto.getBatQuantity());
						if (batchDto.getBatSpecifiedVolume() == null) {
							call.setNull(6, java.sql.Types.NUMERIC);
						} else
							call.setDouble(6, batchDto.getBatSpecifiedVolume());

						call.setString(7, batchDto.getBatAdminRouteCode());
						call.setString(8, "");
						call.setInt(9, 1);
						call.setString(10, batchDto.getBatDsukey());
						call.setString(11, batExact);
						call.setString(12, "");
						call.setString(13, "");
						call.setString(14, batchDto.getEntRiva());

						call.setString(15, batchDto.getStkKey2());
						call.setString(16, batchDto.getBatDsukey2());
						if (batchDto.getBatDose2() == null) {
							call.setDouble(17, java.sql.Types.DOUBLE);
						} else {
							call.setDouble(17, batchDto.getBatDose2());
						}

						call.setString(18, batchDto.getStkKey3());
						call.setString(19, batchDto.getBatDsukey3());
						if (batchDto.getBatDose3() == null) {
							call.setDouble(20, java.sql.Types.DOUBLE);
						} else {
							call.setDouble(20, batchDto.getBatDose3());
						}
						call.setBoolean(21, batchDto.isBatClosedSystem());
						call.setString(22, null);
						call.setString(23, null);
						call.setString(24, null);
						call.setString(25, batchDto.getBatTreatDate());
						call.setString(26, batchDto.getBatCusKey());
						call.setString(27, batchDto.getBatDeliveryLocation());
						call.setString(28, "");
						call.setString(29, batchDto.getBatDispatchDatetime());
						// arrival date/time
						call.setString(30, batchDto.getBatDeliveryDatetime());
						call.setString(31, batchDto.getBatEntKey());
						call.setDouble(32, batchDto.getInfusionDuration());

					} else {

						logger.info("Calling SP Calc Manual Batch with the params {}", batchDto.toString());

						// build XML using the stock Key, quantity and type.
						StringBuffer sbIngredientXML = new StringBuffer("<lev1recsetprop><row><xxx_type>");
						sbIngredientXML.append(batchDto.getBatProcessType());
						sbIngredientXML.append("</xxx_type><ing_stkkey>");
						sbIngredientXML.append(batchDto.getBatMsoIngStkKey());
						sbIngredientXML.append("</ing_stkkey><ing_qtyordered>");
						sbIngredientXML.append(batchDto.getBatQuantity());
						sbIngredientXML.append("</ing_qtyordered></row></lev1recsetprop>");

						String ingredientsXML = sbIngredientXML.toString();

						call = con
								.prepareCall("{?= call SP_ManualBatch_new(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
						call.registerOutParameter(1, Types.INTEGER);
						call.setString(2, "");
						call.setString(3, null);
						call.setString(4, null);
						call.setString(5, "Y");
						call.setString(6, ingredientsXML);
						call.setString(7, null);
						call.setString(8, null);
						call.setString(9, null);
						call.setString(10, null);
						call.setString(11, null);
						call.setString(12, null); // preparation date/time
						call.setString(13, null);
						call.setString(14, null);
						call.setString(15, null);// treatment date/time
						call.setString(16, batchDto.getBatCusKey()); // customer key
						call.setString(17, batchDto.getBatDeliveryLocation());
						call.setString(18, "");// bill to cus
						call.setString(19, batchDto.getBatDispatchDatetime()); // sent date/time in axis
						call.setString(20, batchDto.getBatDeliveryDatetime()); // arrival date/time
						call.setString(21, batchDto.getBatEntKey()); // entity key
						call.setString(22, null);

					}

					// we need to evaluate if the execute returns true or false.
					// If it's false then read the row count and then try to retrieve the result
					// set.
					boolean hasResults = call.execute();
					int rowsAffected = call.getUpdateCount();
					while (hasResults || rowsAffected != -1) {
						if (hasResults) {
							break;
						} else {
							rowsAffected = call.getUpdateCount();
						}
						hasResults = call.getMoreResults();
					}

					int count = 0;
					List<IngredientsDTO> ingredientList = new ArrayList<>();
					while (hasResults) {

						ResultSet rs = call.getResultSet();

						if (count == 0) {
							while (rs.next()) {

								batchDto.setShotSideMessage((rs.getString("SHOTSideMessage")));

								if (batchDto.getBatMsoIngStkKey() != null) {
									// In case of the manual stock, we get the single result set which holds the
									// batch
									// and shot message. Therefore, we need to set the ingredients list explicitly.
									RowMapper<BatchDTO> BatchDtoExtractor = new BeanPropertyRowMapper(BatchDTO.class);
									batchDTO = BatchDtoExtractor.mapRow(rs, rs.getRow());
									batchDto.setShotCancelsave((rs.getString("SHOTCancelSave")));
									batchDTO.setBatProductid(rs.getString("ProductID"));
									batchDTO.setBatFinalvolume(rs.getDouble("FinalVolume"));
									batchDTO.setBatExpiryperiod(rs.getString("ExpiryPeriod"));
									batchDTO.setBatPrepdate(rs.getTimestamp("PrepDate"));
									batchDTO.setBatPriority(rs.getString("batpriority"));
									batchDTO.setBatLastUpdAction(batchDto.getBatLastUpdAction());
									batchDTO.setBatQuantity(batchDto.getBatQuantity());
									batchDTO.setBatDescription(rs.getString("FinalDescription"));
									batchDTO.setBatAssemblyinstructions(rs.getString("OperatorInstructions"));
									batchDTO.setBatStockOnly("Y");
									IngredientsDTO ingredientsDTO = new IngredientsDTO();
									ingredientsDTO.setIngStkkey(batchDto.getBatMsoIngStkKey());
									ingredientsDTO.setIngQtyordered(batchDto.getBatQuantity().doubleValue());
									ingredientsDTO.setXxxType(batchDto.getBatProcessType());
									ingredientList.add(ingredientsDTO);

								}
							}
						}
						if (count == 1) {
							while (rs.next()) {
								RowMapper<IngredientsDTO> extractor = new BeanPropertyRowMapper(IngredientsDTO.class);
								ingredientList.add(extractor.mapRow(rs, rs.getRow()));
							}
						}

						if (count == 2) {
							while (rs.next()) {
								RowMapper<BatchDTO> BatchDtoExtractor = new BeanPropertyRowMapper(BatchDTO.class);
								batchDTO = BatchDtoExtractor.mapRow(rs, rs.getRow());

								batchDTO.setBatLastUpdAction(batchDto.getBatLastUpdAction());
								batchDTO.setBatAdminRouteCode(batchDto.getBatAdminRouteCode());
								batchDTO.setBatDelMechKey(batchDto.getBatDelMechKey());
								batchDTO.setBatDrugKey(batchDto.getStkKey());
								batchDTO.setBatSpecifiedVolume(batchDto.getBatSpecifiedVolume());
								batchDTO.setBatDsukey(batchDto.getBatDsukey());
								batchDTO.setBatDose(batchDto.getBatDose());
								batchDTO.setStkKey2(batchDto.getStkKey2());
								batchDTO.setBatDsukey2(batchDto.getBatDsukey2());
								batchDTO.setBatDose2(batchDto.getBatDose2());
								batchDTO.setStkKey3(batchDto.getStkKey3());
								batchDTO.setBatDsukey3(batchDto.getBatDsukey3());
								batchDTO.setBatDose3(batchDto.getBatDose3());
								batchDTO.setBatClosedSystem(batchDto.isBatClosedSystem());
								String batExactValue = batchDto.getBatExact().equals("t") ? "Y" : "N";
								batchDTO.setBatExact(batExactValue);
								batchDTO.setBatEntKey(batchDto.getBatEntKey());

							}
						}
						count++;
						hasResults = call.getMoreResults();
					}

					// Iterate through the batch and add the corresponding ingredients list
					batchDTO.setIngredient(ingredientList);
					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					Date date = new Date();
					if (batchDto.getBatMsoIngStkKey() != null) {
						calculateformulationsIngredients(ingredientList, date);
					} else {
						// determine whether the ingredients are chargeable for the particular customer
						// and set the chargeable field accordingly
						// we need to form the stock key list
						List<String> stockKeyList = new ArrayList<>();
						stockKeyList.add(batchDto.getStkKey());
						if (batchDto.getStkKey2().isEmpty() != true) {
							stockKeyList.add(batchDto.getStkKey2());
						}
						if (batchDto.getStkKey3().isEmpty() != true) {
							stockKeyList.add(batchDto.getStkKey3());
						}

						// Need to iterate through the collection and find out if the stock is
						// chargeable or not for the customer
						for (String stockKey : stockKeyList) {
							for (IngredientsDTO ingredient : ingredientList) {
								String ingChargeable = calculateChargeforIngredients(batchDto.getBatCusKey(), stockKey,
										ingredient.getXxxType());
								ingredient.setIngChargeable(ingChargeable);
								ingredient.setIngLastUpdAction("I");
								ingredient.setIngLastUpdWhen(date);
								ingredient.setIngStatus("N");
							}
						}

					}

					return batchDTO;

				} // end of function

			};

			session = (Session) entityManager.getDelegate();
			return session.doReturningWork(work);

		} catch (Exception e) {
			logger.error("Failed to calculate the ingredients for a batch in calculateIngredients() due to",
					e.getCause());
			throw new DataAccessException(e.getMessage());
		}

	}

	@SuppressWarnings("unchecked")
	public List<OrderHistoryDTO> getOrderHistoryList2(String customerId, Long patientId) {
		Query query = entityManager.createQuery("from 	ShotBatch as batch " + "where 	batch.customerId = :customerId "
				+ "order by batch.createdDate desc").setParameter("customerId", customerId);
		query.setMaxResults(this.lastNHistoryBatches).getResultList();
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<OrderHistoryDTO> getOrderHistoryList(String customerId, String patientId) {
		Query query = entityManager
				.createQuery("select " + "new com.telstra.health.shot.dto.OrderHistoryDTO( " + "batch.batchId, "
						+ "batch.productDescription, " + "batch.dose, " + "batch.doseUnit, "
						+ "batch.productDescription2, " + "batch.dose2, " + "batch.doseUnit2, "
						+ "batch.productDescription3, " + "batch.dose3, " + "batch.doseUnit3, "
						+ "batch.deliveryMechanismKey, " + "batch.deliveryMechanismDescription, "
						+ "batch.closedSystem, " + "batch.specifiedVolume, " + "batch.exact, "
						+ "batch.infusionDuration, " + "batch.routeId, " + "batch.routeName, " + "batch.quantity, "
						+ "batch.comments, " + "batch.createdDate " + ") " + "from 	ShotBatch as batch "
						+ "where 	batch.customerId = :customerId "
						+ ((ShotUtils.isEmpty(patientId) == false) ? "and batch.patientId = :patientId "
								: "and batch.patientId is null ")
						+ "order by batch.createdDate desc")
				.setParameter("customerId", customerId);
		if (ShotUtils.isEmpty(patientId) == false) {
			query.setParameter("patientId", patientId);
		}
		return query.setMaxResults(this.lastNHistoryBatches).getResultList();
	}

	// function responsible for generating a key for a BATCH
	// input parameter is string which could be the entity name as BAT
	// Key for the entity.
	// User Id who is updating the batch
	public String GenerateKey(String entityName, String entityKey, int userId) throws DataAccessException {

		Session session = null;
		try {

			session = (Session) entityManager.getDelegate();

			// fetches the data from the DB and return it
			ReturningWork<String> work = new ReturningWork<String>() {

				@SuppressWarnings("resource")
				@Override
				public String execute(Connection connection) throws SQLException {

					String generatedKey = null;
					if (entityName.equals("Bat")) {
						PreparedStatement ps = null;
						ResultSet rs = null;
						try {

							String updateQuery = "update idn_identity set idn_value=idn_value + 1  , idn_lastupdby= ?, idn_lastupdwhen=getdate(), idn_lastupdaction='U'   where idn_type = ? and  idn_subtype = ?  and IDN_EntKey = ? ";
							ps = connection.prepareStatement(updateQuery);
							ps.setInt(1, userId);
							ps.setString(2, "BAT");
							ps.setString(3, "ESSQL003");
							ps.setString(4, entityKey);
							int returnUpdate = ps.executeUpdate();
							if (returnUpdate == 1) {

								String query = "select 'B'+ IDN_SubTypeAbbrev + right(cast(datepart(Year,getdate()) as varchar(4)),2) + right('0'+ cast(datepart(MONTH,getdate()) as varchar(2)),2)  + cast(IDN_VALUE as varchar(10)) as NextBatchNumber from ENT_Entity join IDN_Identity on IDN_ENTKey = ENT_KEY and IDN_TYPE = ?  where IDN_ENTKey = ?";

								ps = connection.prepareStatement(query);
								ps.setString(1, "BAT");
								ps.setString(2, entityKey);
								rs = ps.executeQuery();
								if (rs.next()) {
									generatedKey = rs.getString(1);

								}
							}

						} finally {
							if (rs != null)
								rs.close();
							ps.close();

						}
					} else {

						CallableStatement call = connection.prepareCall("{call SP_GetNextKey(?,?)}");
						call.registerOutParameter(2, java.sql.Types.VARCHAR);
						call.setString(1, entityName);
						boolean hasResults = call.execute();
						generatedKey = call.getString(2);

					}
					return generatedKey;
				}
			};
			session = (Session) entityManager.getDelegate();
			return session.doReturningWork(work);
		} catch (Exception e) {
			logger.error("Failed to generate key in GenerateKey()", e.getMessage());
			throw new DataAccessException(e.getMessage());
		}
	}

	// this function will determine whether ingredients will be chargeable or not
	private String calculateChargeforIngredients(String cusKey, String stockCode, String ingType) {

		Session session = null;
		try {
			session = (Session) entityManager.getDelegate();
			// fetches the data from the DB and return it
			ReturningWork<String> work = new ReturningWork<String>() {
				@Override
				public String execute(Connection connection) throws SQLException {
					logger.debug(
							" Calculating the batch ingredients for the customer: {} ,stock code: {} and ing type:{} ",
							cusKey, stockCode, ingType);
					PreparedStatement ps = null;
					ResultSet rs = null;
					String query = "select cus_mainstate from cus_customer where cus_key =? ";
					ps = connection.prepareStatement(query);
					ps.setString(1, cusKey);
					rs = ps.executeQuery();
					if (rs.next()) {
						String[] customerKeys = { "16SYD101", "10SYD101", "30syd101" };
						String cusState = rs.getString("cus_mainstate");
						if (stockCode != null && stockCode.substring(0, 2) == "23"
								&& (Arrays.asList(customerKeys).contains(cusKey) || (cusState == "QLD"))) {
							return "N";
						} else if (ingType.equals("D") || ingType.equals("C") || ingType.equals("M")) {
							return "Y";
						}
						return "N";
					}

					return "Y";
				}
			};
			session = (Session) entityManager.getDelegate();
			return session.doReturningWork(work);
		} catch (Exception e) {
			logger.error("Failed to calculate the charge for ingrdient list in the calculateChargeforIngredients()",
					e.getMessage());
			throw new DataAccessException(e.getMessage());
		}
	}

	// this function will set the ingredients data for the formulation drugs
	private void calculateformulationsIngredients(List<IngredientsDTO> ingredientList, Date date) {
		for (IngredientsDTO ingredient : ingredientList) {
			ingredient.setIngChargeable("Y");
			ingredient.setIngLastUpdAction("I");
			ingredient.setIngLastUpdWhen(date);
			ingredient.setIngStatus("N");
		}
	}

	// this function is responsible for updating the comments in the Shot Batch and
	// Batch Tables
	public boolean updateBatchComments(String customerID, Long shotBatchId, String newComments, String batchId,
			Timestamp updatedDateTime) {
		try {
			Query query = entityManager.createQuery(
					"update ShotBatch SET Comments=:newComments, UpdatedDate=:updatedDate  WHERE CustomerId = :customerId AND ShotBatchId=:shotBatchId")
					.setParameter("newComments", newComments).setParameter("updatedDate", updatedDateTime)
					.setParameter("customerId", customerID).setParameter("shotBatchId", shotBatchId);

			int entityUpdated = query.executeUpdate();
			if (entityUpdated >= 1) {
				updateAxisBatchComments(batchId, newComments, updatedDateTime);
				return true;
			}
		} catch (Exception ex) {
			logger.error("OrderDAOImpl.updateBatchComments() : Failed to update the SHOT Batch comments  : ",
					ex.getCause());
			throw new ShotServiceException(ex.getMessage());
		}
		return false;

	}

	private boolean updateAxisBatchComments(String batchId, String newComments, Timestamp updateDateTime) {
		try {
			Query query = entityManager.createQuery(
					"UPDATE Batch SET batComments = :newComments, batLastUpdWhen= :updateDateTime  WHERE batkey=:batchId")
					.setParameter("newComments", newComments).setParameter("batchId", batchId)
					.setParameter("updateDateTime", updateDateTime);

			int entityUpdated = query.executeUpdate();
			if (entityUpdated >= 1) {
				return true;
			}
		} catch (Exception ex) {
			logger.error("OrderDAOImpl.updateAxisBatchComments() : Failed to update the Axis Batch comments  : ",
					ex.getCause());
			throw new ShotServiceException(ex.getMessage());
		}
		return false;
	}

	// the purpose of the function is to delete the ingredients for a given Batch.
	public boolean updateBatchIngredients(String batchKey) {
		try {
			Query query = this.entityManager.createQuery("from  AxisIngredient  WHERE ING_BATKey=:batchId")
					.setParameter("batchId", batchKey);
			List<AxisIngredient> axisIngredient = query.getResultList();
			if (axisIngredient.size() > 0) {
				Query queryDelete = this.entityManager
						.createQuery("DELETE from AxisIngredient WHERE ING_BATKey=:batchId")
						.setParameter("batchId", batchKey);

				int rowsAffected = queryDelete.executeUpdate();
				if (rowsAffected > 0) {
					return true;
				}
			}
		} catch (Exception ex) {
			logger.error("OrderDAOImpl.updateBatchIngredients() : Failed to delete the axis ingredients record  : ",
					ex.getCause());
			throw new ShotServiceException(ex.getMessage());
		}
		return false;

	}

	// the purpose of the function is to update the order status.
	public boolean updateOrderStatus(String orderKey) {
		try {
			boolean stausfoundFlag = true;
			Query q = this.entityManager.createNativeQuery("SELECT a.bat_status FROM bat_batch a WHERE a.bat_ordkey=:orderKey");
			q.setParameter("orderKey", orderKey);
			List<String> batchStatus = q.getResultList();
			
			if (batchStatus.size() > 0) {
				for (String status : batchStatus) {
					if (status.equalsIgnoreCase(BatchStatus.INVOICED.getStatus())
							|| status.equalsIgnoreCase(BatchStatus.QUARANTINE.getStatus())) {
						continue;
					} else {
						stausfoundFlag = false;
						break;
					}
				}
			}
			else
			{
				//order is empty or no batches.
				return true;
			}

			if (stausfoundFlag == false) {
				return false;
			}
			return true;

		} catch (Exception ex) {
			logger.error("OrderDAOImpl.updateOrderStatus() : Failed to update the axis Order record  : ",
					ex.getCause());
			throw new ShotServiceException(ex.getMessage());
		}

	}

}
