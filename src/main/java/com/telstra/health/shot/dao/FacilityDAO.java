package com.telstra.health.shot.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.telstra.health.shot.entity.reference.Facility;
import com.telstra.health.shot.entity.reference.FacilityHoliday;

public interface FacilityDAO extends JpaRepository< Facility, String > {

	//@Query("select f from CustomerFacility f where f.customerFacilityId.customerKey = ?1")
	//public CustomerFacility findCustomerFacilityByCustomerKey(String customerKey);
	
	@Query("select f from FacilityHoliday f where f.facility = ?1 and f.holidayDate between ?2 and ?3")
	public List < FacilityHoliday > findFacilityHolidays(Facility facility, Date fromDate, Date toDate);
}
