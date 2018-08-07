package com.telstra.health.shot.dto;

import java.io.Serializable;

public class StockDTO implements Serializable {

	private static final long serialVersionUID = 3974764667572053150L;

	private String key;
	private String active;
	private String type;
	private String code;
	private String amountUnits;
	
	public StockDTO() {
		super();
	}

	public StockDTO(String key, String active, String type, String code, String amountUnits) {
		super();
		this.key = key;
		this.active = active;
		this.type = type;
		this.code = code;
		this.amountUnits = amountUnits;
	}

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((active == null) ? 0 : active.hashCode());
		result = prime * result + ((amountUnits == null) ? 0 : amountUnits.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		StockDTO other = (StockDTO) obj;
		if (active == null) {
			if (other.active != null)
				return false;
		} else if (!active.equals(other.active))
			return false;
		if (amountUnits == null) {
			if (other.amountUnits != null)
				return false;
		} else if (!amountUnits.equals(other.amountUnits))
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "StockDTO [key=" + key + ", active=" + active + ", type=" + type + ", code=" + code + ", amountUnits="
				+ amountUnits + "]";
	}
	
	
}
