package com.telstra.health.shot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.telstra.health.shot.entity.CustomerPreference;

/**
 * 
 * TODO: Insert class explaination here.
 *
 * @author Marlon Cenita
 *
 */
@Transactional
public interface CustomerPreferencesDAO extends JpaRepository<CustomerPreference,Integer >,
													   CustomerPreferencesDAOCustom{

	

	 
}
