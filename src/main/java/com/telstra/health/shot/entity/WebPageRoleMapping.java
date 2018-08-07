package com.telstra.health.shot.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name="SHOT_webpage_role_mapping")
public class WebPageRoleMapping {	
	
	@Id
	@Column(name = "id")
	private int id;
	
	@Column(name = "webpagename")
	private String webPageName;

	@Column(name = "roleid")
	private int roleId; 
	
	@Column(name = "isactive")
	@Type(type="yes_no")
	private boolean isActive;
	
	@Column(name = "createdby")
	private String createdBy;
	
	@Column(name = "createddate")
	private Date createdDate;

	@Column(name = "updatedby")
	private String updatedBy;
	
	@Column(name = "updateddate")
	private Date updatedDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWebPageName() {
		return webPageName;
	}

	public void setWebPageName(String webPageName) {
		this.webPageName = webPageName;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
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
	
	
	

}
