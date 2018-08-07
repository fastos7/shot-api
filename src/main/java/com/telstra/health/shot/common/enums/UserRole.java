package com.telstra.health.shot.common.enums;

public enum UserRole {
	CUSTOMER_SUPER_USER(7, "CUSTOMER_SUPER_USER", "Customer Super User"),
	SLADE_ADMIN(8, "SLADE_ADMINISTRATOR", "Slade Administor");

	private Integer roleId;
	private String roleName;
	private String roleDesc;

	private UserRole(Integer roleId, String roleName, String roleDesc) {
		this.roleId = roleId;
		this.roleName = roleName;
		this.roleDesc = roleDesc;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}
}
