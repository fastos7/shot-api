package com.telstra.health.shot.service;

import static org.mockito.Mockito.doThrow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telstra.health.shot.config.ShotApiApplicationTests;
import com.telstra.health.shot.dao.OrderDAO;
import com.telstra.health.shot.dao.ShotBatchDAO;
import com.telstra.health.shot.resources.DummyOrders;
import com.telstra.health.shot.resources.DummyShotBatch;
import com.telstra.health.shot.service.OrderServiceImpl;
import com.telstra.health.shot.service.exception.ShotServiceException;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { ShotApiApplicationTests.class })
public class TestOrderServiceImpl {

	@Autowired
	@InjectMocks
	private OrderServiceImpl orderService;

	@Mock
	private OrderDAO orderDao;

	@Mock
	private ShotBatchDAO shotDao;

	ObjectMapper mapper = new ObjectMapper();

	@Before
	public void setup() throws ShotServiceException {

	}

	@Test
	public void testSaveOrdersException() throws Exception {

		doThrow(new DataIntegrityViolationException(
				"Cannot insert duplicate key row in object 'dbo.ORD_Order' with unique index 'ORD_UNIQUE_IX'. The duplicate key value is (10SYD101, <NULL>, 986667)"))
						.when(shotDao).save(DummyShotBatch.getTestShotBatch());
		 //when(shotDao.save(DummyShotOrder.getTestShotOrder())).thenReturn(DummyShotOrder.getTestShotOrder());
		 //doThrow(new DataIntegrityViolationException("Cannot insert duplicate key row
		// in object 'dbo.ORD_Order' with unique index 'ORD_UNIQUE_IX'. The duplicate
		// key value is (10SYD101, <NULL>, 986667)"))
		// .when(orderDao).save(DummyOrders.getTestOrders());

		try {
			orderService.saveOrder(DummyOrders.getTestOrderDto());
		} catch (ShotServiceException ex) {
			Assert.assertEquals("OrderServiceImpl.saveOrder() : Failed to create the orders.", ex.getMessage());
		}

	}

}
