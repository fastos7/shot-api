package com.telstra.health.shot.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.telstra.health.shot.dao.exception.DataAccessException;
import com.telstra.health.shot.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@PersistenceContext
	private EntityManager entityManager;

	public Customer findCustomer(String customerKey) {
		return entityManager.find(Customer.class, customerKey);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Customer> getCustomerLocationsByCustomer(String customerKey) {
		try {
			return entityManager.createQuery(
					"select custLocation from com.telstra.health.shot.entity.Customer custLocation where custLocation.cusParentkey = :customerKey and custLocation.cusCategory = 'DL' order by custLocation.cusName")
					.setParameter("customerKey", customerKey)
					.getResultList();
		} catch (Exception ex) {
			logger.error("Failed to get Customer Locations by Customer: for Customer {}", customerKey);
			throw new DataAccessException(ex.getMessage());
		}
	}
}
