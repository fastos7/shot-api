package com.telstra.health.shot.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * TODO: Insert class explaination here.
 *
 * @author Marlon Cenita
 *
 */
@Entity
@Table(name="SHOT_Customer_Preferences")
public class CustomerPreference implements Serializable {

	private static final long serialVersionUID = -3432511247658780616L;
	
	@Id
	@Column(name = "PrefId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int prefId;
	
	@Column(name = "Cus_Key")
	private String customerKey;
	
	@Column(name = "Cus_Name")
	private String customerName;
	
	@Column(name = "Product_Description")
	private String productDescription;
	
	@Column(name = "Product_Type")
	private String productType;
	
	@Column(name = "BAT_DrugKey")
	private String batDrugKey;
	
	@Column(name = "BAT_DSUKey")
	private String batDSUKey;
	
	@Column(name = "TRI_Key")
	private String triKey;
	
	@Column(name = "MSO_ING_STKKey")
	private String msoIngStkKey;
	
	@Column(name = "bat_formulation")
	private String batFormulation;
	
	@Column(name = "DoseFrom")
	private Double doseFrom;
	
	@Column(name = "DoseTo")
	private Double doseTo;
	
	@Column(name = "UnitOfMeasure")
	private String unitOfMeasure;
	
	@ManyToOne	
	@JoinColumn(name="DEL_Key")	
	private DeliveryMechanisms deliveryMechanism;
	
	@Column(name = "Volume")
	private Double volume;
	
	@Column(name = "Exact")
	private char exact;
	
	@Column(name = "Quantity")
	private Integer quantity;
	
	@ManyToOne
	@JoinColumn(name = "AdminRouteCode",referencedColumnName="COD_Code")
	private AdministrationRoute administrationRoute;
	
	@Column(name = "InfusionDuration")
	private Double infusionDuration;
	
	@Column(name = "Rank")
	private Integer rank;
	
	@Column(name = "Comments")
	private String comments;
	
	@Column(name = "CreatedBy")
	private Integer createdBy;
	
	@Column(name = "CreatedDate")
	private Timestamp createdDate;
	
	@Column(name = "UpdatedBy")
	private Integer updatedBy;	
	
	@Column(name = "UpdatedDate")
	private Timestamp updatedDate;
	
	
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


	public DeliveryMechanisms getDeliveryMechanism() {
		return deliveryMechanism;
	}


	public void setDeliveryMechanism(DeliveryMechanisms deliveryMechanism) {
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


	public AdministrationRoute getAdministrationRoute() {
		return administrationRoute;
	}


	public void setAdministrationRoute(AdministrationRoute administrationRoute) {
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


	public interface ProductType {
		String STANDARD 		= "Standard";
		String CONSIGNMENT 		= "Consignment";
		String CLINICAL_TRIAL 	= "ClinicalTrial";
		String FORMULATION 		= "Formulation";
		String CNF_FORMULATION 	= "CNF Formulation";
		String MULTI_DRUG		= "MultiDrug";
	}
}
