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
import javax.persistence.Transient;

import com.telstra.health.shot.dto.ExpiryDTO;


@Entity
@Table(name = "SHOT_TEMP_INGREDIENT")
public class ShotTempIngredient implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "ING_BATKey")
	private ShotTempBatch shotTempBatch;

	@Id
 	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ING_Key", unique = true)
	private long ingKey;

	@Column(name = "ING_STKKey")
	private String ingStkkey;

	@Column(name = "ING_Status")
	private String ingStatus;

	@Column(name = "ING_QtyOrdered")
	private double ingQtyordered;

	@Column(name = "ING_Chargeable")
	private String ingChargeable;

	@Column(name = "ING_Type")
	private String xxxType;

	@Column(name = "ING_LastUpdWhen")
	private Timestamp ingLastUpdWhen;

	@Column(name = "ING_LastUpdAction")
	private String ingLastupdaction;

	@Column(name = "ING_LastUpdBy")
	private int ingLastupdby;

	@Column(name = "ing_item_sequence")
	private int ingItemsequence;
	
	@Transient
	private ExpiryDTO expiry;

	public ShotTempBatch getShotTempBatch() {
		return shotTempBatch;
	}

	public void setShotTempBatch(ShotTempBatch shotTempBatch) {
		this.shotTempBatch = shotTempBatch;
	}

	public long getIngKey() {
		return ingKey;
	}

	public void setIngKey(long ingKey) {
		this.ingKey = ingKey;
	}

	public String getIngStkkey() {
		return ingStkkey;
	}

	public void setIngStkkey(String ingStkkey) {
		this.ingStkkey = ingStkkey;
	}

	public String getIngStatus() {
		return ingStatus;
	}

	public void setIngStatus(String ingStatus) {
		this.ingStatus = ingStatus;
	}

	public double getIngQtyordered() {
		return ingQtyordered;
	}

	public void setIngQtyordered(double ingQtyordered) {
		this.ingQtyordered = ingQtyordered;
	}

	public String getIngChargeable() {
		return ingChargeable;
	}

	public void setIngChargeable(String ingChargeable) {
		this.ingChargeable = ingChargeable;
	}

	public String getXxxType() {
		return xxxType;
	}

	public void setXxxType(String xxxType) {
		this.xxxType = xxxType;
	}

	public String getIngLastupdaction() {
		return ingLastupdaction;
	}

	public void setIngLastupdaction(String ingLastupdaction) {
		this.ingLastupdaction = ingLastupdaction;
	}

	public int getIngLastupdby() {
		return ingLastupdby;
	}

	public void setIngLastupdby(int ingLastupdby) {
		this.ingLastupdby = ingLastupdby;
	}

	public int getIngItemsequence() {
		return ingItemsequence;
	}

	public void setIngItemsequence(int ingItemsequence) {
		this.ingItemsequence = ingItemsequence;
	}

	public Timestamp getIngLastUpdWhen() {
		return ingLastUpdWhen;
	}

	public void setIngLastUpdWhen(Timestamp ingLastUpdWhen) {
		this.ingLastUpdWhen = ingLastUpdWhen;
	}

	public ExpiryDTO getExpiry() {
		return expiry;
	}

	public void setExpiry(ExpiryDTO expiry) {
		this.expiry = expiry;
	}

}
