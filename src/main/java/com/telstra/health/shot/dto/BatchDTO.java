package com.telstra.health.shot.dto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class BatchDTO {

	// input param for the Batch API
	private String stkKey;
	private String batTrlkey;
	private String batCusKey;
	private String batDelMechKey;
	private String batAdminRouteCode;
	private String stkpbs;
	private Integer calculateFlag;
	private String batDsukey;
	private Double batFinalvolume;
	private Double batSpecifiedVolume;
	private String stkConcentration;
	private String batDescription;
	private String xxxDoseunits;
	private String batAssemblyinstructions;
	private String batLabelinstructions;
	private Integer batQuantity;
	private double batDose;
	private String batExact;
	private String entRiva;
	private String batRiva;
	private Double batScore;
	private String batInfusionRate;
	private String errorCode;
	private String errorMessage;
	private String batProductid;
	private String batTestCode;
	private String batLastUpdAction;
	private String batLastUpdWhen;
	private String batStatus;
	private String batEntKey;
	private List<IngredientsDTO> ingredient = new ArrayList<>();
	private String batDrugKey;
	private String batDrug2Key;
	private String batDrug3Key;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Timestamp patientDob;

	private String batPatFirstName;
	private String batPatLastName;
	private String batPatientUrNo;
	private String patientId;

	private String status;
	private String batTreatDate;

	private String productDescription;
	private boolean batClosedSystem;
	private double infusionDuration;
	private String routeName;
	private String deliveryMechanismDescription;

	private Timestamp batPrepdate;
	// input for the manual stock only
	private String batMsoIngStkKey;

	private String batReleasespecs;
	private String batExpiryperiod;
	// Multi-drug inputs
	private String stkKey2;
	private String batDsukey2;
	private Double batDose2;
	private String xxxDoseunits2;
	private String xxxDoseunits3;

	private String stkKey3;
	private String batDsukey3;
	private Double batDose3;
	private String batDispatchDatetime;
	// arrival date/time
	private String batDeliveryDatetime;
	private String batDeliveryLocation;

	private String shotCancelsave;
	private String shotSideMessage;

	// these properties will be returned for the manual stock only
	private String batProcessType;
	private String batStockOnly;
	private String batPriority;
	private String batComments;

	private String batDsu2key;
	private String batDsu3key;
	private String productDescription2;
	private String productDescription3;

	// this is for logistics field only for the SHOT batch table
	private Boolean isDeliveryRunRestricted;
	private Boolean hasDeliveryRunIncentive;

	public BatchDTO() {
	}

	public String getStkKey() {
		return stkKey;
	}

	public void setStkKey(String stkKey) {
		this.stkKey = stkKey;
	}

	public String getBatTrlkey() {
		return batTrlkey;
	}

	public void setBatTrlkey(String batTrlkey) {
		this.batTrlkey = batTrlkey;
	}

	public String getBatCusKey() {
		return batCusKey;
	}

	public void setBatCusKey(String batCusKey) {
		this.batCusKey = batCusKey;
	}

	public String getBatDelMechKey() {
		return batDelMechKey;
	}

	public void setBatDelMechKey(String batDelMechKey) {
		this.batDelMechKey = batDelMechKey;
	}

	public String getBatAdminRouteCode() {
		return batAdminRouteCode;
	}

	public void setBatAdminRouteCode(String batAdminRouteCode) {
		this.batAdminRouteCode = batAdminRouteCode;
	}

	public String getStkpbs() {
		return stkpbs;
	}

	public void setStkpbs(String stkpbs) {
		this.stkpbs = stkpbs;
	}

	public Integer getCalculateFlag() {
		return calculateFlag;
	}

	public void setCalculateFlag(Integer calculateFlag) {
		this.calculateFlag = calculateFlag;
	}

	public String getBatDsukey() {
		return batDsukey;
	}

	public void setBatDsukey(String batDsukey) {
		this.batDsukey = batDsukey;
	}

	public Double getBatFinalvolume() {
		return batFinalvolume;
	}

	public void setBatFinalvolume(Double batFinalvolume) {
		this.batFinalvolume = batFinalvolume;
	}

	public Double getBatSpecifiedVolume() {
		return batSpecifiedVolume;
	}

	public void setBatSpecifiedVolume(Double batSpecifiedVolume) {
		this.batSpecifiedVolume = batSpecifiedVolume;
	}

	public String getStkConcentration() {
		return stkConcentration;
	}

	public void setStkConcentration(String stkConcentration) {
		this.stkConcentration = stkConcentration;
	}

	public String getBatDescription() {
		return batDescription;
	}

	public void setBatDescription(String batDescription) {
		this.batDescription = batDescription;
	}

	public String getXxxDoseunits() {
		return xxxDoseunits;
	}

	public void setXxxDoseunits(String xxxDoseunits) {
		this.xxxDoseunits = xxxDoseunits;
	}

	public String getBatAssemblyinstructions() {
		return batAssemblyinstructions;
	}

	public void setBatAssemblyinstructions(String batAssemblyinstructions) {
		this.batAssemblyinstructions = batAssemblyinstructions;
	}

	public String getBatLabelinstructions() {
		return batLabelinstructions;
	}

	public void setBatLabelinstructions(String batLabelinstructions) {
		this.batLabelinstructions = batLabelinstructions;
	}

	public Integer getBatQuantity() {
		return batQuantity;
	}

	public void setBatQuantity(Integer batQuantity) {
		this.batQuantity = batQuantity;
	}

	public Double getBatDose() {
		return batDose;
	}

	public void setBatDose(Double batDose) {
		this.batDose = batDose;
	}

	public String getBatExact() {
		return batExact;
	}

	public void setBatExact(String batExact) {
		this.batExact = batExact;
	}

	public String getEntRiva() {
		return entRiva;
	}

	public void setEntRiva(String entRiva) {
		this.entRiva = entRiva;
	}

	public String getBatRiva() {
		return batRiva;
	}

	public void setBatRiva(String batRiva) {
		this.batRiva = batRiva;
	}

	public Double getBatScore() {
		return batScore;
	}

	public void setBatScore(Double batScore) {
		this.batScore = batScore;
	}

	public String getBatInfusionRate() {
		return batInfusionRate;
	}

	public void setBatInfusionRate(String batInfusionRate) {
		this.batInfusionRate = batInfusionRate;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getBatProductid() {
		return batProductid;
	}

	public void setBatProductid(String batProductid) {
		this.batProductid = batProductid;
	}

	public String getBatTestCode() {
		return batTestCode;
	}

	public void setBatTestCode(String batTestCode) {
		this.batTestCode = batTestCode;
	}

	public String getBatLastUpdAction() {
		return batLastUpdAction;
	}

	public void setBatLastUpdAction(String batLastUpdAction) {
		this.batLastUpdAction = batLastUpdAction;
	}

	public String getBatLastUpdWhen() {
		return batLastUpdWhen;
	}

	public void setBatLastUpdWhen(String batLastUpdWhen) {
		this.batLastUpdWhen = batLastUpdWhen;
	}

	public String getBatStatus() {
		return batStatus;
	}

	public void setBatStatus(String batStatus) {
		this.batStatus = batStatus;
	}

	public String getBatEntKey() {
		return batEntKey;
	}

	public void setBatEntKey(String batEntKey) {
		this.batEntKey = batEntKey;
	}

	public List<IngredientsDTO> getIngredient() {
		return ingredient;
	}

	public void setIngredient(List<IngredientsDTO> ingredient) {
		this.ingredient = ingredient;
	}

	public String getBatDrugKey() {
		return batDrugKey;
	}

	public void setBatDrugKey(String batDrugKey) {
		this.batDrugKey = batDrugKey;
	}

	public Timestamp getPatientDob() {
		return patientDob;
	}

	public void setPatientDob(Timestamp patientDob) {
		this.patientDob = patientDob;
	}

	public String getBatPatFirstName() {
		return batPatFirstName;
	}

	public void setBatPatFirstName(String batPatFirstName) {
		this.batPatFirstName = batPatFirstName;
	}

	public String getBatPatLastName() {
		return batPatLastName;
	}

	public void setBatPatLastName(String batPatLastName) {
		this.batPatLastName = batPatLastName;
	}

	public String getBatPatientUrNo() {
		return batPatientUrNo;
	}

	public void setBatPatientUrNo(String batPatientUrNo) {
		this.batPatientUrNo = batPatientUrNo;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBatTreatDate() {
		return batTreatDate;
	}

	public void setBatTreatDate(String batTreatDate) {
		this.batTreatDate = batTreatDate;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public boolean isBatClosedSystem() {
		return batClosedSystem;
	}

	public void setBatClosedSystem(boolean batClosedSystem) {
		this.batClosedSystem = batClosedSystem;
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

	public String getBatMsoIngStkKey() {
		return batMsoIngStkKey;
	}

	public void setBatMsoIngStkKey(String batMsoIngStkKey) {
		this.batMsoIngStkKey = batMsoIngStkKey;
	}

	public String getBatReleasespecs() {
		return batReleasespecs;
	}

	public void setBatReleasespecs(String batReleasespecs) {
		this.batReleasespecs = batReleasespecs;
	}

	public String getBatExpiryperiod() {
		return batExpiryperiod;
	}

	public void setBatExpiryperiod(String batExpiryperiod) {
		this.batExpiryperiod = batExpiryperiod;
	}

	public String getStkKey2() {
		return stkKey2;
	}

	public void setStkKey2(String stkKey2) {
		this.stkKey2 = stkKey2;
	}

	public String getBatDsukey2() {
		return batDsukey2;
	}

	public void setBatDsukey2(String batDsukey2) {
		this.batDsukey2 = batDsukey2;
	}

	public String getStkKey3() {
		return stkKey3;
	}

	public void setStkKey3(String stkKey3) {
		this.stkKey3 = stkKey3;
	}

	public String getBatDsukey3() {
		return batDsukey3;
	}

	public void setBatDsukey3(String batDsukey3) {
		this.batDsukey3 = batDsukey3;
	}

	public double getInfusionDuration() {
		return infusionDuration;
	}

	public void setInfusionDuration(double infusionDuration) {
		this.infusionDuration = infusionDuration;
	}

	public Double getBatDose2() {
		return batDose2;
	}

	public void setBatDose2(Double batDose2) {
		this.batDose2 = batDose2;
	}

	public Double getBatDose3() {
		return batDose3;
	}

	public void setBatDose3(Double batDose3) {
		this.batDose3 = batDose3;
	}

	public void setBatDose(double batDose) {
		this.batDose = batDose;
	}

	public String getBatDispatchDatetime() {
		return batDispatchDatetime;
	}

	public void setBatDispatchDatetime(String batDispatchDatetime) {
		this.batDispatchDatetime = batDispatchDatetime;
	}

	public String getBatDeliveryDatetime() {
		return batDeliveryDatetime;
	}

	public void setBatDeliveryDatetime(String batDeliveryDatetime) {
		this.batDeliveryDatetime = batDeliveryDatetime;
	}

	public String getBatDeliveryLocation() {
		return batDeliveryLocation;
	}

	public void setBatDeliveryLocation(String batDeliveryLocation) {
		this.batDeliveryLocation = batDeliveryLocation;
	}

	public Timestamp getBatPrepdate() {
		return batPrepdate;
	}

	public void setBatPrepdate(Timestamp batPrepdate) {
		this.batPrepdate = batPrepdate;
	}

	public String getShotCancelsave() {
		return shotCancelsave;
	}

	public void setShotCancelsave(String shotCancelsave) {
		this.shotCancelsave = shotCancelsave;
	}

	public String getBatProcessType() {
		return batProcessType;
	}

	public void setBatProcessType(String batProcessType) {
		this.batProcessType = batProcessType;
	}

	public String getBatStockOnly() {
		return batStockOnly;
	}

	public void setBatStockOnly(String batStockOnly) {
		this.batStockOnly = batStockOnly;
	}

	public String getBatPriority() {
		return batPriority;
	}

	public void setBatPriority(String batPriority) {
		this.batPriority = batPriority;
	}

	public String getShotSideMessage() {
		return shotSideMessage;
	}

	public void setShotSideMessage(String shotSideMessage) {
		this.shotSideMessage = shotSideMessage;
	}

	public Boolean getIsDeliveryRunRestricted() {
		return isDeliveryRunRestricted;
	}

	public void setIsDeliveryRunRestricted(Boolean isDeliveryRunRestricted) {
		this.isDeliveryRunRestricted = isDeliveryRunRestricted;
	}

	public String getXxxDoseunits2() {
		return xxxDoseunits2;
	}

	public void setXxxDoseunits2(String xxxDoseunits2) {
		this.xxxDoseunits2 = xxxDoseunits2;
	}

	public String getXxxDoseunits3() {
		return xxxDoseunits3;
	}

	public void setXxxDoseunits3(String xxxDoseunits3) {
		this.xxxDoseunits3 = xxxDoseunits3;
	}

	public String getBatComments() {
		return batComments;
	}

	public void setBatComments(String batComments) {
		this.batComments = batComments;
	}

	public String getBatDrug2Key() {
		return batDrug2Key;
	}

	public void setBatDrug2Key(String batDrug2Key) {
		this.batDrug2Key = batDrug2Key;
	}

	public String getBatDrug3Key() {
		return batDrug3Key;
	}

	public void setBatDrug3Key(String batDrug3Key) {
		this.batDrug3Key = batDrug3Key;
	}

	public String getBatDsu2key() {
		return batDsu2key;
	}

	public void setBatDsu2key(String batDsu2key) {
		this.batDsu2key = batDsu2key;
	}

	public String getBatDsu3key() {
		return batDsu3key;
	}

	public void setBatDsu3key(String batDsu3key) {
		this.batDsu3key = batDsu3key;
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

	@Override
	public String toString() {
		return "BatchDTO [stkKey=" + stkKey + ", batTrlkey=" + batTrlkey + ", batCusKey=" + batCusKey
				+ ", batDelMechKey=" + batDelMechKey + ", batAdminRouteCode=" + batAdminRouteCode + ", stkpbs=" + stkpbs
				+ ", calculateFlag=" + calculateFlag + ", batDsukey=" + batDsukey + ", batFinalvolume=" + batFinalvolume
				+ ", batSpecifiedVolume=" + batSpecifiedVolume + ", stkConcentration=" + stkConcentration
				+ ", batDescription=" + batDescription + ", xxxDoseunits=" + xxxDoseunits + ", batAssemblyinstructions="
				+ batAssemblyinstructions + ", batLabelinstructions=" + batLabelinstructions + ", batQuantity="
				+ batQuantity + ", batDose=" + batDose + ", batExact=" + batExact + ", entRiva=" + entRiva
				+ ", batRiva=" + batRiva + ", batScore=" + batScore + ", batInfusionRate=" + batInfusionRate
				+ ", errorCode=" + errorCode + ", errorMessage=" + errorMessage + ", batProductid=" + batProductid
				+ ", batTestCode=" + batTestCode + ", batLastUpdAction=" + batLastUpdAction + ", batLastUpdWhen="
				+ batLastUpdWhen + ", batStatus=" + batStatus + ", batEntKey=" + batEntKey + ", ingredient="
				+ ingredient + ", batDrugKey=" + batDrugKey + ", batDrug2Key=" + batDrug2Key + ", batDrug3Key="
				+ batDrug3Key + ", patientDob=" + patientDob + ", batPatFirstName=" + batPatFirstName
				+ ", batPatLastName=" + batPatLastName + ", batPatientUrNo=" + batPatientUrNo + ", patientId="
				+ patientId + ", status=" + status + ", batTreatDate=" + batTreatDate + ", productDescription="
				+ productDescription + ", batClosedSystem=" + batClosedSystem + ", infusionDuration=" + infusionDuration
				+ ", routeName=" + routeName + ", deliveryMechanismDescription=" + deliveryMechanismDescription
				+ ", batPrepdate=" + batPrepdate + ", batMsoIngStkKey=" + batMsoIngStkKey + ", batReleasespecs="
				+ batReleasespecs + ", batExpiryperiod=" + batExpiryperiod + ", stkKey2=" + stkKey2 + ", batDsukey2="
				+ batDsukey2 + ", batDose2=" + batDose2 + ", xxxDoseunits2=" + xxxDoseunits2 + ", xxxDoseunits3="
				+ xxxDoseunits3 + ", stkKey3=" + stkKey3 + ", batDsukey3=" + batDsukey3 + ", batDose3=" + batDose3
				+ ", batDispatchDatetime=" + batDispatchDatetime + ", batDeliveryDatetime=" + batDeliveryDatetime
				+ ", batDeliveryLocation=" + batDeliveryLocation + ", shotCancelsave=" + shotCancelsave
				+ ", shotSideMessage=" + shotSideMessage + ", batProcessType=" + batProcessType + ", batStockOnly="
				+ batStockOnly + ", batPriority=" + batPriority + ", batComments=" + batComments + ", batDsu2key="
				+ batDsu2key + ", batDsu3key=" + batDsu3key + ", productDescription2=" + productDescription2
				+ ", productDescription3=" + productDescription3 + ", isDeliveryRunRestricted="
				+ isDeliveryRunRestricted + "]";
	}

	public Boolean getHasDeliveryRunIncentive() {
		return hasDeliveryRunIncentive;
	}

	public void setHasDeliveryRunIncentive(Boolean hasDeliveryRunIncentive) {
		this.hasDeliveryRunIncentive = hasDeliveryRunIncentive;
	}

}
