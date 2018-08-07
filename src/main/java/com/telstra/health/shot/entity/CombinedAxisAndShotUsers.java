package com.telstra.health.shot.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="combined_user_master_and_shot_user_view")
public class CombinedAxisAndShotUsers implements Serializable {

	private static final long serialVersionUID = 6299385293466578708L;
	
	@Id
	@Column(name="UserId")
	private Long userId;
	
	@Column(name="FirsName")
	private String firtName;
	
	@Column(name="LastName")
	private String lastName;
	
	@Column(name="System")
	private String system;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFirtName() {
		return firtName;
	}

	public void setFirtName(String firtName) {
		this.firtName = firtName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}
	
	
	
}
