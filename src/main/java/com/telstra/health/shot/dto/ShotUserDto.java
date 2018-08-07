package com.telstra.health.shot.dto;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the ShotUser database table.
 * 
 */
 
 public class ShotUserDto implements Serializable {
 
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1480110698679061929L;
	
	private long userId;
	private String userName;
	private String password;
	private String passwordExpiry;
 	private String active;
 	private String createdBy;
	private Timestamp createdDate;
	private String updatedBy;
	private Timestamp updatedDate;
	
 	private UserRoleDTO userRole ;

	public ShotUserDto() {
	} 
	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
	
 	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

 	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

 	public String getPasswordExpiry() {
		return passwordExpiry;
	}

	public void setPasswordExpiry(String passwordExpiry) {
		this.passwordExpiry = passwordExpiry;
	}

 	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

 	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

 	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

 	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

 	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}
 
 	public UserRoleDTO getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRoleDTO userRole) {
		this.userRole = userRole;
	}
 
	 

}