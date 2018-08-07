package com.telstra.health.shot.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.telstra.health.shot.entity.Patient;

@Transactional
public interface PatientDAO extends  CrudRepository<Patient, Long>, PatientDAOCustom { 
	
	@Query(" from Patient where patientId = :patientId and customerKey =  :customerKey ")
	Patient findOne(@Param("patientId") long patientId, @Param("customerKey") String customerKey);
 
}
