package com.telstra.health.shot.dto;

import java.io.Serializable;

/**
 * 
 * TODO: Insert class explaination here.
 *
 * @author Marlon Cenita
 *
 */
public class UnitOfMeasureDTO implements Serializable {
	
	private static final long serialVersionUID = -7220752426384359796L;
	
	private String code;
	private String type;
	private String description;
	
	public UnitOfMeasureDTO() {
		super();	
	}

	public UnitOfMeasureDTO(String code, String type, String description) {
		super();
		this.code = code;
		this.type = type;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
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
		UnitOfMeasureDTO other = (UnitOfMeasureDTO) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
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
		return "UnitOfMeasureDTO [code=" + code + ", type=" + type + ", description=" + description + "]";
	}

	
	
}
