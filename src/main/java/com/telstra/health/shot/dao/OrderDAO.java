package com.telstra.health.shot.dao;



import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.telstra.health.shot.entity.Order;

@Transactional
public interface OrderDAO extends CrudRepository<Order,String >, OrderDAOCustom{

	

}
