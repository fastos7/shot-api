package com.telstra.health.shot.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "ING_Ingredient")
public class AxisIngredient implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "ING_BATKey")
	private Batch batch;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "com.telstra.health.shot.entity.generator.KeyIdGenerator")
	@Column(name = "ING_Key", unique = true)
	private String ingKey;

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

	public Batch getBatch() {
		return batch;
	}

	public void setBatch(Batch batch) {
		this.batch = batch;
	}

	public String getIngKey() {
		return ingKey;
	}

	public void setIngKey(String ingKey) {
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

}
