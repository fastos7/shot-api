package com.telstra.health.shot.dto;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * DTO class for representing the Batch Order History
 * @author osama.shakeel
 *
 */
public class OrderHistoryDTO {
	@JsonIgnore
	private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private String batchId;
	
	private ProductDTO product;
	private String productDesc;
	private String productDesc2;
	private String productDesc3;

	
	private BigDecimal dose;
	private BigDecimal dose2;
	private BigDecimal dose3;
	private String doseUnit;
	private String doseUnit2;
	private String doseUnit3;
	
	private String deliveryMechanismKey;
	private String deliveryMechanismDesc;
	
	private BigDecimal volume;
	private String exact;
	private Boolean closedSystem;
	
	private String infusionDuration;
	
	private String routeId;
	private String routeName;
	private Integer quantity;
	private String comments;
	private String createdDate;	

	public OrderHistoryDTO(String batchId, String productDesc, BigDecimal dose, String doseUnit,
			String productDesc2, BigDecimal dose2, String doseUnit2,
			String productDesc3, BigDecimal dose3, String doseUnit3,
			String deliveryMechanismKey, String deliveryMechanismDesc, Boolean closedSystem,
			BigDecimal volume, String exact, String infusionDuration, String routeId, String routeName, Integer quantity, String comments,
			Date createdDate) {
		this.batchId = batchId;

		//this.product = new ProductDTO(productId, productName);

		this.productDesc = productDesc;
		this.dose = dose;
		this.doseUnit = doseUnit;
		this.productDesc2 = productDesc2;
		this.dose2 = dose2;
		this.doseUnit2 = doseUnit2;
		this.productDesc3 = productDesc3;
		this.dose3 = dose3;
		this.doseUnit3 = doseUnit3;
		this.deliveryMechanismKey = deliveryMechanismKey;
		this.deliveryMechanismDesc = deliveryMechanismDesc;
		this.closedSystem = closedSystem;
		this.volume = volume;
		this.exact = exact;
		this.infusionDuration = infusionDuration;
		this.routeId = routeId;
		this.routeName = routeName;
		this.quantity = quantity;
		this.comments = comments;
		try {
			this.createdDate = dateFormat.format(createdDate);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
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

	public String getDeliveryMechanismDesc() {
		return deliveryMechanismDesc;
	}

	public void setDeliveryMechanismDesc(String containerDesc) {
		this.deliveryMechanismDesc = containerDesc;
	}

	public BigDecimal getVolume() {
		return volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	public String getExact() {
		return exact;
	}

	public void setExact(String exact) {
		this.exact = exact;
	}

	public String getInfusionDuration() {
		return infusionDuration;
	}

	public void setInfusionDuration(String infusionDuration) {
		this.infusionDuration = infusionDuration;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String route) {
		this.routeName = route;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
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
	
	@SuppressWarnings("unused")
	private class ProductDTO {
		private String productId;
		private String name;
		
		public ProductDTO(String productId, String productName) {
			super();
			this.productId = productId;
			this.name = productName;
		}
		public String getProductId() {
			return productId;
		}
		public void setProductId(String productId) {
			this.productId = productId;
		}
		public String getName() {
			return name;
		}
		public void setName(String productName) {
			this.name = productName;
		}
	}

	public ProductDTO getProduct() {
		return product;
	}

	public void setProduct(ProductDTO product) {
		this.product = product;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getDeliveryMechanismKey() {
		return deliveryMechanismKey;
	}

	public void setDeliveryMechanismKey(String deliveryMechanismKey) {
		this.deliveryMechanismKey = deliveryMechanismKey;
	}

	public Boolean getClosedSystem() {
		return closedSystem;
	}

	public void setClosedSystem(Boolean closedSystem) {
		this.closedSystem = closedSystem;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getProductDesc2() {
		return productDesc2;
	}

	public void setProductDesc2(String productDesc2) {
		this.productDesc2 = productDesc2;
	}

	public String getProductDesc3() {
		return productDesc3;
	}

	public void setProductDesc3(String productDesc3) {
		this.productDesc3 = productDesc3;
	}

	public BigDecimal getDose2() {
		return dose2;
	}

	public void setDose2(BigDecimal dose2) {
		this.dose2 = dose2;
	}

	public BigDecimal getDose3() {
		return dose3;
	}

	public void setDose3(BigDecimal dose3) {
		this.dose3 = dose3;
	}

	public String getDoseUnit2() {
		return doseUnit2;
	}

	public void setDoseUnit2(String doseUnit2) {
		this.doseUnit2 = doseUnit2;
	}

	public String getDoseUnit3() {
		return doseUnit3;
	}

	public void setDoseUnit3(String doseUnit3) {
		this.doseUnit3 = doseUnit3;
	}
}
