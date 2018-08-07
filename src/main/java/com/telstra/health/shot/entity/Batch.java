package com.telstra.health.shot.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Type;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * The persistent class for the BATA_Batch database table.
 * 
 */
@Entity
@Table(name = "BAT_Batch")
public class Batch implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "ShotBatchKey")
	private ShotBatch shotBatch;

	@Id
	@Column(name = "bat_key")
	private String batkey;

	@ManyToOne
	@JoinColumn(name = "bat_ordkey")
	private Order order;

	@Column(name = "bat_invkey")
	private String batInvKey;

	@Column(name = "bat_patkey")
	private String batPatKey;

	@Column(name = "bat_shpkey")
	private String batShpKey;

	@Column(name = "bat_adminroutecode")
	private String batAdminRouteCode;

	@Column(name = "bat_assemblyinstructions")
	private String batAssemblyInstructions;

	@Column(name = "bat_assistedby")
	private int batAssistedBy;

	@Column(name = "bat_billto")
	private String BatbillTo;

	@Column(name = "bat_billtocus")
	private String batBillToCus;

	@Column(name = "bat_cabinetno")
	private int batCabinetNo;

	@Column(name = "bat_compounded")
	private String batCompounded;

	@Column(name = "bat_confirmedby")
	private int batConfirmedBy;

	@Column(name = "bat_containervarfactor")
	private BigDecimal batContainerVarFactor;

	@Column(name = "bat_dateassisted")
	private Timestamp batDateAssisted;

	@Column(name = "bat_datecompounded")
	private Timestamp batDateCompounded;

	@Column(name = "bat_dateentered")
	private Timestamp batDateEntered;

	@Column(name = "bat_datepacked")
	private Timestamp batDatePacked;

	@Column(name = "bat_datepicked")
	private Timestamp batDatePicked;

	@Column(name = "bat_deliverylocation")
	private String batDeliveryLocation;

	@Column(name = "bat_delmechkey")
	private String batDelMechKey;

	@Column(name = "bat_description")
	private String batDescription;

	@Column(name = "bat_diluentvarfactor")
	private BigDecimal batDiluentVarFactor;

	@Column(name = "Bat_Dispatched")
	private String batDispatched;

	@Column(name = "bat_dose")
	private BigDecimal batDose;

	@Column(name = "bat_doseunits")
	private String xxxDoseunits;

	@Column(name = "bat_drtkey")
	private String batDrtKey;

	@Column(name = "bat_drugkey")
	private String batDrugKey;

	@Column(name = "bat_dsukey")
	private String batDsukey;

	@Column(name = "bat_enteredby")
	private int batEnteredBy;

	@Column(name = "bat_entkey")
	private String batEntKey;

	@Column(name = "bat_exact")
	private String batExact;

	@Column(name = "bat_expirychanged")
	private String batExpiryChanged;

	@Column(name = "bat_expirydate")
	private Timestamp batExpiryDate;

	@Column(name = "bat_expiryperiod")
	private BigDecimal batExpiryPeriod;

	@Column(name = "bat_extendedprice")
	private BigDecimal batExtendedPrice;

	@Column(name = "bat_finalvolume")
	private BigDecimal batFinalVolume;

	@Column(name = "bat_finalweight")
	private BigDecimal batFinalWeight;

	@Column(name = "bat_formulation")
	private String batFormulation;

	@Column(name = "bat_ftp")
	private String batFtp;

	@Column(name = "bat_infusionrate")
	private String batInfusionRate;

	@Column(name = "bat_invquantity")
	private int batInvQuantity;

	@Column(name = "bat_labelinstructions")
	private String batLabelInstructions;

	@Column(name = "bat_lastupdaction")
	private String batLastUpdAction;

	@Column(name = "bat_lastupdby")
	private int batLastUpdBy;

	@Column(name = "Bat_Operator")
	private int batOperator;

	@Column(name = "bat_otherfee")
	private BigDecimal batOtherFee;

	@Column(name = "bat_packedby")
	private int batPackedBy;

	@Column(name = "bat_patclass")
	private String batPatClass;

	@Column(name = "bat_patfirstname")
	private String batPatFirstName;

	@Column(name = "bat_patientname")
	private String batPatientName;

	@Column(name = "bat_patienturno")
	private String batPatientUrNo;

	@Column(name = "bat_patlastname")
	private String batPatLastName;

	@Column(name = "bat_pickedby")
	private int batPickedbBy;

	@Column(name = "bat_prepdate")
	private Timestamp batPrepDate;

	@Column(name = "bat_printedby")
	private int batPrintedBy;

	@Column(name = "bat_priority")
	private String batPriority;

	@Column(name = "bat_productid")
	private String batProductId;

	@Column(name = "bat_pump")
	private String batPump;

	@Column(name = "bat_pumpretentionvol")
	private BigDecimal batPumpRetentionVol;

	@Column(name = "BAT_QED")
	private String batQed;

	@Column(name = "bat_quantity")
	private short batQuantity;

	@Column(name = "bat_rejectnotes")
	private String batRejectNotes;

	@Column(name = "bat_rejectreason")
	private String batRejectReason;

	@Column(name = "bat_releasespecs")
	private String batReleaseSpecs;

	@Column(name = "bat_riva")
	private String batRiva;

	@Column(name = "bat_score")
	private BigDecimal batScore;

	@Column(name = "bat_senttompx")
	private String batSentTompx;

	@Column(name = "bat_session")
	private int batSession;

	@Column(name = "bat_shipdate")
	private Timestamp batShipDate;

	@Column(name = "bat_specificgravity")
	private BigDecimal batSpecificGravity;

	@Column(name = "bat_specifiedvolume")
	private BigDecimal batSpecifiedVolume;

	@Column(name = "bat_status")
	private String batStatus;

	@Column(name = "bat_stockonly")
	private String batStockOnly;

	@Column(name = "bat_testcode")
	private String batTestCode;

	@Column(name = "bat_theoreticalwastage")
	private BigDecimal batTheoreticalWastage;

	@Column(name = "bat_treatdate")
	private Timestamp batTreatDate;

	@Column(name = "bat_trlkey")
	private String batTrlKey;

	@Column(name = "BAT_UnitPrice")
	private BigDecimal BAT_UnitPrice;

	@Column(name = "BAT_LastUpdWhen")
	private Timestamp batLastUpdWhen;

	@Column(name = "bat_doseunit2")
	private String xxxDoseunits2;

	@Column(name = "bat_doseunit3")
	private String xxxDoseunits3;

	@OneToMany(mappedBy = "batch", cascade = CascadeType.ALL)
	private List<AxisIngredient> ingredient;

	@Column(name = "bat_onhold")
	private String batOnhold;

	@Transient
	private Timestamp patientDob;

	@Transient
	private String patientId;

	@Transient
	private String status;

	@Transient
	private String productDescription;

	@Transient
	private Boolean closedSystem;

	@Transient
	private String infusionDuration;

	@Transient
	private String routeName;

	@Transient
	private String deliveryMechanismDescription;

	@Transient
	private String productDescription2;

	@Transient
	private String productDescription3;

	@Transient
	private Boolean isDeliveryRunRestricted;

	@Column(name = "bat_Drug2Key")
	private String batdrug2Key;

	@Column(name = "bat_DSU2Key")
	private String batDsu2key;

	@Column(name = "bat_Dose2")
	private BigDecimal batDose2;

	@Column(name = "bat_Drug3Key")
	private String batdrug3Key;

	@Column(name = "bat_DSU3Key")
	private String batDsu3key;

	@Column(name = "bat_Dose3")
	private BigDecimal batDose3;

	@Column(name = "bat_csoverride")
	@Type(type = "yes_no")
	private Boolean batCsoverride;

	@Column(name = "bat_PrepDtOverride")
	private String batPrepdtoverride;

	@Column(name = "bat_comments")
	private String batComments;

	@Column(name = "bat_ReplacementBatkey")
	private String batReplacementbatkey;

	@Column(name = "bat_incentive")
	@Type(type = "yes_no")
	private Boolean hasDeliveryRunIncentive;

	public ShotBatch getShotBatch() {
		return shotBatch;
	}

	public void setShotBatch(ShotBatch shotBatch) {
		this.shotBatch = shotBatch;
	}

	public String getBatkey() {
		return batkey;
	}

	public void setBatkey(String batkey) {
		this.batkey = batkey;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public String getBatInvKey() {
		return batInvKey;
	}

	public void setBatInvKey(String batInvKey) {
		this.batInvKey = batInvKey;
	}

	public String getBatPatKey() {
		return batPatKey;
	}

	public void setBatPatKey(String batPatKey) {
		this.batPatKey = batPatKey;
	}

	public String getBatShpKey() {
		return batShpKey;
	}

	public void setBatShpKey(String batShpKey) {
		this.batShpKey = batShpKey;
	}

	public String getBatAdminRouteCode() {
		return batAdminRouteCode;
	}

	public void setBatAdminRouteCode(String batAdminRouteCode) {
		this.batAdminRouteCode = batAdminRouteCode;
	}

	public String getBatAssemblyInstructions() {
		return batAssemblyInstructions;
	}

	public void setBatAssemblyInstructions(String batAssemblyInstructions) {
		this.batAssemblyInstructions = batAssemblyInstructions;
	}

	public int getBatAssistedBy() {
		return batAssistedBy;
	}

	public void setBatAssistedBy(int batAssistedBy) {
		this.batAssistedBy = batAssistedBy;
	}

	public String getBatbillTo() {
		return BatbillTo;
	}

	public void setBatbillTo(String batbillTo) {
		BatbillTo = batbillTo;
	}

	public String getBatBillToCus() {
		return batBillToCus;
	}

	public void setBatBillToCus(String batBillToCus) {
		this.batBillToCus = batBillToCus;
	}

	public int getBatCabinetNo() {
		return batCabinetNo;
	}

	public void setBatCabinetNo(int batCabinetNo) {
		this.batCabinetNo = batCabinetNo;
	}

	public String getBatCompounded() {
		return batCompounded;
	}

	public void setBatCompounded(String batCompounded) {
		this.batCompounded = batCompounded;
	}

	public int getBatConfirmedBy() {
		return batConfirmedBy;
	}

	public void setBatConfirmedBy(int batConfirmedBy) {
		this.batConfirmedBy = batConfirmedBy;
	}

	public BigDecimal getBatContainerVarFactor() {
		return batContainerVarFactor;
	}

	public void setBatContainerVarFactor(BigDecimal batContainerVarFactor) {
		this.batContainerVarFactor = batContainerVarFactor;
	}

	public Timestamp getBatDateAssisted() {
		return batDateAssisted;
	}

	public void setBatDateAssisted(Timestamp batDateAssisted) {
		this.batDateAssisted = batDateAssisted;
	}

	public Timestamp getBatDateCompounded() {
		return batDateCompounded;
	}

	public void setBatDateCompounded(Timestamp batDateCompounded) {
		this.batDateCompounded = batDateCompounded;
	}

	public Timestamp getBatDateEntered() {
		return batDateEntered;
	}

	public void setBatDateEntered(Timestamp batDateEntered) {
		this.batDateEntered = batDateEntered;
	}

	public Timestamp getBatDatePacked() {
		return batDatePacked;
	}

	public void setBatDatePacked(Timestamp batDatePacked) {
		this.batDatePacked = batDatePacked;
	}

	public Timestamp getBatDatePicked() {
		return batDatePicked;
	}

	public void setBatDatePicked(Timestamp batDatePicked) {
		this.batDatePicked = batDatePicked;
	}

	public String getBatDeliveryLocation() {
		return batDeliveryLocation;
	}

	public void setBatDeliveryLocation(String batDeliveryLocation) {
		this.batDeliveryLocation = batDeliveryLocation;
	}

	public String getBatDelMechKey() {
		return batDelMechKey;
	}

	public void setBatDelMechKey(String batDelMechKey) {
		this.batDelMechKey = batDelMechKey;
	}

	public String getBatDescription() {
		return batDescription;
	}

	public void setBatDescription(String batDescription) {
		this.batDescription = batDescription;
	}

	public BigDecimal getBatDiluentVarFactor() {
		return batDiluentVarFactor;
	}

	public void setBatDiluentVarFactor(BigDecimal batDiluentVarFactor) {
		this.batDiluentVarFactor = batDiluentVarFactor;
	}

	public String getBatDispatched() {
		return batDispatched;
	}

	public void setBatDispatched(String batDispatched) {
		this.batDispatched = batDispatched;
	}

	public BigDecimal getBatDose() {
		return batDose;
	}

	public void setBatDose(BigDecimal batDose) {
		this.batDose = batDose;
	}

	public String getXxxDoseunits() {
		return xxxDoseunits;
	}

	public void setXxxDoseunits(String xxxDoseunits) {
		this.xxxDoseunits = xxxDoseunits;
	}

	public String getBatDrtKey() {
		return batDrtKey;
	}

	public void setBatDrtKey(String batDrtKey) {
		this.batDrtKey = batDrtKey;
	}

	public String getBatDrugKey() {
		return batDrugKey;
	}

	public void setBatDrugKey(String batDrugKey) {
		this.batDrugKey = batDrugKey;
	}

	public String getBatDsukey() {
		return batDsukey;
	}

	public void setBatDsukey(String batDsukey) {
		this.batDsukey = batDsukey;
	}

	public int getBatEnteredBy() {
		return batEnteredBy;
	}

	public void setBatEnteredBy(int batEnteredBy) {
		this.batEnteredBy = batEnteredBy;
	}

	public String getBatEntKey() {
		return batEntKey;
	}

	public void setBatEntKey(String batEntKey) {
		this.batEntKey = batEntKey;
	}

	public String getBatExact() {
		return batExact;
	}

	public void setBatExact(String batExact) {
		this.batExact = batExact;
	}

	public String getBatExpiryChanged() {
		return batExpiryChanged;
	}

	public void setBatExpiryChanged(String batExpiryChanged) {
		this.batExpiryChanged = batExpiryChanged;
	}

	public Timestamp getBatExpiryDate() {
		return batExpiryDate;
	}

	public void setBatExpiryDate(Timestamp batExpiryDate) {
		this.batExpiryDate = batExpiryDate;
	}

	public BigDecimal getBatExpiryPeriod() {
		return batExpiryPeriod;
	}

	public void setBatExpiryPeriod(BigDecimal batExpiryPeriod) {
		this.batExpiryPeriod = batExpiryPeriod;
	}

	public BigDecimal getBatExtendedPrice() {
		return batExtendedPrice;
	}

	public void setBatExtendedPrice(BigDecimal batExtendedPrice) {
		this.batExtendedPrice = batExtendedPrice;
	}

	public BigDecimal getBatFinalVolume() {
		return batFinalVolume;
	}

	public void setBatFinalVolume(BigDecimal batFinalVolume) {
		this.batFinalVolume = batFinalVolume;
	}

	public BigDecimal getBatFinalWeight() {
		return batFinalWeight;
	}

	public void setBatFinalWeight(BigDecimal batFinalWeight) {
		this.batFinalWeight = batFinalWeight;
	}

	public String getBatFormulation() {
		return batFormulation;
	}

	public void setBatFormulation(String batFormulation) {
		this.batFormulation = batFormulation;
	}

	public String getBatFtp() {
		return batFtp;
	}

	public void setBatFtp(String batFtp) {
		this.batFtp = batFtp;
	}

	public String getBatInfusionRate() {
		return batInfusionRate;
	}

	public void setBatInfusionRate(String batInfusionRate) {
		this.batInfusionRate = batInfusionRate;
	}

	public int getBatInvQuantity() {
		return batInvQuantity;
	}

	public void setBatInvQuantity(int batInvQuantity) {
		this.batInvQuantity = batInvQuantity;
	}

	public String getBatLabelInstructions() {
		return batLabelInstructions;
	}

	public void setBatLabelInstructions(String batLabelInstructions) {
		this.batLabelInstructions = batLabelInstructions;
	}

	public String getBatLastUpdAction() {
		return batLastUpdAction;
	}

	public void setBatLastUpdAction(String batLastUpdAction) {
		this.batLastUpdAction = batLastUpdAction;
	}

	public int getBatLastUpdBy() {
		return batLastUpdBy;
	}

	public void setBatLastUpdBy(int batLastUpdBy) {
		this.batLastUpdBy = batLastUpdBy;
	}

	public int getBatOperator() {
		return batOperator;
	}

	public void setBatOperator(int batOperator) {
		this.batOperator = batOperator;
	}

	public BigDecimal getBatOtherFee() {
		return batOtherFee;
	}

	public void setBatOtherFee(BigDecimal batOtherFee) {
		this.batOtherFee = batOtherFee;
	}

	public int getBatPackedBy() {
		return batPackedBy;
	}

	public void setBatPackedBy(int batPackedBy) {
		this.batPackedBy = batPackedBy;
	}

	public String getBatPatClass() {
		return batPatClass;
	}

	public void setBatPatClass(String batPatClass) {
		this.batPatClass = batPatClass;
	}

	public String getBatPatFirstName() {
		return batPatFirstName;
	}

	public void setBatPatFirstName(String batPatFirstName) {
		this.batPatFirstName = batPatFirstName;
	}

	public String getBatPatientName() {
		return batPatientName;
	}

	public void setBatPatientName(String batPatientName) {
		this.batPatientName = batPatientName;
	}

	public String getBatPatientUrNo() {
		return batPatientUrNo;
	}

	public void setBatPatientUrNo(String batPatientUrNo) {
		this.batPatientUrNo = batPatientUrNo;
	}

	public String getBatPatLastName() {
		return batPatLastName;
	}

	public void setBatPatLastName(String batPatLastName) {
		this.batPatLastName = batPatLastName;
	}

	public int getBatPickedbBy() {
		return batPickedbBy;
	}

	public void setBatPickedbBy(int batPickedbBy) {
		this.batPickedbBy = batPickedbBy;
	}

	public Timestamp getBatPrepDate() {
		return batPrepDate;
	}

	public void setBatPrepDate(Timestamp batPrepDate) {
		this.batPrepDate = batPrepDate;
	}

	public int getBatPrintedBy() {
		return batPrintedBy;
	}

	public void setBatPrintedBy(int batPrintedBy) {
		this.batPrintedBy = batPrintedBy;
	}

	public String getBatPriority() {
		return batPriority;
	}

	public void setBatPriority(String batPriority) {
		this.batPriority = batPriority;
	}

	public String getBatProductId() {
		return batProductId;
	}

	public void setBatProductId(String batProductId) {
		this.batProductId = batProductId;
	}

	public String getBatPump() {
		return batPump;
	}

	public void setBatPump(String batPump) {
		this.batPump = batPump;
	}

	public BigDecimal getBatPumpRetentionVol() {
		return batPumpRetentionVol;
	}

	public void setBatPumpRetentionVol(BigDecimal batPumpRetentionVol) {
		this.batPumpRetentionVol = batPumpRetentionVol;
	}

	public String getBatQed() {
		return batQed;
	}

	public void setBatQed(String batQed) {
		this.batQed = batQed;
	}

	public short getBatQuantity() {
		return batQuantity;
	}

	public void setBatQuantity(short batQuantity) {
		this.batQuantity = batQuantity;
	}

	public String getBatRejectNotes() {
		return batRejectNotes;
	}

	public void setBatRejectNotes(String batRejectNotes) {
		this.batRejectNotes = batRejectNotes;
	}

	public String getBatRejectReason() {
		return batRejectReason;
	}

	public void setBatRejectReason(String batRejectReason) {
		this.batRejectReason = batRejectReason;
	}

	public String getBatReleaseSpecs() {
		return batReleaseSpecs;
	}

	public void setBatReleaseSpecs(String batReleaseSpecs) {
		this.batReleaseSpecs = batReleaseSpecs;
	}

	public String getBatRiva() {
		return batRiva;
	}

	public void setBatRiva(String batRiva) {
		this.batRiva = batRiva;
	}

	public BigDecimal getBatScore() {
		return batScore;
	}

	public void setBatScore(BigDecimal batScore) {
		this.batScore = batScore;
	}

	public String getBatSentTompx() {
		return batSentTompx;
	}

	public void setBatSentTompx(String batSentTompx) {
		this.batSentTompx = batSentTompx;
	}

	public int getBatSession() {
		return batSession;
	}

	public void setBatSession(int batSession) {
		this.batSession = batSession;
	}

	public Timestamp getBatShipDate() {
		return batShipDate;
	}

	public void setBatShipDate(Timestamp batShipDate) {
		this.batShipDate = batShipDate;
	}

	public BigDecimal getBatSpecificGravity() {
		return batSpecificGravity;
	}

	public void setBatSpecificGravity(BigDecimal batSpecificGravity) {
		this.batSpecificGravity = batSpecificGravity;
	}

	public BigDecimal getBatSpecifiedVolume() {
		return batSpecifiedVolume;
	}

	public void setBatSpecifiedVolume(BigDecimal batSpecifiedVolume) {
		this.batSpecifiedVolume = batSpecifiedVolume;
	}

	public String getBatStatus() {
		return batStatus;
	}

	public void setBatStatus(String batStatus) {
		this.batStatus = batStatus;
	}

	public String getBatStockOnly() {
		return batStockOnly;
	}

	public void setBatStockOnly(String batStockOnly) {
		this.batStockOnly = batStockOnly;
	}

	public String getBatTestCode() {
		return batTestCode;
	}

	public void setBatTestCode(String batTestCode) {
		this.batTestCode = batTestCode;
	}

	public BigDecimal getBatTheoreticalWastage() {
		return batTheoreticalWastage;
	}

	public void setBatTheoreticalWastage(BigDecimal batTheoreticalWastage) {
		this.batTheoreticalWastage = batTheoreticalWastage;
	}

	public Timestamp getBatTreatDate() {
		return batTreatDate;
	}

	public void setBatTreatDate(Timestamp batTreatDate) {
		this.batTreatDate = batTreatDate;
	}

	public String getBatTrlKey() {
		return batTrlKey;
	}

	public void setBatTrlKey(String batTrlKey) {
		this.batTrlKey = batTrlKey;
	}

	public BigDecimal getBAT_UnitPrice() {
		return BAT_UnitPrice;
	}

	public void setBAT_UnitPrice(BigDecimal bAT_UnitPrice) {
		BAT_UnitPrice = bAT_UnitPrice;
	}

	public Timestamp getBatLastUpdWhen() {
		return batLastUpdWhen;
	}

	public void setBatLastUpdWhen(Timestamp batLastUpdWhen) {
		this.batLastUpdWhen = batLastUpdWhen;
	}

	public List<AxisIngredient> getIngredient() {
		return ingredient;
	}

	public void setIngredient(List<AxisIngredient> ingredient) {
		this.ingredient = ingredient;
	}

	public Timestamp getPatientDob() {
		return patientDob;
	}

	public void setPatientDob(Timestamp patientDob) {
		this.patientDob = patientDob;
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

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public Boolean getClosedSystem() {
		return closedSystem;
	}

	public void setClosedSystem(Boolean closedSystem) {
		this.closedSystem = closedSystem;
	}

	public String getInfusionDuration() {
		return infusionDuration;
	}

	public void setInfusionDuration(String infusionDuration) {
		this.infusionDuration = infusionDuration;
	}

	public String getBatComments() {
		return batComments;
	}

	public void setBatComments(String batComments) {
		this.batComments = batComments;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getBatOnhold() {
		return batOnhold;
	}

	public void setBatOnhold(String batOnhold) {
		this.batOnhold = batOnhold;
	}

	public String getBatdrug2Key() {
		return batdrug2Key;
	}

	public void setBatdrug2Key(String batdrug2Key) {
		this.batdrug2Key = batdrug2Key;
	}

	public String getBatDsu2key() {
		return batDsu2key;
	}

	public void setBatDsu2key(String batDsu2key) {
		this.batDsu2key = batDsu2key;
	}

	public BigDecimal getBatDose2() {
		return batDose2;
	}

	public void setBatDose2(BigDecimal batDose2) {
		this.batDose2 = batDose2;
	}

	public String getBatdrug3Key() {
		return batdrug3Key;
	}

	public void setBatdrug3Key(String batdrug3Key) {
		this.batdrug3Key = batdrug3Key;
	}

	public String getBatDsu3key() {
		return batDsu3key;
	}

	public void setBatDsu3key(String batDsu3key) {
		this.batDsu3key = batDsu3key;
	}

	public BigDecimal getBatDose3() {
		return batDose3;
	}

	public void setBatDose3(BigDecimal batDose3) {
		this.batDose3 = batDose3;
	}

	public Boolean getBatCsoverride() {
		return batCsoverride;
	}

	public void setBatCsoverride(Boolean batCsoverride) {
		this.batCsoverride = batCsoverride;
	}

	public String getBatPrepdtoverride() {
		return batPrepdtoverride;
	}

	public void setBatPrepdtoverride(String batPrepdtoverride) {
		this.batPrepdtoverride = batPrepdtoverride;
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

	public Boolean getIsDeliveryRunRestricted() {
		return isDeliveryRunRestricted;
	}

	public void setIsDeliveryRunRestricted(Boolean isDeliveryRunRestricted) {
		this.isDeliveryRunRestricted = isDeliveryRunRestricted;
	}

	public String getBatReplacementbatkey() {
		return batReplacementbatkey;
	}

	public void setBatReplacementbatkey(String batReplacementbatkey) {
		this.batReplacementbatkey = batReplacementbatkey;
	}

	public Boolean getHasDeliveryRunIncentive() {
		return hasDeliveryRunIncentive;
	}

	public void setHasDeliveryRunIncentive(Boolean batIncentive) {
		this.hasDeliveryRunIncentive = batIncentive;
	}

}