package com.telstra.health.shot.entity.reference;

import java.sql.Timestamp;
import java.time.MonthDay;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "SHOT_FACILITY_HOLIDAY")
public class FacilityHoliday {

	@Id
	@Column(name = "FAC_HOLIDAY_ID")
	private Integer facilityHolidayId;

	@ManyToOne
	@JoinColumn(name = "ENT_KEY")
	private Facility facility;

	@Column(name = "HOLIDAY_DATE")
	private Date holidayDate;

	@Column(name="CreatedDate")
	private Timestamp createdDate;

	@Transient
	private MonthDay monthDay;

	public Integer getFacilityHolidayId() {
		return facilityHolidayId;
	}

	public void setFacilityHolidayId(Integer facilityHolidayId) {
		this.facilityHolidayId = facilityHolidayId;
	}

	public Facility getFacility() {
		return facility;
	}

	public void setFacility(Facility facility) {
		this.facility = facility;
	}

	public MonthDay getMonthDay() {
		if (this.monthDay == null) {
			// this.monthDay = MonthDay.of(this.holidayMonth, this.holidayDay);
			this.monthDay = FacilityHoliday.getMonthDay(this.holidayDate);
		}
		return this.monthDay;
	}

	public Date getHolidayDate() {
		return holidayDate;
	}

	public void setHolidayDate(Date holidayDate) {
		this.holidayDate = holidayDate;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public static MonthDay getMonthDay(Date holidayDate) {
		MonthDay monthDay = null;
		if (holidayDate != null) {
			monthDay = MonthDay.from(holidayDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		}
		return monthDay;
	}

	public static List < MonthDay > getMonthDays(List < FacilityHoliday > facHolidays) {
		if (facHolidays != null) {
			return facHolidays.stream().map(
					facHoliday -> FacilityHoliday.getMonthDay(facHoliday.getHolidayDate()))
					.collect(Collectors.toList());
		}
		return null;
	}
	
}
