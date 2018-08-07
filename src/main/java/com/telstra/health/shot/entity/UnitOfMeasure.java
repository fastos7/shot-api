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
public class UnitOfMeasure implements Serializable {

	private static final long serialVersionUID = 2754206078422411775L;
	
	@Id
	@Column(name="COD_Key")
	private String key;
	
	@Column(name="COD_Code")
	private String code;

	@Column(name="Cod_Type")
	private String type;
	
	@Column(name = "Cod_Desc")
	private String description;

	@Column(name = "Cod_Active")
	private String active;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}
	
	
}
