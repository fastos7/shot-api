package com.telstra.health.shot.dto;

import java.io.Serializable;

public class TrialLineDTO implements Serializable {

	private static final long serialVersionUID = -6070331004850914064L;
	
	private String key;
	private String stkKey;
	private String delKey;
	private String active;
	private String cusKey;
	
	public TrialLineDTO() {
	
	}

	public TrialLineDTO(String key, String stkKey, String delKey, String active,
			String cusKey) {
		super();
		this.key = key;
		this.stkKey = stkKey;
		this.delKey = delKey;
		this.active = active;
		this.cusKey = cusKey;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getStkKey() {
		return stkKey;
	}

	public void setStkKey(String stkKey) {
		this.stkKey = stkKey;
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

	public String getCusKey() {
		return cusKey;
	}

	public void setCusKey(String cusKey) {
		this.cusKey = cusKey;
	}

	@Override
	public String toString() {
		return "TrialLineDTO [key=" + key + ", stkKey=" + stkKey + ", delKey="
				+ delKey + ", active=" + active + ", cusKey=" + cusKey + "]";
	}
	
	
}
