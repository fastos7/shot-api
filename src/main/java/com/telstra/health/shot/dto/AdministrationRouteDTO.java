package com.telstra.health.shot.dto;

import java.io.Serializable;

/**
 * 
 * TODO: Insert class explaination here.
 *
 * @author Marlon Cenita
 *
 */
public class AdministrationRouteDTO implements Serializable {

	private static final long serialVersionUID = 3406757536288673697L;
	
	private String code;
	private String codeType;
	private String description;
	
	public AdministrationRouteDTO() {
		super();
	}

	public AdministrationRouteDTO(String code, String codeType, String description) {
		super();
		this.code = code;
		this.codeType = codeType;
		this.description = description;
	}
		
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
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
		result = prime * result + ((codeType == null) ? 0 : codeType.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
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
		AdministrationRouteDTO other = (AdministrationRouteDTO) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (codeType == null) {
			if (other.codeType != null)
				return false;
		} else if (!codeType.equals(other.codeType))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AdministrationRouteDTO [code=" + code + ", codeType=" + codeType + ", description=" + description + "]";
	}
}
