package com.telstra.health.shot.entity.reference;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.telstra.health.shot.common.enums.AusState;

/**
 * Represents the Manufacturing Site Facility.
 * @author osama.shakeel
 *
 */
@Entity
@Table(name = "ENT_ENTITY")
public class Facility {

	@Id
	@Column(name = "ENT_KEY")
	private String entityKey;

	@Column(name = "ENT_REG_NAME")
	private String name;

	@Column(name = "ENT_PHONE")
	private String phoneNo;
	
	/**
	 * It can have the following values ("Australia/*") depending on where the facility is located:
	 * Australia/Queensland: +10
	 * Australia/NSW: +10/+11
	 * Australia/ACT: +10/+11
	 * Australia/Victoria: +10/+11
	 * Australia/Tasmania: +10/+11
	 * Australia/Adelaide: +09:30/+10:30
	 * Australia/Perth: +08
	 * Australia/Darwin: +09:30
	 * Mentioned above are all GMT Offsets for the zone.
	 */
	@Column(name = "ENT_STATE")
	private String stateName;

	@Column(name = "ENT_TIMEZONE")
	private String timeZone;

	/**
	 * Get the current local time of the facility.
	 * @return ZonedDateTime instance. It also takes care of DST.
	 */
	public ZonedDateTime getCurrentLocalFacilityTime() {
		ZonedDateTime facTime = null;
		try {
			facTime =  ZonedDateTime.ofInstant(Instant.now(), ZoneId.of(this.getTimeZone()));
		} catch (Exception e) {
			facTime = ZonedDateTime.ofInstant(
					Instant.now(), AusState.getTimeZoneByState(this.getStateName()));
		}
		//facTime =  ZonedDateTime.of(LocalDateTime.of(2018, 03, 13, 13, 33, 45), AusState.getTimeZoneByState(this.getStateName()));
		return facTime;
	}
	
	public Date getCurrentLocalFacilityDate(ZonedDateTime zonedDateTime) {
		if (zonedDateTime == null) {
			zonedDateTime = this.getCurrentLocalFacilityTime();
		}
		return new Date(zonedDateTime.with(LocalTime.MIDNIGHT).toInstant().toEpochMilli());
	}

	public String getEntityKey() {
		return entityKey;
	}

	public void setEntityKey(String facilityId) {
		this.entityKey = facilityId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String zoneName) {
		this.stateName = zoneName;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String contactNo) {
		this.phoneNo = contactNo;
	}

	public static void main(String[] args) {
		LocalDate date = LocalDate.now();
		System.out.println(ChronoUnit.DAYS.between(date, date.plusDays(1L)));
		/*ZonedDateTime zdt = ZonedDateTime.of(2017, 12, 28, 1, 0, 0, 0, ZoneId.of("Australia/Darwin"));
		WeekFields weekFields = WeekFields.of(Locale.getDefault());
		System.out.println(zdt.get(weekFields.weekOfWeekBasedYear()));
		System.out.println(zdt);
		System.out.println(zdt.getZone());
		System.out.println(zdt.getOffset());
		System.out.println(zdt.getOffset().get(ChronoField.OFFSET_SECONDS)/3600);*/
		
		ZonedDateTime adelaideTime = ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Australia/Wollongong"));
//		ZonedDateTime adelaideTime = ZonedDateTime.of(2017, 12, 28, 1, 0, 0, 0, ZoneId.of("Australia/Queensland"));
		System.out.println(adelaideTime);
//		System.out.println(adelaideTime.getZone());
//		System.out.println(adelaideTime.getOffset());
//		System.out.println(
//				(double)adelaideTime.getOffset().get(ChronoField.OFFSET_SECONDS)
//				/3600);
		
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
}
