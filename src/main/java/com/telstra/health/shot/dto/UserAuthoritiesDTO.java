package com.telstra.health.shot.dto;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class UserAuthoritiesDTO {
	
 	private String customerKey;
	private String customerName;
	private Integer roleId;
	private String role;
	private String roleDesc;
	private Boolean isDefaultSite;
	private List<String> roles = new ArrayList<>(); 
	private List<String> roleDescriptions = new ArrayList<>();
	private List<UserRoleDTO> roleDTOs = new ArrayList<>();
	private List<UserSite> billTos = new ArrayList<>();
	
	public UserAuthoritiesDTO(String customerKey, String customerName, String role) {
		super();
 		this.customerKey = customerKey;
		this.customerName = customerName;
		this.role = role;
	}
 
	public UserAuthoritiesDTO(String customerKey, String customerName, String role, String roleDesc, String billTo1Key, String billTo1Name,
			String billTo2Key, String billTo2Name, String billTo3Key, String billTo3Name, String billTo4Key,
			String billTo4Name) {
		super();
		this.customerKey = customerKey;
		this.customerName = customerName;
		this.role = role;
		this.roleDesc = roleDesc;
		if (billTo1Key != null) {
			this.billTos.add(new UserSite(billTo1Key, billTo1Name));
		}
		if (billTo2Key != null) {
			this.billTos.add(new UserSite(billTo2Key, billTo2Name));
		}
		if (billTo3Key != null) {
			this.billTos.add(new UserSite(billTo3Key, billTo3Name));
		}
		if (billTo4Key != null) {
			this.billTos.add(new UserSite(billTo4Key, billTo4Name));
		}
	}

	public UserAuthoritiesDTO() {
	}

	public String getCustomerKey() {
		return customerKey;
	}
	public void setCustomerKey(String customerKey) {
		this.customerKey = customerKey;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	} 
	
	@Override
	public int hashCode() {
		
		return new HashCodeBuilder(17, 37)
        .append(customerKey).append(customerName).append(roles).append(role)
        .toHashCode(); 
	}
 
	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		UserAuthoritiesDTO other = (UserAuthoritiesDTO) obj;
		
		return new EqualsBuilder()
        .append(customerKey, other.customerKey)
        .append(customerName, other.customerName)
        .append(role, other.role) 
        .append(roles, other.roles) 
        .isEquals();
		
	 
	}

	public static class UserSite {
		private String customerKey;
		private String customerName;
		
		public UserSite() {
		}

		public UserSite(String customerKey, String customerName) {
			super();
			this.customerKey = customerKey;
			this.customerName = customerName;
		}

		public String getCustomerKey() {
			return customerKey;
		}

		public void setCustomerKey(String customerKey) {
			this.customerKey = customerKey;
		}

		public String getCustomerName() {
			return customerName;
		}

		public void setCustomerName(String customerName) {
			this.customerName = customerName;
		}
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public List<UserSite> getBillTos() {
		return billTos;
	}

	public void setBillTos(List<UserSite> billTos) {
		this.billTos = billTos;
	}

	public Boolean getIsDefaultSite() {
		return isDefaultSite;
	}

	public void setIsDefaultSite(Boolean isDefaultSite) {
		this.isDefaultSite = isDefaultSite;
	}

	public List<UserRoleDTO> getRoleDTOs() {
		return roleDTOs;
	}

	public void setRoleDTOs(List<UserRoleDTO> rolesDTOs) {
		this.roleDTOs = rolesDTOs;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public List<String> getRoleDescriptions() {
		return roleDescriptions;
	}

	public void setRoleDescriptions(List<String> roleDescriptions) {
		this.roleDescriptions = roleDescriptions;
	}

}
