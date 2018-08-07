package com.telstra.health.shot.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SHOT_Roles")
public class Roles implements Serializable {
	
 	/**
	 * 
	 */
	private static final long serialVersionUID = -573000675358368507L;

	@Id
	@Column(name = "roleid")
	private int roleId;
 	
	@Column(name = "rolename")
	private String roleName;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "BillingRight")
	private Boolean hasBillingRight;

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getHasBillingRight() {
		return hasBillingRight;
	}

	public void setHasBillingRight(Boolean hasBillingRight) {
		this.hasBillingRight = hasBillingRight;
	}
	
	

}
