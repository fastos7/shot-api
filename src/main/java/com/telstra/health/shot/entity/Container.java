package com.telstra.health.shot.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * TODO: Insert class explaination here.
 *
 * @author Marlon Cenita
 *
 */
@Entity
@Table(name="STK_Stock")
public class Container implements Serializable {

	private static final long serialVersionUID = -6361240297951059275L;
	
	@Id
	@Column(name = "STK_Key")
	private String stockKey;
	
	@Column(name = "STK_Description")
	private String stockDescription;
	
	@Column(name = "Stk_Type")
	private String stockType;

	@Column(name = "Stk_Code")
	private String stockCode;

	@Column(name = "Stk_Active")
	private String stockActive;

	public String getStockKey() {
		return stockKey;
	}

	public void setStockKey(String stockKey) {
		this.stockKey = stockKey;
	}

	public String getStockDescription() {
		return stockDescription;
	}

	public void setStockDescription(String stockDescription) {
		this.stockDescription = stockDescription;
	}

	public String getStockType() {
		return stockType;
	}

	public void setStockType(String stockType) {
		this.stockType = stockType;
	}

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public String getStockActive() {
		return stockActive;
	}

	public void setStockActive(String stockActive) {
		this.stockActive = stockActive;
	}
	
	
}