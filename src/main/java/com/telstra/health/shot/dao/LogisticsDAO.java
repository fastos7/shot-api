package com.telstra.health.shot.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.telstra.health.shot.common.enums.OrderType;
import com.telstra.health.shot.entity.Logistic;
import com.telstra.health.shot.entity.reference.Facility;

public interface LogisticsDAO extends CrudRepository < Logistic, Long > {

	List < Logistic > findByCustomerAndOrderTypeAndFacility(String customerKey, OrderType orderType, Facility facility);
	
	@Query("select sum(batch.quantity) from Order ord, ShotBatch batch where ord.ordKey = batch.orderId and ord.ordCusKey = ?1 and ord.ordEntKey = ?2 and ord.ordSendDate = ?3 and batch.isDeliveryRunRestricted = true and batch.status in ('Submitted', 'Preproduction', 'Production') ")
	Integer findDeliveryRunQty(String customerKey, String entityKey, Date dispatchDateTime);
}
