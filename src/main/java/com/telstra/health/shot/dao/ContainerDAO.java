package com.telstra.health.shot.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.telstra.health.shot.entity.Container;

/**
 * 
 * TODO: Insert class explaination here.
 *
 * @author Marlon Cenita
 *
 */
@Transactional
public interface ContainerDAO extends JpaRepository<Container,String > {
	
	@Query("select c from Container c where c.stockKey = ?1 and c.stockActive = 'A' and c.stockType = 'CNT'")
	public Container findActiveContainerByKey(String stockKey);
}