package com.telstra.health.shot.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.telstra.health.shot.entity.AdministrationRoute;

@Transactional
public interface AdminstrationRouteDAO extends JpaRepository<AdministrationRoute,String > {
	
	@Query("select ar from AdministrationRoute ar where ar.code = ?1 and ar.active = 'A' and ar.codeType = 'ADMINROUTE'")
	public AdministrationRoute findActiveAdministrationRouteByCode(String code);
}