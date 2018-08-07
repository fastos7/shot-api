package com.telstra.health.shot.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class AxisBatchAuditDTO implements Serializable {

	private static final long serialVersionUID = -7627769530127314820L;
	
	private String batKey;	
	private String batLastUpdWhen;
	private Long shotBatchKey;
	private Long batLastUpdBy;
	private String batLastUpdByFirstName;
	private String batLastUpdByLastName;
	private String batLastUpdBySystem;
	private Integer batQuantity;	
	private String batDescription;
	private BigDecimal batDose;	
	private String batDoseUnits;	
	private BigDecimal batDose2;	
	private String batDoseUnit2;	
	private BigDecimal batDose3;
	private String batDoseUnit3;
	private BigDecimal batSpecifiedVolume;
	private String batExact;
	private String administrationRoute;	
	private String batTreatDate;
	private String batStatus;	
	private String batShipDate;
	private String batComments;
	
	public AxisBatchAuditDTO() {
		
	}
	
	public AxisBatchAuditDTO(String batKey, String batLastUpdWhen, Long shotBatchKey, Long batLastUpdBy,
			String batLastUpdByFirstName, String batLastUpdByLastName, String batLastUpdBySystem, Integer batQuantity,String batDescription,
			BigDecimal batDose, String batDoseUnits, BigDecimal batDose2, String batDoseUnit2, BigDecimal batDose3,
			String batDoseUnit3, BigDecimal batSpecifiedVolume,String batExact, String administrationRoute, String batTreatDate,
			String batStatus, String batShipDate, String batComments) {
		super();
		this.batKey = batKey;
		this.batLastUpdWhen = batLastUpdWhen;
		this.shotBatchKey = shotBatchKey;
		this.batLastUpdBy = batLastUpdBy;
		this.batLastUpdByFirstName = batLastUpdByFirstName;
		this.batLastUpdByLastName = batLastUpdByLastName;
		this.batLastUpdBySystem = batLastUpdBySystem;
		this.batQuantity = batQuantity;
		this.batDescription = batDescription;
		this.batDose = batDose;
		this.batDoseUnits = batDoseUnits;
		this.batDose2 = batDose2;
		this.batDoseUnit2 = batDoseUnit2;
		this.batDose3 = batDose3;
		this.batDoseUnit3 = batDoseUnit3;
		this.batSpecifiedVolume = batSpecifiedVolume;
		this.batExact = batExact;
		this.administrationRoute = administrationRoute;
		this.batTreatDate = batTreatDate;
		this.batStatus = batStatus;
		this.batShipDate = batShipDate;
		this.batComments = batComments;
	}

	public String getBatKey() {
		return batKey;
	}

	public void setBatKey(String batKey) {
		this.batKey = batKey;
	}

	public String getBatLastUpdWhen() {
		return batLastUpdWhen;
	}

	public void setBatLastUpdWhen(String batLastUpdWhen) {
		this.batLastUpdWhen = batLastUpdWhen;
	}

	public Long getShotBatchKey() {
		return shotBatchKey;
	}

	public void setShotBatchKey(Long shotBatchKey) {
		this.shotBatchKey = shotBatchKey;
	}

	public Long getBatLastUpdBy() {
		return batLastUpdBy;
	}

	public void setBatLastUpdBy(Long batLastUpdBy) {
		this.batLastUpdBy = batLastUpdBy;
	}

	public String getBatLastUpdByFirstName() {
		return batLastUpdByFirstName;
	}

	public void setBatLastUpdByFirstName(String batLastUpdByFirstName) {
		this.batLastUpdByFirstName = batLastUpdByFirstName;
	}

	public String getBatLastUpdByLastName() {
		return batLastUpdByLastName;
	}

	public void setBatLastUpdByLastName(String batLastUpdByLastName) {
		this.batLastUpdByLastName = batLastUpdByLastName;
	}

	public String getBatLastUpdBySystem() {
		return batLastUpdBySystem;
	}

	public void setBatLastUpdBySystem(String batLastUpdBySystem) {
		this.batLastUpdBySystem = batLastUpdBySystem;
	}

	public Integer getBatQuantity() {
		return batQuantity;
	}

	public void setBatQuantity(Integer batQuantity) {
		this.batQuantity = batQuantity;
	}

	public BigDecimal getBatDose() {
		return batDose;
	}

	public void setBatDose(BigDecimal batDose) {
		this.batDose = batDose;
	}

	public String getBatDoseUnits() {
		return batDoseUnits;
	}

	public void setBatDoseUnits(String batDoseUnits) {
		this.batDoseUnits = batDoseUnits;
	}

	public BigDecimal getBatDose2() {
		return batDose2;
	}

	public void setBatDose2(BigDecimal batDose2) {
		this.batDose2 = batDose2;
	}

	public String getBatDoseUnit2() {
		return batDoseUnit2;
	}

	public void setBatDoseUnit2(String batDoseUnit2) {
		this.batDoseUnit2 = batDoseUnit2;
	}

	public BigDecimal getBatDose3() {
		return batDose3;
	}

	public void setBatDose3(BigDecimal batDose3) {
		this.batDose3 = batDose3;
	}

	public String getBatDoseUnit3() {
		return batDoseUnit3;
	}

	public void setBatDoseUnit3(String batDoseUnit3) {
		this.batDoseUnit3 = batDoseUnit3;
	}

	public BigDecimal getBatSpecifiedVolume() {
		return batSpecifiedVolume;
	}

	public void setBatSpecifiedVolume(BigDecimal batSpecifiedVolume) {
		this.batSpecifiedVolume = batSpecifiedVolume;
	}
	
	public String getBatExact() {
		return batExact;
	}

	public void setBatExact(String batExact) {
		this.batExact = batExact;
	}

	public String getAdministrationRoute() {
		return administrationRoute;
	}

	public void setAdministrationRoute(String administrationRoute) {
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

	public String getBatDescription() {
		return batDescription;
	}

	public void setBatDescription(String batDescription) {
		this.batDescription = batDescription;
	}
	
	
}
