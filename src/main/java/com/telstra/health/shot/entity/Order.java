package com.telstra.health.shot.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * The persistent class for the ORD_ORDER database table.
 * 
 */
@Entity
@Table(name = "ORD_ORDER")
public class Order implements Serializable {

	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<Batch> batches;

	@Id
	@Column(name = "ORD_Key", unique = true)
	private String ordKey;

	@Column(name = "ORD_CUSKey")
	private String ordCusKey;

	@Column(name = "ORD_No")
	private String ordNo;

	@Column(name = "ORD_Status")
	private String ordStatus;

	@Column(name = "ORD_Date")
	private Timestamp ordDate;

	@Column(name = "ORD_SendDate")
	private Timestamp ordSendDate;

	@Column(name = "ORD_RecDate")
	private Timestamp ordRecDate;

	@Column(name = "ORD_EntDate")
	private Timestamp ordEntDate;

	@Column(name = "ORD_AdminFrom")
	private String ordAdminFrom;

	@Column(name = "ORD_AdminTo")
	private String ordAdminTo;

	@Column(name = "ORD_LastUpdWhen")
	private Timestamp ordlastUpdWhen;

	@Column(name = "ORD_LastUpdAction")
	private String ordLastUpdAction;

	@Column(name = "ORD_Priority")
	private String ordPriority;

	@Column(name = "ORD_ENTKey")
	private String ordEntKey;

	@Column(name = "ORD_LastUpdBy")
	private int ordLastUpdBy;

	@Column(name = "ord_billto")
	private String ordBillTo;

	@Column(name = "ord_deliverylocation")
	private String ordDeliveryLocation;

	@Column(name = "ord_deliveryDate")
	private Timestamp ordDeliveryDate;

	public List<Batch> getBatches() {
		return batches;
	}

	public void setBatches(List<Batch> batches) {
		this.batches = batches;
	}

	public String getOrdKey() {
		return ordKey;
	}

	public void setOrdKey(String ordKey) {
		this.ordKey = ordKey;
	}

	public String getOrdCusKey() {
		return ordCusKey;
	}

	public void setOrdCusKey(String ordCusKey) {
		this.ordCusKey = ordCusKey;
	}

	public String getOrdNo() {
		return ordNo;
	}

	public void setOrdNo(String ordNo) {
		this.ordNo = ordNo;
	}

	public String getOrdStatus() {
		return ordStatus;
	}

	public void setOrdStatus(String ordStatus) {
		this.ordStatus = ordStatus;
	}

	public Timestamp getOrdDate() {
		return ordDate;
	}

	public void setOrdDate(Timestamp ordDate) {
		this.ordDate = ordDate;
	}

	public Timestamp getOrdSendDate() {
		return ordSendDate;
	}

	public void setOrdSendDate(Timestamp ordSendDate) {
		this.ordSendDate = ordSendDate;
	}

	public Timestamp getOrdRecDate() {
		return ordRecDate;
	}

	public void setOrdRecDate(Timestamp ordRecDate) {
		this.ordRecDate = ordRecDate;
	}

	public Timestamp getOrdEntDate() {
		return ordEntDate;
	}

	public void setOrdEntDate(Timestamp ordEntDate) {
		this.ordEntDate = ordEntDate;
	}

	public String getOrdAdminFrom() {
		return ordAdminFrom;
	}

	public void setOrdAdminFrom(String ordAdminFrom) {
		this.ordAdminFrom = ordAdminFrom;
	}

	public String getOrdAdminTo() {
		return ordAdminTo;
	}

	public void setOrdAdminTo(String ordAdminTo) {
		this.ordAdminTo = ordAdminTo;
	}

	public Timestamp getOrdlastUpdWhen() {
		return ordlastUpdWhen;
	}

	public void setOrdlastUpdWhen(Timestamp ordlastUpdWhen) {
		this.ordlastUpdWhen = ordlastUpdWhen;
	}

	public String getOrdLastUpdAction() {
		return ordLastUpdAction;
	}

	public void setOrdLastUpdAction(String ordLastUpdAction) {
		this.ordLastUpdAction = ordLastUpdAction;
	}

	public String getOrdPriority() {
		return ordPriority;
	}

	public void setOrdPriority(String ordPriority) {
		this.ordPriority = ordPriority;
	}

	public String getOrdEntKey() {
		return ordEntKey;
	}

	public void setOrdEntKey(String ordEntKey) {
		this.ordEntKey = ordEntKey;
	}

	public int getOrdLastUpdBy() {
		return ordLastUpdBy;
	}

	public void setOrdLastUpdBy(int ordLastUpdBy) {
		this.ordLastUpdBy = ordLastUpdBy;
	}

	public String getOrdBillTo() {
		return ordBillTo;
	}

	public void setOrdBillTo(String ordBillTo) {
		this.ordBillTo = ordBillTo;
	}

	public String getOrdDeliveryLocation() {
		return ordDeliveryLocation;
	}

	public void setOrdDeliveryLocation(String ordDeliveryLocation) {
		this.ordDeliveryLocation = ordDeliveryLocation;
	}

	public Timestamp getOrdDeliveryDate() {
		return ordDeliveryDate;
	}

	public void setOrdDeliveryDate(Timestamp ordDeliveryDate) {
		this.ordDeliveryDate = ordDeliveryDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
