package com.telstra.health.shot.resources;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.MonthDay;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.telstra.health.shot.common.enums.OrderType;
import com.telstra.health.shot.dto.DeliveryDateTimeDTO;
import com.telstra.health.shot.entity.CustomerFacility;
import com.telstra.health.shot.entity.CustomerFacilityId;
import com.telstra.health.shot.entity.Logistic;
import com.telstra.health.shot.entity.reference.Facility;

public class LogisticsTestData {

	private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
	private static DateTimeFormatter dayMonthFormatter = DateTimeFormatter.ofPattern("MM-dd");

	/**
	 * Create list of Delivery Date Time results based on the provided string array of delivery date times
	 * @param deliveryList Date times in format: yyyy-MM-dd hh:mm
	 * @return
	 */
	public static List < DeliveryDateTimeDTO > getDeliveryDateTimes(List < Object[] > deliveryList) {
		List < DeliveryDateTimeDTO > deliveryDateTimes = new ArrayList<>();
//		deliveryList.forEach(delivery -> deliveryDateTimes
//				.add(new DeliveryDateTimeDTO(LocalDateTime.parse((String) delivery[0], dateTimeFormatter),
//						(Boolean) delivery[1], (Boolean) delivery[2])));
		return deliveryDateTimes;
	}
	
	/**
	 * Create the provided Customer's facility
	 * @param customerKey
	 * @param facility
	 * @return
	 */
	public static CustomerFacility getCustomerFacility(String customerKey, Facility facility) {
		CustomerFacility customerFacility = new CustomerFacility();
		CustomerFacilityId customerFacilityId = new CustomerFacilityId();
		customerFacilityId.setCustomerKey(customerKey);
		customerFacilityId.setFacility(facility);
		customerFacility.setCustomerFacilityId(customerFacilityId);

		return customerFacility;
	}

	public static List < MonthDay > getHolidays(List < String > holidays) {
		List < MonthDay > holidayList = null;
		if (holidays != null) {
			holidayList = holidays.stream().map(
				hol -> MonthDay.parse(hol, dayMonthFormatter)
			).collect(Collectors.toList());
		}
		return holidayList;
	}

	public static ZonedDateTime getZonedDateTime(String dateTime, String zoneId) {
		return ZonedDateTime.of(
				LocalDateTime.parse(
						dateTime, dateTimeFormatter), ZoneId.of(zoneId));
	}

	/**
	 * Bendigo	Non-formulation	WestMelb	
	 * D0	10	D0	14	4	D0	17	D+1	8	Mon Tue Wed Thur
	 * D-2	10	D-2	14	4	D0	17	D+1	8	Sun
	 */
	public static List < Logistic > getBendigoLogistics() {
		List < Logistic > logistics = new ArrayList<>();
		Logistic logistic = new Logistic();
		logistic.setId(1L);
		logistic.setCustomer("1");
		logistic.setOrderType(OrderType.NON_FORMULATED);
		logistic.setIncentiveDayOffset(1);
		logistic.setIncentiveTimeOffset(15);
		logistic.setCutOff1DayOffset(0);
		logistic.setCutOff1TimeOffset(10);
		logistic.setCutOff2DayOffset(0);
		logistic.setCutOff2TimeOffset(15);
		logistic.setCutOff2QuantityLimit(4);
		logistic.setDispatchTime(17);
		logistic.setDeliveryDayOffset(1);
		logistic.setDeliveryTime(8);
		SortedSet< DayOfWeek > dispatchDays = new TreeSet<>();
		Collections.addAll(dispatchDays, 
				DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY);
		logistic.setDispatchDays(dispatchDays);
		logistics.add(logistic);

		logistic = new Logistic();
		logistic.setId(2L);
		logistic.setCustomer("1");
		logistic.setOrderType(OrderType.NON_FORMULATED);
		logistic.setIncentiveDayOffset(3);
		logistic.setIncentiveTimeOffset(15);
		logistic.setCutOff1DayOffset(2);
		logistic.setCutOff1TimeOffset(10);
		logistic.setCutOff2DayOffset(2);
		logistic.setCutOff2TimeOffset(14);
		logistic.setCutOff2QuantityLimit(4);
		logistic.setDispatchTime(17);
		logistic.setDeliveryDayOffset(1);
		logistic.setDeliveryTime(8);
		dispatchDays = new TreeSet<>();
		Collections.addAll(dispatchDays, DayOfWeek.SUNDAY);
		logistic.setDispatchDays(dispatchDays);
		logistics.add(logistic);
		
		return logistics;
	}

	/**
	 * Frankston Formulation Mount Waverly	
	 * D-2	10	D-2 14	4	D0	16	D+0	17	Wed, Thu, Fri
	 */
	public static List < Logistic > getFrankstonLogistics() {
		List < Logistic > logistics = new ArrayList<>();
		Logistic logistic = new Logistic();
		logistic.setId(1L);
		logistic.setCustomer("1");
		logistic.setOrderType(OrderType.FORMULATED_NON_TPN);
		logistic.setCutOff1DayOffset(2);
		logistic.setCutOff1TimeOffset(10);
		logistic.setCutOff2DayOffset(2);
		logistic.setCutOff2TimeOffset(14);
		logistic.setCutOff2QuantityLimit(4);
		logistic.setDispatchTime(16);
		logistic.setDeliveryDayOffset(0);
		logistic.setDeliveryTime(17);
		SortedSet< DayOfWeek > dispatchDays = new TreeSet<>();
		Collections.addAll(dispatchDays, 
				DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY);
		logistic.setDispatchDays(dispatchDays);
		logistics.add(logistic);

		logistic = new Logistic();
		logistic.setId(2L);
		logistic.setCustomer("1");
		logistic.setOrderType(OrderType.NON_FORMULATED);
		logistic.setCutOff1DayOffset(2);
		logistic.setCutOff1TimeOffset(10);
		logistic.setCutOff2DayOffset(2);
		logistic.setCutOff2TimeOffset(14);
		logistic.setCutOff2QuantityLimit(4);
		logistic.setDispatchTime(17);
		logistic.setDeliveryDayOffset(1);
		logistic.setDeliveryTime(8);
		dispatchDays = new TreeSet<>();
		Collections.addAll(dispatchDays, DayOfWeek.SUNDAY);
		logistic.setDispatchDays(dispatchDays);
		//logistics.add(logistic);
		
		return logistics;
	}
}
