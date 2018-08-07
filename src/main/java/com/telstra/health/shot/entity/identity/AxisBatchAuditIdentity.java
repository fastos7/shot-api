package com.telstra.health.shot.entity.identity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import com.telstra.health.shot.entity.converter.TimestampToStringConverter;

@Embeddable
public class AxisBatchAuditIdentity implements Serializable {

	private static final long serialVersionUID = 576345398295575118L;
	
	@NotNull
	@Column(name="Bat_Key")
	private String batKey;
	
	@NotNull
	@Column(name="Bat_LastUpdWhen")
	@Convert(converter= TimestampToStringConverter.class)
	private String batLastUpdWhen;
	
	public AxisBatchAuditIdentity() {
	
	}

	public AxisBatchAuditIdentity(String batKey, String batLastUpdWhen) {		
		this.batKey = batKey;
		this.batLastUpdWhen = batLastUpdWhen;
	}

	public String getBatKey() {
		return batKey;
	}

	public void setBatKey(String batKey) {
		this.batKey = batKey;
	}

	public String getBatLastUpdWhen() {
		return batLastUpdWhen;
	}

	public void setBatLastUpdWhen(String batLastUpdWhen) {
		this.batLastUpdWhen = batLastUpdWhen;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((batKey == null) ? 0 : batKey.hashCode());
		result = prime * result + ((batLastUpdWhen == null) ? 0 : batLastUpdWhen.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AxisBatchAuditIdentity other = (AxisBatchAuditIdentity) obj;
		if (batKey == null) {
			if (other.batKey != null)
				return false;
		} else if (!batKey.equals(other.batKey))
			return false;
		if (batLastUpdWhen == null) {
			if (other.batLastUpdWhen != null)
				return false;
		} else if (!batLastUpdWhen.equals(other.batLastUpdWhen))
			return false;
		return true;
	}
	
}
