package com.telstra.health.shot.dto;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.telstra.health.shot.util.ShotUtils;

public class DeliveryDateTimeDTO implements Comparable < DeliveryDateTimeDTO > {

	@JsonIgnore
	private ZonedDateTime localDateTime;
	
	@JsonIgnore
	private ZonedDateTime dispatchDateTime;

	private String value;
	private String dispatchDateTimeValue;
	private boolean withinCutOff1Time;
	private boolean withinIncentiveCutoffTime;

	public DeliveryDateTimeDTO() {
		
	}
	
	public DeliveryDateTimeDTO(final ZonedDateTime localDateTime, final ZonedDateTime dispatchDateTime, final boolean withinCutOff1Time,
			final boolean withinIncentiveCutoffTime) {
		this.localDateTime = localDateTime;
		this.dispatchDateTime = dispatchDateTime;
		this.withinCutOff1Time = withinCutOff1Time;
		this.withinIncentiveCutoffTime = withinIncentiveCutoffTime;

		if (localDateTime != null) {
			this.value = ShotUtils.formatDateToJS(localDateTime, true);
		}
		if (dispatchDateTime != null) {
			this.dispatchDateTimeValue = ShotUtils.formatDateToJS(dispatchDateTime, true);
		}
	}

	public ZonedDateTime getLocalDateTime() {
		return localDateTime;
	}

	public void setLocalDateTime(ZonedDateTime localDateTime) {
		this.localDateTime = localDateTime;
		if (localDateTime != null) {
			this.value = ShotUtils.formatDateToJS(localDateTime, true);
		}
	}

	public boolean isWithinCutOff1Time() {
		return withinCutOff1Time;
	}

	public void setWithinCutOff1Time(boolean withinCutOff1Time) {
		this.withinCutOff1Time = withinCutOff1Time;
	}

	@Override
	public int compareTo(DeliveryDateTimeDTO deliveryDateTime) {
		return this.localDateTime != null ? 
				this.localDateTime.compareTo(deliveryDateTime.getLocalDateTime()) : -1;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof DeliveryDateTimeDTO) {
			DeliveryDateTimeDTO deliveryDTO = (DeliveryDateTimeDTO)obj;
			return this.localDateTime.equals(deliveryDTO.getLocalDateTime())
					&& this.withinCutOff1Time == deliveryDTO.isWithinCutOff1Time()
					&& this.withinIncentiveCutoffTime == deliveryDTO.isWithinIncentiveCutoffTime();
		}
		return false;
	}
	
	@Override
	public String toString() {
		return String.valueOf(this.localDateTime);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isWithinIncentiveCutoffTime() {
		return withinIncentiveCutoffTime;
	}

	public void setWithinIncentiveCutoffTime(boolean withinIncentiveCutoffTime) {
		this.withinIncentiveCutoffTime = withinIncentiveCutoffTime;
	}

	public String getDispatchDateTimeValue() {
		return dispatchDateTimeValue;
	}

	public void setDispatchDateTimeValue(String dispatchDateTimeValue) {
		this.dispatchDateTimeValue = dispatchDateTimeValue;
	}

	public ZonedDateTime getDispatchDateTime() {
		return dispatchDateTime;
	}

	public void setDispatchDateTime(ZonedDateTime dispatchDateTime) {
		this.dispatchDateTime = dispatchDateTime;
		if (dispatchDateTime != null) {
			this.dispatchDateTimeValue = ShotUtils.formatDateToJS(dispatchDateTime, true);
		}
	}
}
