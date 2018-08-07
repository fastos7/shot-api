package com.telstra.health.shot.dto;

import java.io.Serializable;
import java.util.List;

/**
 * This is the Data Transfer Object for Product Entity.<p/>
 * The methods <code>hashCode, equals</code> and <code>toString</code>
 * were generated using eclipse.
 *  
 * @author Marlon Cenita
 *
 */
public class ProductDTO implements Serializable {

	private static final long serialVersionUID = 151909275078818016L;
	
	private String productDescription;
	private String entryType;
	private String processType;
	private String targetSite;
	private String schedule;
	private String batDrugKey;
	private String batDSUKey;
	private String triKey;
	private String msoIngStkKey;
	private String batFormulation;
	private String unitOfMeasure;
	private String genericDrugDescription;
	
	private String batDrugKey2;
	private String batDSUKey2;
	private String unitOfMeasure2;
	private String genericDrugDescription2;
	
	private String batDrugKey3;
	private String batDSUKey3;
	private String unitOfMeasure3;
	private String genericDrugDescription3;
	
	private List<DeliveryMechanismsDTO> deliveryMechanisms;
	
	public ProductDTO(String productDescription, String entryType, String processType, String targetSite,
			String schedule, String batDrugKey, String batDSUKey, String triKey, String msoIngStkKey,
			String batFormulation, String unitOfMeasure, String genericDrugDescription, String batDrugKey2,
			String batDSUKey2, String unitOfMeasure2, String genericDrugDescription2, String batDrugKey3,
			String batDSUKey3, String unitOfMeasure3, String genericDrugDescription3) {
		super();
		this.productDescription = productDescription;
		this.entryType = entryType;
		this.processType = processType;
		this.targetSite = targetSite;
		this.schedule = schedule;
		this.batDrugKey = batDrugKey;
		this.batDSUKey = batDSUKey;
		this.triKey = triKey;
		this.msoIngStkKey = msoIngStkKey;
		this.batFormulation = batFormulation;
		this.unitOfMeasure = unitOfMeasure;
		this.genericDrugDescription = genericDrugDescription;
		this.batDrugKey2 = batDrugKey2;
		this.batDSUKey2 = batDSUKey2;
		this.unitOfMeasure2 = unitOfMeasure2;
		this.genericDrugDescription2 = genericDrugDescription2;
		this.batDrugKey3 = batDrugKey3;
		this.batDSUKey3 = batDSUKey3;
		this.unitOfMeasure3 = unitOfMeasure3;
		this.genericDrugDescription3 = genericDrugDescription3;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public String getEntryType() {
		return entryType;
	}

	public void setEntryType(String entryType) {
		this.entryType = entryType;
	}

	public String getProcessType() {
		return processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
	}

	public String getTargetSite() {
		return targetSite;
	}

	public void setTargetSite(String targetSite) {
		this.targetSite = targetSite;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
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

	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	public String getGenericDrugDescription() {
		return genericDrugDescription;
	}

	public void setGenericDrugDescription(String genericDrugDescription) {
		this.genericDrugDescription = genericDrugDescription;
	}

	public String getBatDrugKey2() {
		return batDrugKey2;
	}

	public void setBatDrugKey2(String batDrugKey2) {
		this.batDrugKey2 = batDrugKey2;
	}

	public String getBatDSUKey2() {
		return batDSUKey2;
	}

	public void setBatDSUKey2(String batDSUKey2) {
		this.batDSUKey2 = batDSUKey2;
	}

	public String getUnitOfMeasure2() {
		return unitOfMeasure2;
	}

	public void setUnitOfMeasure2(String unitOfMeasure2) {
		this.unitOfMeasure2 = unitOfMeasure2;
	}

	public String getGenericDrugDescription2() {
		return genericDrugDescription2;
	}

	public void setGenericDrugDescription2(String genericDrugDescription2) {
		this.genericDrugDescription2 = genericDrugDescription2;
	}

	public String getBatDrugKey3() {
		return batDrugKey3;
	}

	public void setBatDrugKey3(String batDrugKey3) {
		this.batDrugKey3 = batDrugKey3;
	}

	public String getBatDSUKey3() {
		return batDSUKey3;
	}

	public void setBatDSUKey3(String batDSUKey3) {
		this.batDSUKey3 = batDSUKey3;
	}

	public String getUnitOfMeasure3() {
		return unitOfMeasure3;
	}

	public void setUnitOfMeasure3(String unitOfMeasure3) {
		this.unitOfMeasure3 = unitOfMeasure3;
	}

	public String getGenericDrugDescription3() {
		return genericDrugDescription3;
	}

	public void setGenericDrugDescription3(String genericDrugDescription3) {
		this.genericDrugDescription3 = genericDrugDescription3;
	}

	public List<DeliveryMechanismsDTO> getDeliveryMechanisms() {
		return deliveryMechanisms;
	}

	public void setDeliveryMechanisms(List<DeliveryMechanismsDTO> deliveryMechanisms) {
		this.deliveryMechanisms = deliveryMechanisms;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((batDSUKey == null) ? 0 : batDSUKey.hashCode());
		result = prime * result + ((batDSUKey2 == null) ? 0 : batDSUKey2.hashCode());
		result = prime * result + ((batDSUKey3 == null) ? 0 : batDSUKey3.hashCode());
		result = prime * result + ((batDrugKey == null) ? 0 : batDrugKey.hashCode());
		result = prime * result + ((batDrugKey2 == null) ? 0 : batDrugKey2.hashCode());
		result = prime * result + ((batDrugKey3 == null) ? 0 : batDrugKey3.hashCode());
		result = prime * result + ((batFormulation == null) ? 0 : batFormulation.hashCode());
		result = prime * result + ((entryType == null) ? 0 : entryType.hashCode());
		result = prime * result + ((genericDrugDescription == null) ? 0 : genericDrugDescription.hashCode());
		result = prime * result + ((genericDrugDescription2 == null) ? 0 : genericDrugDescription2.hashCode());
		result = prime * result + ((genericDrugDescription3 == null) ? 0 : genericDrugDescription3.hashCode());
		result = prime * result + ((msoIngStkKey == null) ? 0 : msoIngStkKey.hashCode());
		result = prime * result + ((processType == null) ? 0 : processType.hashCode());
		result = prime * result + ((productDescription == null) ? 0 : productDescription.hashCode());
		result = prime * result + ((schedule == null) ? 0 : schedule.hashCode());
		result = prime * result + ((targetSite == null) ? 0 : targetSite.hashCode());
		result = prime * result + ((triKey == null) ? 0 : triKey.hashCode());
		result = prime * result + ((unitOfMeasure == null) ? 0 : unitOfMeasure.hashCode());
		result = prime * result + ((unitOfMeasure2 == null) ? 0 : unitOfMeasure2.hashCode());
		result = prime * result + ((unitOfMeasure3 == null) ? 0 : unitOfMeasure3.hashCode());
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
		ProductDTO other = (ProductDTO) obj;
		if (batDSUKey == null) {
			if (other.batDSUKey != null)
				return false;
		} else if (!batDSUKey.equals(other.batDSUKey))
			return false;
		if (batDSUKey2 == null) {
			if (other.batDSUKey2 != null)
				return false;
		} else if (!batDSUKey2.equals(other.batDSUKey2))
			return false;
		if (batDSUKey3 == null) {
			if (other.batDSUKey3 != null)
				return false;
		} else if (!batDSUKey3.equals(other.batDSUKey3))
			return false;
		if (batDrugKey == null) {
			if (other.batDrugKey != null)
				return false;
		} else if (!batDrugKey.equals(other.batDrugKey))
			return false;
		if (batDrugKey2 == null) {
			if (other.batDrugKey2 != null)
				return false;
		} else if (!batDrugKey2.equals(other.batDrugKey2))
			return false;
		if (batDrugKey3 == null) {
			if (other.batDrugKey3 != null)
				return false;
		} else if (!batDrugKey3.equals(other.batDrugKey3))
			return false;
		if (batFormulation == null) {
			if (other.batFormulation != null)
				return false;
		} else if (!batFormulation.equals(other.batFormulation))
			return false;
		if (entryType == null) {
			if (other.entryType != null)
				return false;
		} else if (!entryType.equals(other.entryType))
			return false;
		if (genericDrugDescription == null) {
			if (other.genericDrugDescription != null)
				return false;
		} else if (!genericDrugDescription.equals(other.genericDrugDescription))
			return false;
		if (genericDrugDescription2 == null) {
			if (other.genericDrugDescription2 != null)
				return false;
		} else if (!genericDrugDescription2.equals(other.genericDrugDescription2))
			return false;
		if (genericDrugDescription3 == null) {
			if (other.genericDrugDescription3 != null)
				return false;
		} else if (!genericDrugDescription3.equals(other.genericDrugDescription3))
			return false;
		if (msoIngStkKey == null) {
			if (other.msoIngStkKey != null)
				return false;
		} else if (!msoIngStkKey.equals(other.msoIngStkKey))
			return false;
		if (processType == null) {
			if (other.processType != null)
				return false;
		} else if (!processType.equals(other.processType))
			return false;
		if (productDescription == null) {
			if (other.productDescription != null)
				return false;
		} else if (!productDescription.equals(other.productDescription))
			return false;
		if (schedule == null) {
			if (other.schedule != null)
				return false;
		} else if (!schedule.equals(other.schedule))
			return false;
		if (targetSite == null) {
			if (other.targetSite != null)
				return false;
		} else if (!targetSite.equals(other.targetSite))
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
		if (unitOfMeasure2 == null) {
			if (other.unitOfMeasure2 != null)
				return false;
		} else if (!unitOfMeasure2.equals(other.unitOfMeasure2))
			return false;
		if (unitOfMeasure3 == null) {
			if (other.unitOfMeasure3 != null)
				return false;
		} else if (!unitOfMeasure3.equals(other.unitOfMeasure3))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProductDTO [productDescription=" + productDescription + ", entryType=" + entryType + ", processType="
				+ processType + ", targetSite=" + targetSite + ", schedule=" + schedule + ", batDrugKey=" + batDrugKey
				+ ", batDSUKey=" + batDSUKey + ", triKey=" + triKey + ", msoIngStkKey=" + msoIngStkKey
				+ ", batFormulation=" + batFormulation + ", unitOfMeasure=" + unitOfMeasure
				+ ", genericDrugDescription=" + genericDrugDescription + ", batDrugKey2=" + batDrugKey2
				+ ", batDSUKey2=" + batDSUKey2 + ", unitOfMeasure2=" + unitOfMeasure2 + ", genericDrugDescription2="
				+ genericDrugDescription2 + ", batDrugKey3=" + batDrugKey3 + ", batDSUKey3=" + batDSUKey3
				+ ", unitOfMeasure3=" + unitOfMeasure3 + ", genericDrugDescription3=" + genericDrugDescription3 + "]";
	}
	
		
}
