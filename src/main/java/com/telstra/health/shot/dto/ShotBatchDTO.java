package com.telstra.health.shot.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class ShotBatchDTO implements Serializable {

	private static final long serialVersionUID = -802386292700935813L;

	private Long shotBatchId;
	private String batchId;
	private String orderId;
	private String customerId;
	private Timestamp deliveryDateTime;
	private String deliveryDate;
	private String deliveryTime;
	private Timestamp treatmentDateTime;
	private String treatmentDate;
	private String treatmentTime;
	private String ordNo;
	private String ordDeliverylocation;
	private String deliveryLocationId;
	private String patientFirstName;
	private String patientLastName;
	private String patientDob;
	private String patientUr;
	private String patientId;
	private BigDecimal price;
	private String productDescription;
	private BigDecimal dose;
	private String doseUnit;
	private String productDescription2;
	private BigDecimal dose2;
	private String doseUnit2;
	private String productDescription3;
	private BigDecimal dose3;
	private String doseUnit3;
	private String deliveryMechanismKey;
	private String deliveryMechanismDescription;
	private boolean closedSystem;
	private BigDecimal specifiedVolume;
	private String exact;
	private String comments;
	private String routeId;
	private String routeName;
	private int quantity;
	private String infusionDuration;
	private String expiryDate;
	private String Status;
	private String ordBillto;
	private Timestamp createdDate;
	private int updatedBy;
	// Adding the required fields according to the Batch API
	private String stkKey;
	private String stkKey2;
	private String stkKey3;
	private String batDsuKey;
	private String batEntKey;
	private String batDispatchDatetime;
	// identifier whether the comments,Order or Batch has been updated or not in the SHOT UI while updating the batch.
	private String changeType;

	public Long getShotBatchId() {
		return shotBatchId;
	}

	public void setShotBatchId(Long shotBatchId) {
		this.shotBatchId = shotBatchId;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Timestamp getDeliveryDateTime() {
		return deliveryDateTime;
	}

	public void setDeliveryDateTime(Timestamp deliveryDateTime) {
		this.deliveryDateTime = deliveryDateTime;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Timestamp getTreatmentDateTime() {
		return treatmentDateTime;
	}

	public void setTreatmentDateTime(Timestamp treatmentDateTime) {
		this.treatmentDateTime = treatmentDateTime;
	}

	public String getTreatmentDate() {
		return treatmentDate;
	}

	public void setTreatmentDate(String treatmentDate) {
		this.treatmentDate = treatmentDate;
	}

	public String getTreatmentTime() {
		return treatmentTime;
	}

	public void setTreatmentTime(String treatmentTime) {
		this.treatmentTime = treatmentTime;
	}

	public String getOrdNo() {
		return ordNo;
	}

	public void setOrdNo(String ordNo) {
		this.ordNo = ordNo;
	}

	public String getOrdDeliverylocation() {
		return ordDeliverylocation;
	}

	public void setOrdDeliverylocation(String ordDeliverylocation) {
		this.ordDeliverylocation = ordDeliverylocation;
	}

	public String getDeliveryLocationId() {
		return deliveryLocationId;
	}

	public void setDeliveryLocationId(String deliveryLocationId) {
		this.deliveryLocationId = deliveryLocationId;
	}

	public String getPatientFirstName() {
		return patientFirstName;
	}

	public void setPatientFirstName(String patientFirstName) {
		this.patientFirstName = patientFirstName;
	}

	public String getPatientLastName() {
		return patientLastName;
	}

	public void setPatientLastName(String patientLastName) {
		this.patientLastName = patientLastName;
	}

	public String getPatientDob() {
		return patientDob;
	}

	public void setPatientDob(String patientDob) {
		this.patientDob = patientDob;
	}

	public String getPatientUr() {
		return patientUr;
	}

	public void setPatientUr(String patientUr) {
		this.patientUr = patientUr;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public BigDecimal getDose() {
		return dose;
	}

	public void setDose(BigDecimal dose) {
		this.dose = dose;
	}

	public String getDoseUnit() {
		return doseUnit;
	}

	public void setDoseUnit(String doseUnit) {
		this.doseUnit = doseUnit;
	}

	public String getProductDescription2() {
		return productDescription2;
	}

	public void setProductDescription2(String productDescription2) {
		this.productDescription2 = productDescription2;
	}

	public BigDecimal getDose2() {
		return dose2;
	}

	public void setDose2(BigDecimal dose2) {
		this.dose2 = dose2;
	}

	public String getDoseUnit2() {
		return doseUnit2;
	}

	public void setDoseUnit2(String doseUnit2) {
		this.doseUnit2 = doseUnit2;
	}

	public String getProductDescription3() {
		return productDescription3;
	}

	public void setProductDescription3(String productDescription3) {
		this.productDescription3 = productDescription3;
	}

	public BigDecimal getDose3() {
		return dose3;
	}

	public void setDose3(BigDecimal dose3) {
		this.dose3 = dose3;
	}

	public String getDoseUnit3() {
		return doseUnit3;
	}

	public void setDoseUnit3(String doseUnit3) {
		this.doseUnit3 = doseUnit3;
	}

	public String getDeliveryMechanismKey() {
		return deliveryMechanismKey;
	}

	public void setDeliveryMechanismKey(String deliveryMechanismKey) {
		this.deliveryMechanismKey = deliveryMechanismKey;
	}

	public String getDeliveryMechanismDescription() {
		return deliveryMechanismDescription;
	}

	public void setDeliveryMechanismDescription(String deliveryMechanismDescription) {
		this.deliveryMechanismDescription = deliveryMechanismDescription;
	}

	public boolean isClosedSystem() {
		return closedSystem;
	}

	public void setClosedSystem(boolean closedSystem) {
		this.closedSystem = closedSystem;
	}

	public BigDecimal getSpecifiedVolume() {
		return specifiedVolume;
	}

	public void setSpecifiedVolume(BigDecimal specifiedVolume) {
		this.specifiedVolume = specifiedVolume;
	}

	public String getExact() {
		return exact;
	}

	public void setExact(String exact) {
		this.exact = exact;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getInfusionDuration() {
		return infusionDuration;
	}

	public void setInfusionDuration(String infusionDuration) {
		this.infusionDuration = infusionDuration;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getOrdBillto() {
		return ordBillto;
	}

	public void setOrdBillto(String ordBillto) {
		this.ordBillto = ordBillto;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getStkKey() {
		return stkKey;
	}

	public void setStkKey(String stkKey) {
		this.stkKey = stkKey;
	}

	public String getBatDsuKey() {
		return batDsuKey;
	}

	public void setBatDsuKey(String batDsuKey) {
		this.batDsuKey = batDsuKey;
	}

	public String getBatEntKey() {
		return batEntKey;
	}

	public void setBatEntKey(String batEntKey) {
		this.batEntKey = batEntKey;
	}

	public String getBatDispatchDatetime() {
		return batDispatchDatetime;
	}

	public void setBatDispatchDatetime(String batDispatchDatetime) {
		this.batDispatchDatetime = batDispatchDatetime;
	}

	public String getStkKey2() {
		return stkKey2;
	}

	public void setStkKey2(String stkKey2) {
		this.stkKey2 = stkKey2;
	}

	public String getStkKey3() {
		return stkKey3;
	}

	public void setStkKey3(String stkKey3) {
		this.stkKey3 = stkKey3;
	}

	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

}
