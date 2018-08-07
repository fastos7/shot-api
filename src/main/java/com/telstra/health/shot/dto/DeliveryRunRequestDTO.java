package com.telstra.health.shot.dto;

import java.util.List;
import java.util.Map;

public class DeliveryRunRequestDTO {
    String customerKey;
    Integer orderType;
    Integer quantity;
    String entityKey;
    List < DeliveryRunQuantityDTO > deliveryRunQuantites;
    Map < String, Integer > deliveryRunQtyMap;

    public static class DeliveryRunQuantityDTO {
    	String dispatchDateTime;
    	Integer totalQuantity;
    	
		public String getDispatchDateTime() {
			return dispatchDateTime;
		}
		public void setDispatchDateTime(String deliveryDateTime) {
			this.dispatchDateTime = deliveryDateTime;
		}
		public Integer getTotalQuantity() {
			return totalQuantity;
		}
		public void setTotalQuantity(Integer totalQuantity) {
			this.totalQuantity = totalQuantity;
		}
    }

	public String getCustomerKey() {
		return customerKey;
	}

	public void setCustomerKey(String customerKey) {
		this.customerKey = customerKey;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getEntityKey() {
		return entityKey;
	}

	public void setEntityKey(String entityKey) {
		this.entityKey = entityKey;
	}

	public List<DeliveryRunQuantityDTO> getDeliveryRunQuantites() {
		return deliveryRunQuantites;
	}

	public void setDeliveryRunQuantites(List<DeliveryRunQuantityDTO> deliveryRunQuantites) {
		this.deliveryRunQuantites = deliveryRunQuantites;
	}

	public Map<String, Integer> getDeliveryRunQtyMap() {
		return deliveryRunQtyMap;
	}

	public void setDeliveryRunQtyMap(Map<String, Integer> deliveryRunQtyMap) {
		this.deliveryRunQtyMap = deliveryRunQtyMap;
	}
}

