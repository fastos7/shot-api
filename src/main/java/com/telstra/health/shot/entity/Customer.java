package com.telstra.health.shot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.telstra.health.shot.common.enums.AusState;

@Entity
@Table(name = "CUS_Customer")
public class Customer {
	
	@Id
	@Column(name = "cus_key")
	private String cusKey;
	
	@Column(name = "cus_name")
	private String cusName;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "cus_billto")
	private Customer cusBillto;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "cus_billto2")
	private Customer cusBillto2;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "cus_billto3")
	private Customer cusBillto3;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "cus_billto4")
	private Customer cusBillto4;
	
	@Column(name = "cus_parentkey")
	private String cusParentkey;
	
	@Column(name = "cus_category")
	private String cusCategory;
	
	@Column(name = "CUS_Timezone")
	private String timeZone; 

	@Column(name = "CUS_Active")
	private String cusActive; 

	public String getCusKey() {
		return cusKey;
	}

	public void setCusKey(String cusKey) {
		this.cusKey = cusKey;
	}

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public Customer getCusBillto() {
		return cusBillto;
	}

	public void setCusBillto(Customer cusBillto) {
		this.cusBillto = cusBillto;
	}

	public Customer getCusBillto2() {
		return cusBillto2;
	}

	public void setCusBillto2(Customer cusBillto2) {
		this.cusBillto2 = cusBillto2;
	}

	public Customer getCusBillto3() {
		return cusBillto3;
	}

	public void setCusBillto3(Customer cusBillto3) {
		this.cusBillto3 = cusBillto3;
	}

	public Customer getCusBillto4() {
		return cusBillto4;
	}

	public void setCusBillto4(Customer cusBillto4) {
		this.cusBillto4 = cusBillto4;
	}

	public String getCusParentkey() {
		return cusParentkey;
	}

	public void setCusParentkey(String cusParentkey) {
		this.cusParentkey = cusParentkey;
	}

	public String getCusCategory() {
		return cusCategory;
	}

	public void setCusCategory(String cusCategory) {
		this.cusCategory = cusCategory;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String mainState) {
		this.timeZone = mainState;
	}

	public String getCusActive() {
		return cusActive;
	}

	public void setCusActive(String cusActive) {
		this.cusActive = cusActive;
	}

}
