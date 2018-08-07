package com.telstra.health.shot.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;


@Entity
@Table(name="SHOT_home_page_actions")
public class HomePageActions { 
	
	@Id
	@Column(name = "id")
	private int id;	
	
	@Column(name = "webpageurl")
	private String webPageUrl;
 	
	@Column(name = "webpagename")
	private String webPageName;
	
	@Column(name = "isactive")
	@Type(type="yes_no")
	private boolean isActive;
	
	@Column(name = "iconname")
	private String iconName;
	
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

	public String getIconName() {
		return iconName;
	}

	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	public String getWebPageUrl() {
		return webPageUrl;
	}

	public void setWebPageUrl(String webPageUrl) {
		this.webPageUrl = webPageUrl;
	}

 

}
