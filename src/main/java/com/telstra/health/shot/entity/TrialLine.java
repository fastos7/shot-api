package com.telstra.health.shot.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="TRL_TrialLine")
public class TrialLine implements Serializable {

	private static final long serialVersionUID = 747085918582740223L;

	@Id
	@Column(name="TRL_Key")
	private String key;
	
	@Column(name = "TRL_STKKey")
	private String stkKey;
	
	@Column(name="TRL_DELKey")
	private String delKey;
	
	@Column(name="TRL_Active")
	private String active;
	
	@Column(name="TRL_CusKey")
	private String cusKey;
	
	@ManyToOne
	@JoinColumn(name = "TRL_TRIKey")
	private Trial trial;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDelKey() {
		return delKey;
	}

	public void setDelKey(String delKey) {
		this.delKey = delKey;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getStkKey() {
		return stkKey;
	}

	public void setStkKey(String stkKey) {
		this.stkKey = stkKey;
	}

	public String getCusKey() {
		return cusKey;
	}

	public void setCusKey(String cusKey) {
		this.cusKey = cusKey;
	}

	public Trial getTrial() {
		return trial;
	}

	public void setTrial(Trial trial) {
		this.trial = trial;
	}
	
	
}
