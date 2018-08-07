package com.telstra.health.shot.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "SHOT_Batch")
public class ShotBatch implements Serializable {

	private static final long serialVersionUID = -902110696728396723L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ShotBatchId", unique = true)
	private Long shotBatchId;

	@Column(name = "BatchId")
	private String batchId;

	@Column(name = "OrderId")
	private String orderId;

	@Column(name = "CustomerId")
	private String customerId;

	@Column(name = "DeliveryDateTime")
	private Timestamp ordDeliveryDate;

	@Column(name = "TreatmentDateTime")
	private Timestamp treatmentDateTime;

	@Column(name = "PONumber")
	private String ordNo;

	@Column(name = "DeliveryLocation")
	private String ordDeliveryLocationName;

	@Column(name = "DeliveryLocationId")
	private String ordDeliveryLocation;

	@Column(name = "PatientFirstName")
	private String patientFirstName;

	@Column(name = "PatientLastName")
	private String patientLastName;

	@Column(name = "PatientDOB")
	private Timestamp patientDob;

	@Column(name = "PatientUR")
	private String patientUr;

	@Column(name = "PatientId")
	private String patientId;

	@Column(name = "price")
	private String price;

	@Column(name = "ProductDescription2")
	private String productDescription2;

	@Column(name = "Dose2")
	private BigDecimal dose2;

	@Column(name = "DoseUnit2")
	private String doseUnit2;

	@Column(name = "ProductDescription3")
	private String productDescription3;

	@Column(name = "Dose3")
	private BigDecimal dose3;

	@Column(name = "DoseUnit3")
	private String doseUnit3;

	@Column(name = "ProductDescription")
	private String productDescription;

	@Column(name = "Dose")
	private BigDecimal dose;

	@Column(name = "DoseUnit")
	private String doseUnit;

	@Column(name = "DeliveryMechanismKey")
	private String deliveryMechanismKey;

	@Column(name = "DeliveryMechanismDescription")
	private String deliveryMechanismDescription;

	@Column(name = "ClosedSystem")
	@Type(type = "yes_no")
	private Boolean closedSystem;

	@Column(name = "SpecifiedVolume")
	private BigDecimal specifiedVolume;

	@Column(name = "Exact")
	private String exact;

	@Column(name = "Comments")
	private String comments;

	@Column(name = "RouteId")
	private String routeId;

	@Column(name = "RouteName")
	private String routeName;

	@Column(name = "Quantity")
	private Integer quantity;

	@Column(name = "InfusionDuration")
	private String infusionDuration;

	@Column(name = "ExpiryDate")
	private Timestamp expiryDate;

	@Column(name = "Status")
	private String status;

	@Column(name = "BillTo")
	private String ordBillTo;

	@Column(name = "IsRestricted")
	@Type(type = "yes_no")
	private Boolean isDeliveryRunRestricted;

	@Column(name = "HasIncentive")
	@Type(type = "yes_no")
	private Boolean hasDeliveryRunIncentive;

	@Column(name = "CreatedDate")
	private Timestamp createdDate;

	@Column(name = "CreatedBy")
	private int createdBy;

	@Column(name = "UpdatedDate")
	private Timestamp updatedDate;

	@Column(name = "UpdatedBy")
	private Integer updatedBy;

	@Column(name = "EntityKey")
	private String batEntKey;

	@Column(name = "DispatchDateTime")
	private String batDispatchDatetime;

	@OneToMany(mappedBy = "shotBatch")
	private List<Batch> batches;
	
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

	public String getOrdCuskey() {
		return customerId;
	}

	public void setOrdCuskey(String ordCuskey) {
		this.customerId = ordCuskey;
	}

	public Timestamp getOrdDeliveryDate() {
		return ordDeliveryDate;
	}

	public void setOrdDeliveryDate(Timestamp ordDeliveryDate) {
		this.ordDeliveryDate = ordDeliveryDate;
	}

	public Timestamp getTreatmentDateTime() {
		return treatmentDateTime;
	}

	public void setTreatmentDateTime(Timestamp treatmentDateTime) {
		this.treatmentDateTime = treatmentDateTime;
	}

	public String getOrdNo() {
		return ordNo;
	}

	public void setOrdNo(String ordNo) {
		this.ordNo = ordNo;
	}

	public String getOrdDeliveryLocationName() {
		return ordDeliveryLocationName;
	}

	public void setOrdDeliveryLocationName(String ordDeliveryLocationName) {
		this.ordDeliveryLocationName = ordDeliveryLocationName;
	}

	public String getOrdDeliveryLocation() {
		return ordDeliveryLocation;
	}

	public void setOrdDeliveryLocation(String ordDeliveryLocation) {
		this.ordDeliveryLocation = ordDeliveryLocation;
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

	public Timestamp getPatientDob() {
		return patientDob;
	}

	public void setPatientDob(Timestamp patientDob) {
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

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
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

	public Boolean getClosedSystem() {
		return closedSystem;
	}

	public void setClosedSystem(Boolean closedSystem) {
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

	public String getBatAdminrouteCode() {
		return routeId;
	}

	public void setBatAdminrouteCode(String batAdminrouteCode) {
		this.routeId = batAdminrouteCode;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getInfusionDuration() {
		return infusionDuration;
	}

	public void setInfusionDuration(String infusionDuration) {
		this.infusionDuration = infusionDuration;
	}

	public Timestamp getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Timestamp expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrdBillTo() {
		return ordBillTo;
	}

	public void setOrdBillTo(String ordBillTo) {
		this.ordBillTo = ordBillTo;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public List<Batch> getBatches() {
		return batches;
	}

	public void setBatches(List<Batch> batches) {
		this.batches = batches;
	}

	public Boolean getIsDeliveryRunRestricted() {
		return isDeliveryRunRestricted;
	}

	public void setIsDeliveryRunRestricted(Boolean isDeliveryRunRestricted) {
		this.isDeliveryRunRestricted = isDeliveryRunRestricted;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Integer getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Long getShotBatchId() {
		return shotBatchId;
	}

	public void setShotBatchId(Long shotBatchId) {
		this.shotBatchId = shotBatchId;
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

	public Boolean getHasDeliveryRunIncentive() {
		return hasDeliveryRunIncentive;
	}

	public void setHasDeliveryRunIncentive(Boolean hasIncentive) {
		this.hasDeliveryRunIncentive = hasIncentive;
	}
	
	

}