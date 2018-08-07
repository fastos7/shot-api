package com.telstra.health.shot.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="STK_Stock")
public class Stock implements Serializable {

	private static final long serialVersionUID = 3804550172244010016L;

	@Id
	@Column(name="STK_Key")
	private String key;
	
	@Column(name="STK_Active")
	private String active;
	
	@Column(name="STK_Type")
	private String type;
	
	@Column(name="STK_Code")
	private String code;
	
	@Column(name="STK_Description")
	private String description;

	@Column(name="STK_AmountUnits")
	private String amountUnits;
	
//	@ManyToOne 
//	@JoinColumn(name="STK_AmountUnits",referencedColumnName="COD_Code",insertable=false,updatable=false)
//	private UnitOfMeasure unitOfMeasure;
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAmountUnits() {
		return amountUnits;
	}

	public void setAmountUnits(String amountUnits) {
		this.amountUnits = amountUnits;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
}
