package com.telstra.health.shot.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.telstra.health.shot.entity.UnitOfMeasure;

/**
 * 
 * TODO: Insert class explaination here.
 *
 * @author Marlon Cenita
 *
 */
@Transactional
public interface UnitOfMeasureDAO extends JpaRepository<UnitOfMeasure,String > {
	
	@Query("select uom from UnitOfMeasure uom where uom.code = ?1 and uom.active = 'A' and uom.type = 'AMOUNTUNITS'")
	public UnitOfMeasure findActiveUnitOfMeasureByCode(String code);
}