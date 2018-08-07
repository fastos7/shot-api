package com.telstra.health.shot.dto;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.telstra.health.shot.common.enums.OrderViewType;

public class UserDTO {	
	
	private Long userId;
	private String lastName;	
	private String email;	
	private List<UserAuthoritiesDTO> userAuthorities = new ArrayList<>();
	private String firstName;
	private String defaultSite;
	private String defaultOrderView;
	private String password;
	private Boolean isShotAdmin;
	private Boolean isActive;
	private Long loginUserId;
	private String loginRole;
	private List < String > userSiteKeys;

	public UserDTO(String givenName, String surName, String email, String defaultSite) {
		// TODO Auto-generated constructor stub
		this.firstName = givenName;
		this.lastName = surName;
		this.email = email;
		this.defaultSite = defaultSite;

	}

	public UserDTO() {
		// TODO Auto-generated constructor stub
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<UserAuthoritiesDTO> getUserAuthorities() {
		return userAuthorities;
	}

	public void setUserAuthorities(List<UserAuthoritiesDTO> userAuthorities) {
		this.userAuthorities = userAuthorities;
	}

	public String getDefaultSite() {
		return defaultSite;
	}

	public void setDefaultSite(String defaultSite) {
		this.defaultSite = defaultSite;
	}

	@Override
	public int hashCode() {

		return new HashCodeBuilder(17, 37).append(email).append(firstName).append(lastName).append(defaultSite)
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

		UserDTO other = (UserDTO) obj;

		return new EqualsBuilder().append(email, other.email).append(firstName, other.firstName)
				.append(lastName, other.lastName).append(defaultSite, other.defaultSite).isEquals();

	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getIsShotAdmin() {
		return isShotAdmin;
	}

	public void setIsShotAdmin(Boolean isSladeAdmin) {
		this.isShotAdmin = isSladeAdmin;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public void addUserAuthorities(UserAuthoritiesDTO authoritiesDTO) {
		this.userAuthorities.add(authoritiesDTO);
	}

	public Long getLoginUserId() {
		return loginUserId;
	}

	public void setLoginUserId(Long loginUserId) {
		this.loginUserId = loginUserId;
	}

	public String getLoginRole() {
		return loginRole;
	}

	public void setLoginRole(String loginRole) {
		this.loginRole = loginRole;
	}

	public String getDefaultOrderView() {
		if (defaultOrderView == null) {
			defaultOrderView = OrderViewType.DAY_VIEW_DELV_TIME.name();
		}
		return defaultOrderView;
	}

	public void setDefaultOrderView(String defaultOrderView) {
		this.defaultOrderView = defaultOrderView;
	}

	public List<String> getUserSiteKeys() {
		return userSiteKeys;
	}

	public void setUserSiteKeys(List<String> userSiteKeys) {
		this.userSiteKeys = userSiteKeys;
	}

}
