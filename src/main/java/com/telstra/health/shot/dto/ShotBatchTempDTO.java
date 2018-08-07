package com.telstra.health.shot.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ShotBatchTempDTO {

	private Long batchId;
	// this is the treatment date/time
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private String treatmentDateTime;
	private String status;
	private String productDescription;
	private boolean closedSystem;
	private double infusionDuration;
	private String comments;
	private String routeName;
	private String deliveryMechanismDescription;
	private String productType;
	private Integer quantity;
	private Boolean isDeliveryRunRestricted;
	private Boolean hasDeliveryRunIncentive;
	private String productDescription2;
	private String productDescription3;
	
	public String getTreatmentDateTime() {
		return treatmentDateTime;
	}

	public void setTreatmentDateTime(String treatmentDateTime) {
		this.treatmentDateTime = treatmentDateTime;
	}

	public Long getBatchId() {
		return batchId;
	}

	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public boolean isClosedSystem() {
		return closedSystem;
	}

	public void setClosedSystem(boolean closedSystem) {
		this.closedSystem = closedSystem;
	}

	public double getInfusionDuration() {
		return infusionDuration;
	}

	public void setInfusionDuration(double infusionDuration) {
		this.infusionDuration = infusionDuration;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public String getDeliveryMechanismDescription() {
		return deliveryMechanismDescription;
	}

	public void setDeliveryMechanismDescription(String deliveryMechanismDescription) {
		this.deliveryMechanismDescription = deliveryMechanismDescription;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public Boolean getIsDeliveryRunRestricted() {
		return isDeliveryRunRestricted;
	}

	public void setIsDeliveryRunRestricted(Boolean isDeliveryRunRestricted) {
		this.isDeliveryRunRestricted = isDeliveryRunRestricted;
	}

	public Boolean getHasDeliveryRunIncentive() {
		return hasDeliveryRunIncentive;
	}

	public void setHasDeliveryRunIncentive(Boolean hasDeliveryRunIncentive) {
		this.hasDeliveryRunIncentive = hasDeliveryRunIncentive;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getProductDescription2() {
		return productDescription2;
	}

	public void setProductDescription2(String productDescription2) {
		this.productDescription2 = productDescription2;
	}

	public String getProductDescription3() {
		return productDescription3;
	}

	public void setProductDescription3(String productDescription3) {
		this.productDescription3 = productDescription3;
	}

	
	
}
