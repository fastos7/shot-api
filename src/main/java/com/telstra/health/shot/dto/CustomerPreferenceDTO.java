package com.telstra.health.shot.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * TODO: Insert class explaination here.
 *
 * @author Marlon Cenita
 *
 */
public class CustomerPreferenceDTO implements Serializable {

	private static final long serialVersionUID = -3483356056838614987L;
	
	private int prefId;
	private String customerKey;
	private String customerName;	
	private String productDescription;
	private String productType;
	private String batDrugKey;
	private String batDSUKey;
	private String triKey;
	private String msoIngStkKey;
	private String batFormulation;
	private Double doseFrom;
	private Double doseTo;
	private String unitOfMeasure;
	private DeliveryMechanismsDTO deliveryMechanism;	
	private Double volume;
	private char exact;
	private Integer quantity;
	private AdministrationRouteDTO administrationRoute;
	private Double infusionDuration;
	private Integer rank;
	private String comments;
	private Integer createdBy;	
	/**
	 * This will transform the <code>Timestamp</code> into json in the format provided below but using UTC timezone and
	 * not the local timezone. When persisting data using this DTO, it should be converted to local timezone.
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp createdDate;
	private int updatedBy;
	/**
	 * This will transform the <code>Timestamp</code> into json in the format provided below but using UTC timezone and
	 * not the local timezone. When persisting data using this DTO, it should be converted to local timezone.
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp updatedDate;
	
	public CustomerPreferenceDTO() {
		super();
	}

	public CustomerPreferenceDTO(int prefId, String customerKey, String customerName, 
			String productDescription, String productType, String batDrugKey, String batDSUKey, String triKey,
			String msoIngStkKey, String batFormulation, Double doseFrom, Double doseTo, String unitOfMeasure,
			DeliveryMechanismsDTO deliveryMechanism, Double volume, char exact, Integer quantity,
			AdministrationRouteDTO administrationRoute, Double infusionDuration, Integer rank, String comments,
			Integer createdBy, Timestamp createdDate, Integer updatedBy, Timestamp updatedDate) {
		super();
		this.prefId = prefId;
		this.customerKey = customerKey;
		this.customerName = customerName;		
		this.productDescription = productDescription;
		this.productType = productType;
		this.batDrugKey = batDrugKey;
		this.batDSUKey = batDSUKey;
		this.triKey = triKey;
		this.msoIngStkKey = msoIngStkKey;
		this.batFormulation = batFormulation;
		this.doseFrom = doseFrom;
		this.doseTo = doseTo;
		this.unitOfMeasure = unitOfMeasure;
		this.deliveryMechanism = deliveryMechanism;
		this.volume = volume;
		this.exact = exact;
		this.quantity = quantity;
		this.administrationRoute = administrationRoute;
		this.infusionDuration = infusionDuration;
		this.rank = rank;
		this.comments = comments;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.updatedBy = updatedBy;
		this.updatedDate = updatedDate;
	}

	public int getPrefId() {
		return prefId;
	}

	public void setPrefId(int prefId) {
		this.prefId = prefId;
	}

	public String getCustomerKey() {
		return customerKey;
	}

	public void setCustomerKey(String customerKey) {
		this.customerKey = customerKey;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getBatDrugKey() {
		return batDrugKey;
	}

	public void setBatDrugKey(String batDrugKey) {
		this.batDrugKey = batDrugKey;
	}

	public String getBatDSUKey() {
		return batDSUKey;
	}

	public void setBatDSUKey(String batDSUKey) {
		this.batDSUKey = batDSUKey;
	}

	public String getTriKey() {
		return triKey;
	}

	public void setTriKey(String triKey) {
		this.triKey = triKey;
	}

	public String getMsoIngStkKey() {
		return msoIngStkKey;
	}

	public void setMsoIngStkKey(String msoIngStkKey) {
		this.msoIngStkKey = msoIngStkKey;
	}

	public String getBatFormulation() {
		return batFormulation;
	}

	public void setBatFormulation(String batFormulation) {
		this.batFormulation = batFormulation;
	}

	public Double getDoseFrom() {
		return doseFrom;
	}

	public void setDoseFrom(Double doseFrom) {
		this.doseFrom = doseFrom;
	}

	public Double getDoseTo() {
		return doseTo;
	}

	public void setDoseTo(Double doseTo) {
		this.doseTo = doseTo;
	}

	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	public DeliveryMechanismsDTO getDeliveryMechanism() {
		return deliveryMechanism;
	}

	public void setDeliveryMechanism(DeliveryMechanismsDTO deliveryMechanism) {
		this.deliveryMechanism = deliveryMechanism;
	}

	public Double getVolume() {
		return volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

	public char getExact() {
		return exact;
	}

	public void setExact(char exact) {
		this.exact = exact;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public AdministrationRouteDTO getAdministrationRoute() {
		return administrationRoute;
	}

	public void setAdministrationRoute(AdministrationRouteDTO administrationRoute) {
		this.administrationRoute = administrationRoute;
	}

	public Double getInfusionDuration() {
		return infusionDuration;
	}

	public void setInfusionDuration(Double infusionDuration) {
		this.infusionDuration = infusionDuration;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((administrationRoute == null) ? 0 : administrationRoute.hashCode());
		result = prime * result + ((batDSUKey == null) ? 0 : batDSUKey.hashCode());
		result = prime * result + ((batDrugKey == null) ? 0 : batDrugKey.hashCode());
		result = prime * result + ((batFormulation == null) ? 0 : batFormulation.hashCode());
		result = prime * result + ((comments == null) ? 0 : comments.hashCode());
		result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result + ((customerKey == null) ? 0 : customerKey.hashCode());
		result = prime * result + ((customerName == null) ? 0 : customerName.hashCode());
		result = prime * result + ((deliveryMechanism == null) ? 0 : deliveryMechanism.hashCode());
		result = prime * result + ((doseFrom == null) ? 0 : doseFrom.hashCode());
		result = prime * result + ((doseTo == null) ? 0 : doseTo.hashCode());
		result = prime * result + exact;
		result = prime * result + ((infusionDuration == null) ? 0 : infusionDuration.hashCode());
		result = prime * result + ((msoIngStkKey == null) ? 0 : msoIngStkKey.hashCode());
		result = prime * result + prefId;
		result = prime * result + ((productDescription == null) ? 0 : productDescription.hashCode());
		result = prime * result + ((productType == null) ? 0 : productType.hashCode());
		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
		result = prime * result + ((rank == null) ? 0 : rank.hashCode());
		result = prime * result + ((triKey == null) ? 0 : triKey.hashCode());
		result = prime * result + ((unitOfMeasure == null) ? 0 : unitOfMeasure.hashCode());
		result = prime * result + updatedBy;
		result = prime * result + ((updatedDate == null) ? 0 : updatedDate.hashCode());
		result = prime * result + ((volume == null) ? 0 : volume.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerPreferenceDTO other = (CustomerPreferenceDTO) obj;
		if (administrationRoute == null) {
			if (other.administrationRoute != null)
				return false;
		} else if (!administrationRoute.equals(other.administrationRoute))
			return false;
		if (batDSUKey == null) {
			if (other.batDSUKey != null)
				return false;
		} else if (!batDSUKey.equals(other.batDSUKey))
			return false;
		if (batDrugKey == null) {
			if (other.batDrugKey != null)
				return false;
		} else if (!batDrugKey.equals(other.batDrugKey))
			return false;
		if (batFormulation == null) {
			if (other.batFormulation != null)
				return false;
		} else if (!batFormulation.equals(other.batFormulation))
			return false;
		if (comments == null) {
			if (other.comments != null)
				return false;
		} else if (!comments.equals(other.comments))
			return false;
		if (createdBy == null) {
			if (other.createdBy != null)
				return false;
		} else if (!createdBy.equals(other.createdBy))
			return false;
		if (createdDate == null) {
			if (other.createdDate != null)
				return false;
		} else if (!createdDate.equals(other.createdDate))
			return false;
		if (customerKey == null) {
			if (other.customerKey != null)
				return false;
		} else if (!customerKey.equals(other.customerKey))
			return false;
		if (customerName == null) {
			if (other.customerName != null)
				return false;
		} else if (!customerName.equals(other.customerName))
			return false;
		if (deliveryMechanism == null) {
			if (other.deliveryMechanism != null)
				return false;
		} else if (!deliveryMechanism.equals(other.deliveryMechanism))
			return false;
		if (doseFrom == null) {
			if (other.doseFrom != null)
				return false;
		} else if (!doseFrom.equals(other.doseFrom))
			return false;
		if (doseTo == null) {
			if (other.doseTo != null)
				return false;
		} else if (!doseTo.equals(other.doseTo))
			return false;
		if (exact != other.exact)
			return false;
		if (infusionDuration == null) {
			if (other.infusionDuration != null)
				return false;
		} else if (!infusionDuration.equals(other.infusionDuration))
			return false;
		if (msoIngStkKey == null) {
			if (other.msoIngStkKey != null)
				return false;
		} else if (!msoIngStkKey.equals(other.msoIngStkKey))
			return false;
		if (prefId != other.prefId)
			return false;
		if (productDescription == null) {
			if (other.productDescription != null)
				return false;
		} else if (!productDescription.equals(other.productDescription))
			return false;
		if (productType == null) {
			if (other.productType != null)
				return false;
		} else if (!productType.equals(other.productType))
			return false;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		if (rank == null) {
			if (other.rank != null)
				return false;
		} else if (!rank.equals(other.rank))
			return false;
		if (triKey == null) {
			if (other.triKey != null)
				return false;
		} else if (!triKey.equals(other.triKey))
			return false;
		if (unitOfMeasure == null) {
			if (other.unitOfMeasure != null)
				return false;
		} else if (!unitOfMeasure.equals(other.unitOfMeasure))
			return false;
		if (updatedBy != other.updatedBy)
			return false;
		if (updatedDate == null) {
			if (other.updatedDate != null)
				return false;
		} else if (!updatedDate.equals(other.updatedDate))
			return false;
		if (volume == null) {
			if (other.volume != null)
				return false;
		} else if (!volume.equals(other.volume))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CustomerPreferenceDTO [prefId=" + prefId + ", customerKey=" + customerKey + ", customerName="
				+ customerName + ", productDescription=" + productDescription + ", productType=" + productType
				+ ", batDrugKey=" + batDrugKey + ", batDSUKey=" + batDSUKey + ", triKey=" + triKey + ", msoIngStkKey="
				+ msoIngStkKey + ", batFormulation=" + batFormulation + ", doseFrom=" + doseFrom + ", doseTo=" + doseTo
				+ ", unitOfMeasure=" + unitOfMeasure + ", deliveryMechanism=" + deliveryMechanism + ", volume=" + volume
				+ ", exact=" + exact + ", quantity=" + quantity + ", administrationRoute=" + administrationRoute
				+ ", infusionDuration=" + infusionDuration + ", rank=" + rank + ", comments=" + comments
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", updatedBy=" + updatedBy
				+ ", updatedDate=" + updatedDate + "]";
	}

		
}
