package com.telstra.health.shot.resources;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.telstra.health.shot.dto.OrderDTO;
import com.telstra.health.shot.entity.Order;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class DummyOrders {

	
	public static List<OrderDTO> getTestOrderDto() throws Exception {
		
		List<OrderDTO> orderList = new ArrayList<OrderDTO>();

		String dateTime = "2017-12-06 17:55:50";
		SimpleDateFormat formatter4 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Date currentDate = formatter4.parse(dateTime);
		OrderDTO orderDTOObj = new OrderDTO();
		orderDTOObj.setOrdNo("12345");
		orderDTOObj.setOrdCusKey("10SYD101");
		orderDTOObj.setOrdLastUpdAction("I");
		orderDTOObj.setOrdStatus("N");
		orderList.add(orderDTOObj);
		return orderList;
		
		
	
		
		
		
	}
}

