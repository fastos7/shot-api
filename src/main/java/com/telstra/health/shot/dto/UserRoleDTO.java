package com.telstra.health.shot.dto;

import java.io.Serializable;


/**
 * The persistent class for the UserRoles database table.
 * 
 */
 
public class UserRoleDTO implements Serializable { 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1832276735899166712L;
	
	private int roleId;
	private String role; 
	private String description;
	private Boolean hasBillingRight;
	
	public UserRoleDTO() {
	}
	
	public UserRoleDTO(int roleId, String role, String description, Boolean hasBillingRight) {
		super();
		this.roleId = roleId;
		this.role = role;
		this.description = description;
		this.hasBillingRight = hasBillingRight;
	}

	public int getRoleId() {
		return this.roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

 	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

 	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Boolean getHasBillingRight() {
		return hasBillingRight;
	}

	public void setHasBillingRight(Boolean hasBillingRight) {
		this.hasBillingRight = hasBillingRight;
	}

}