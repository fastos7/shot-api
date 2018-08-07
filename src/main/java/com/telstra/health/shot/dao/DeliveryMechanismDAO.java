package com.telstra.health.shot.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.telstra.health.shot.entity.DeliveryMechanisms;

/**
 * This repository extends <code>JpaRepository</code> so that <code>getOne</code>
 * will be available which is usefull to get a reference of an object for later
 * update operation.
 * 
 * @author Marlon Cenita
 *
 */
public interface DeliveryMechanismDAO  extends JpaRepository<DeliveryMechanisms,String > {

}
