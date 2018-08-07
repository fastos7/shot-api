package com.telstra.health.shot.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

import com.telstra.health.shot.common.enums.OrderViewType;

@Entity
@Table(name = "SHOT_Users")
public class Users implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4868886676850407822L;

	@Id
	@Column(name = "userid")
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "shotUser-Identity", strategy = "com.telstra.health.shot.entity.generator.UserIdGenerator")
	@GeneratedValue(generator = "shotUser-Identity", strategy = GenerationType.IDENTITY)
	private Long userId;

	@Column(name = "firstname")
	private String firstName;

	@Column(name = "lastName")
	private String lastName;

	@Column(name = "email")
	private String email;

	@Column(name = "isactive")
	@Type(type = "yes_no")
	private Boolean isActive;

	@Column(name = "isDeleted")
	@Type(type = "yes_no")
	private Boolean isDeleted;

	@Column(name = "defaultcustomersite")
	private String defaultCustomerSite;

	@Enumerated(EnumType.STRING)
	@Column(name = "DefaultOrderView")
	private OrderViewType defaultOrderView;

	@Column(name = "createdby")
	private String createdBy;

	@Column(name = "createddate")
	private Date createdDate;

	@Column(name = "updatedby")
	private String updatedBy;

	@Column(name = "updateddate")
	private Date updatedDate;

	@Version
	private Long version;

	@Column(name = "ShotAdmin")
	private Boolean isShotAdmin;

	@Column(name = "LAST_LOGIN_TIME")
	private Date lastLoginDate;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@Where(clause = "isActive = 'Y'")
	private Set<UserRoles> userRoles;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public void setLastName(String lasttname) {
		this.lastName = lasttname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean isActive() {
		return isActive;
	}

	public void setActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getDefaultCustomerSite() {
		return defaultCustomerSite;
	}

	public void setDefaultCustomerSite(String defaultCustomerSite) {
		this.defaultCustomerSite = defaultCustomerSite;
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

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Boolean getIsShotAdmin() {
		return isShotAdmin;
	}

	public void setIsShotAdmin(Boolean isShotAdmin) {
		this.isShotAdmin = isShotAdmin;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Set<UserRoles> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<UserRoles> userRoles) {
		this.userRoles = userRoles;
	}

	public String toString() {
		return this.getFirstName();
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public OrderViewType getDefaultOrderView() {
		if (defaultOrderView == null) {
			defaultOrderView = OrderViewType.DAY_VIEW_DELV_TIME;
		}
		return defaultOrderView;
	}

	public void setDefaultOrderView(OrderViewType defaultOrderView) {
		this.defaultOrderView = defaultOrderView;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
}
