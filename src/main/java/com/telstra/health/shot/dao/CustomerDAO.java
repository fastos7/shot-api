package com.telstra.health.shot.dao;

import java.util.List;

import com.telstra.health.shot.entity.Customer;

public interface CustomerDAO {

	List < Customer > getCustomerLocationsByCustomer(String customerKey);

	Customer findCustomer(String customerKey);
}
