package com.telstra.health.shot.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.Type;


/**
 * The persistent class for the UserRoles database table.
 * 
 */
@Entity
@Table(name="SHOT_User_Roles")
public class UserRoles implements Serializable {  
	
 	/**
	 * 
	 */
	private static final long serialVersionUID = 8830428511167603130L;

	@Id
 	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "userroleid")
	private Long userRoleId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "userId")
	private Users user;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "customerkey")
	private Customer customer;
	
	@Column(name = "customername")
	private String customerName;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "roleId")
	private Roles role; 	

	@Column(name = "isactive")
	@Type(type="yes_no")
	private Boolean isActive;
	
	@Column(name = "createdby")
	private String createdBy;
	
	@Column(name = "createddate")
	private Date createdDate;

	@Column(name = "updatedby")
	private String updatedBy;
	
	@Column(name = "updateddate")
	private Date updatedDate;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "BillTo1")
	private Customer billTo1;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "BillTo2")
	private Customer billTo2;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "BillTo3")
	private Customer billTo3;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "BillTo4")
	private Customer billTo4;
	
	@Version
	private Long version;
	
	public Long getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(Long userRoleId) {
		this.userRoleId = userRoleId;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users userId) {
		this.user = userId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Roles getRole() {
		return role;
	}

	public void setRole(Roles role) {
		this.role = role;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	} 
	
	
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Customer getBillTo1() {
		return billTo1;
	}

	public void setBillTo1(Customer billTo1) {
		this.billTo1 = billTo1;
	}

	public Customer getBillTo2() {
		return billTo2;
	}

	public void setBillTo2(Customer billTo2) {
		this.billTo2 = billTo2;
	}

	public Customer getBillTo3() {
		return billTo3;
	}

	public void setBillTo3(Customer billTo3) {
		this.billTo3 = billTo3;
	}

	public Customer getBillTo4() {
		return billTo4;
	}

	public void setBillTo4(Customer billTo4) {
		this.billTo4 = billTo4;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

}