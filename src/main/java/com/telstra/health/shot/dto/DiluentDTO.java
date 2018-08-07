package com.telstra.health.shot.dto;

import java.io.Serializable;

/**
 * 
 * TODO: Insert class explaination here.
 *
 * @author Marlon Cenita
 *
 */
public class DiluentDTO implements Serializable {

	private static final long serialVersionUID = -8640405526860672929L;
	
	private String stockKey;
	private String stockDescription;
	private String stockCode;
	
	public DiluentDTO() {
		super();
	}

	public DiluentDTO(String stockKey, String stockDescription, String stockCode) {
		super();
		this.stockKey = stockKey;
		this.stockDescription = stockDescription;
		this.stockCode = stockCode;
	}

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

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((stockCode == null) ? 0 : stockCode.hashCode());
		result = prime * result + ((stockDescription == null) ? 0 : stockDescription.hashCode());
		result = prime * result + ((stockKey == null) ? 0 : stockKey.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DiluentDTO other = (DiluentDTO) obj;
		if (stockCode == null) {
			if (other.stockCode != null)
				return false;
		} else if (!stockCode.equals(other.stockCode))
			return false;
		if (stockDescription == null) {
			if (other.stockDescription != null)
				return false;
		} else if (!stockDescription.equals(other.stockDescription))
			return false;
		if (stockKey == null) {
			if (other.stockKey != null)
				return false;
		} else if (!stockKey.equals(other.stockKey))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DiluentDTO [stockKey=" + stockKey + ", stockDescription=" + stockDescription + ", stockCode="
				+ stockCode + "]";
	}
	
}
