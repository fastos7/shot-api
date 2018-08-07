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
@Table(name="COD_Code")
public class AdministrationRoute implements Serializable  {
	
	private static final long serialVersionUID = -3551740922485074393L;
	
	@Id
	@Column(name="COD_Key")
	private String codeKey;
	
	@Column(name="COD_Code")
	private String code;
	
	@Column(name="Cod_Type")
	private String codeType;
	
	@Column(name = "Cod_Desc")
	private String description;
	
	@Column(name = "COD_Active")
	private String active;

	public String getCodeKey() {
		return codeKey;
	}

	public void setCodeKey(String codeKey) {
		this.codeKey = codeKey;
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

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	
}
