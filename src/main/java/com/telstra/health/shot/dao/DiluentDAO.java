package com.telstra.health.shot.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.telstra.health.shot.entity.Diluent;

/**
 * 
 * TODO: Insert class explaination here.
 *
 * @author Marlon Cenita
 *
 */
@Transactional
public interface DiluentDAO extends JpaRepository<Diluent,String > {
	
	@Query("select d from Diluent d where d.stockKey = ?1 and d.stockActive = 'A' and d.stockType = 'DLT'")
	public Diluent findActiveDiluentByKey(String stockKey);
}
