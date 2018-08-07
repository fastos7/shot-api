package com.telstra.health.shot.dto;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

public class IngredientsDTO {

	private Double ingQtyordered;
	private String xxxDescription;
	private String stkCode;
	private String xxxType;
	private String ingStkkey;
	private String ingKey;
	private String ingStatus;
	private String dilKey;
	private String dilCode;
	private String stuQuantity;
	private String ingLastUpdBy;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date ingLastUpdWhen;
	private String ingLastUpdAction;
	private String ingChargeable;
	private String ingItemsequence;
	private ExpiryDTO expiry;

	public Double getIngQtyordered() {
		return ingQtyordered;
	}

	public void setIngQtyordered(Double ingQtyordered) {
		this.ingQtyordered = ingQtyordered;
	}

	public String getXxxDescription() {
		return xxxDescription;
	}

	public void setXxxDescription(String xxxDescription) {
		this.xxxDescription = xxxDescription;
	}

	public String getStkCode() {
		return stkCode;
	}

	public void setStkCode(String stkCode) {
		this.stkCode = stkCode;
	}

	public String getXxxType() {
		return xxxType;
	}

	public void setXxxType(String xxxType) {
		this.xxxType = xxxType;
	}

	public String getIngStkkey() {
		return ingStkkey;
	}

	public void setIngStkkey(String ingStkkey) {
		this.ingStkkey = ingStkkey;
	}

	public String getIngKey() {
		return ingKey;
	}

	public void setIngKey(String ingKey) {
		this.ingKey = ingKey;
	}

	public String getIngStatus() {
		return ingStatus;
	}

	public void setIngStatus(String ingStatus) {
		this.ingStatus = ingStatus;
	}

	public String getDilKey() {
		return dilKey;
	}

	public void setDilKey(String dilKey) {
		this.dilKey = dilKey;
	}

	public String getDilCode() {
		return dilCode;
	}

	public void setDilCode(String dilCode) {
		this.dilCode = dilCode;
	}

	public String getStuQuantity() {
		return stuQuantity;
	}

	public void setStuQuantity(String stuQuantity) {
		this.stuQuantity = stuQuantity;
	}

	public String getIngLastUpdBy() {
		return ingLastUpdBy;
	}

	public void setIngLastUpdBy(String ingLastUpdBy) {
		this.ingLastUpdBy = ingLastUpdBy;
	}

	public Date getIngLastUpdWhen() {
		return ingLastUpdWhen;
	}

	public void setIngLastUpdWhen(Date ingLastUpdWhen) {
		this.ingLastUpdWhen = ingLastUpdWhen;
	}

	public String getIngLastUpdAction() {
		return ingLastUpdAction;
	}

	public void setIngLastUpdAction(String ingLastUpdAction) {
		this.ingLastUpdAction = ingLastUpdAction;
	}

	public String getIngChargeable() {
		return ingChargeable;
	}

	public void setIngChargeable(String ingChargeable) {
		this.ingChargeable = ingChargeable;
	}

	public String getIngItemsequence() {
		return ingItemsequence;
	}

	public void setIngItemsequence(String ingItemsequence) {
		this.ingItemsequence = ingItemsequence;
	}

	public ExpiryDTO getExpiry() {
		return expiry;
	}

	public void setExpiry(ExpiryDTO expiry) {
		this.expiry = expiry;
	}

}
