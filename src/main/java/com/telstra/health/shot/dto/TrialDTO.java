package com.telstra.health.shot.dto;

import java.io.Serializable;
import java.util.Set;

public class TrialDTO implements Serializable {
	
	private static final long serialVersionUID = -7957541265255672112L;
	
	private String key;
	private String name;
	private String active;
	private Set<TrialLineDTO> trialLines;
	
	public TrialDTO() {
	
	}
	
	public TrialDTO(String key, String name, String active) {
		
		this.key = key;
		this.name = name;
		this.active = active;
	}

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

	public Set<TrialLineDTO> getTrialLines() {
		return trialLines;
	}

	public void setTrialLines(Set<TrialLineDTO> trialLines) {
		this.trialLines = trialLines;
	}

	@Override
	public String toString() {
		return "TrialDTO [key=" + key + ", name=" + name + ", active=" + active
				+ "]";
	}
	
	
	
}
