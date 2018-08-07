package com.telstra.health.shot.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.telstra.health.shot.entity.converter.TimestampToStringConverter;
import com.telstra.health.shot.entity.identity.AxisBatchAuditIdentity;

@Entity
@Table(name="Batch_Audit_History")
public class AxisBatchAudit implements Serializable {

	private static final long serialVersionUID = 3174514033593710507L;
	
	@EmbeddedId
	private AxisBatchAuditIdentity axisBatchAuditIdentity; 
	
	@Column(name="ShotBatchKey")
	private Long shotBatchKey;
	
	@ManyToOne
	@JoinColumn(name = "Bat_LastUpdBy",referencedColumnName="UserId")	
	private CombinedAxisAndShotUsers batLastUpdBy;
	
	@Column(name="Bat_Description")
	private String batDescription;
	
	@Column(name="Bat_Quantity")
	private Integer batQuantity;
	
	@Column(name="Bat_Dose")
	private BigDecimal batDose;
	
	@ManyToOne
	@JoinColumn(name = "BAT_DoseUnits",referencedColumnName="COD_Code")
	private AmountUnits batDoseUnits;
	
	@Column(name="Bat_Dose2")
	private BigDecimal batDose2;
	
	@ManyToOne	
	@JoinColumn(name = "BAT_DoseUnit2",referencedColumnName="COD_Code")
	private AmountUnits batDoseUnit2;
	
	@Column(name="Bat_Dose3")
	private BigDecimal batDose3;

	@ManyToOne
	@JoinColumn(name = "BAT_DoseUnit3",referencedColumnName="COD_Code")
	private AmountUnits batDoseUnit3;
	  
	@Column(name="BAT_SpecifiedVolume")
	private BigDecimal batSpecifiedVolume;
	
	@ManyToOne
	@JoinColumn(name = "BAT_AdminRouteCode",referencedColumnName="COD_Code")
	private AdministrationRoute administrationRoute;
	
	@Column(name="BAT_TreatDate")
	@Convert(converter= TimestampToStringConverter.class)
	private String batTreatDate;
	
	@Column(name="BAT_Status")
	private String batStatus;
	
	@Column(name="BAT_ShipDate")
	@Convert(converter= TimestampToStringConverter.class)
	private String batShipDate;
	
	@Column(name="Bat_comments")
	private String batComments;
	
	@Column(name="Bat_exact")
	private String batExact;
	
	public AxisBatchAuditIdentity getAxisBatchAuditIdentity() {
		return axisBatchAuditIdentity;
	}

	public void setAxisBatchAuditIdentity(AxisBatchAuditIdentity axisBatchAuditIdentity) {
		this.axisBatchAuditIdentity = axisBatchAuditIdentity;
	}

	public Long getShotBatchKey() {
		return shotBatchKey;
	}

	public void setShotBatchKey(Long shotBatchKey) {
		this.shotBatchKey = shotBatchKey;
	}

	public CombinedAxisAndShotUsers getBatLastUpdBy() {
		return batLastUpdBy;
	}

	public void setBatLastUpdBy(CombinedAxisAndShotUsers batLastUpdBy) {
		this.batLastUpdBy = batLastUpdBy;
	}

	public Integer getBatQuantity() {
		return batQuantity;
	}

	public void setBatQuantity(Integer batQuantity) {
		this.batQuantity = batQuantity;
	}
	
	public String getBatDescription() {
		return batDescription;
	}

	public void setBatDescription(String batDescription) {
		this.batDescription = batDescription;
	}

	public BigDecimal getBatDose() {
		return batDose;
	}

	public void setBatDose(BigDecimal batDose) {
		this.batDose = batDose;
	}

	public AmountUnits getBatDoseUnits() {
		return batDoseUnits;
	}

	public void setBatDoseUnits(AmountUnits batDoseUnits) {
		this.batDoseUnits = batDoseUnits;
	}

	public BigDecimal getBatDose2() {
		return batDose2;
	}

	public void setBatDose2(BigDecimal batDose2) {
		this.batDose2 = batDose2;
	}

	public AmountUnits getBatDoseUnit2() {
		return batDoseUnit2;
	}

	public void setBatDoseUnit2(AmountUnits batDoseUnit2) {
		this.batDoseUnit2 = batDoseUnit2;
	}

	public BigDecimal getBatDose3() {
		return batDose3;
	}

	public void setBatDose3(BigDecimal batDose3) {
		this.batDose3 = batDose3;
	}

	public AmountUnits getBatDoseUnit3() {
		return batDoseUnit3;
	}

	public void setBatDoseUnit3(AmountUnits batDoseUnit3) {
		this.batDoseUnit3 = batDoseUnit3;
	}

	public BigDecimal getBatSpecifiedVolume() {
		return batSpecifiedVolume;
	}

	public void setBatSpecifiedVolume(BigDecimal batSpecifiedVolume) {
		this.batSpecifiedVolume = batSpecifiedVolume;
	}

	public AdministrationRoute getAdministrationRoute() {
		return administrationRoute;
	}

	public void setAdministrationRoute(AdministrationRoute administrationRoute) {
		this.administrationRoute = administrationRoute;
	}

	public String getBatTreatDate() {
		return batTreatDate;
	}

	public void setBatTreatDate(String batTreatDate) {
		this.batTreatDate = batTreatDate;
	}

	public String getBatStatus() {
		return batStatus;
	}

	public void setBatStatus(String batStatus) {
		this.batStatus = batStatus;
	}

	public String getBatShipDate() {
		return batShipDate;
	}

	public void setBatShipDate(String batShipDate) {
		this.batShipDate = batShipDate;
	}

	public String getBatComments() {
		return batComments;
	}

	public void setBatComments(String batComments) {
		this.batComments = batComments;
	}

	public String getBatExact() {
		return batExact;
	}

	public void setBatExact(String batExact) {
		this.batExact = batExact;
	}
		
	
}
