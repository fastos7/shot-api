package com.telstra.health.shot.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="TRI_Trial")
public class Trial implements Serializable {

	private static final long serialVersionUID = -4385056033826610852L;
	
	@Id
	@Column(name="TRI_Key")
	private String key;
	
	@Column(name="TRI_Name")
	private String name;
	
	@Column(name="TRI_Active")
	private String active;
	
	@OneToMany(mappedBy="trial")
	private Set<TrialLine> trialLines;
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public Set<TrialLine> getTrialLines() {
		return trialLines;
	}

	public void setTrialLines(Set<TrialLine> trialLines) {
		this.trialLines = trialLines;
	}
	
	
}
