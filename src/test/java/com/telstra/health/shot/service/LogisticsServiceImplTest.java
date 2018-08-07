package com.telstra.health.shot.service;

import static org.mockito.Mockito.when;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.telstra.health.shot.common.enums.OrderType;
import com.telstra.health.shot.dao.FacilityDAO;
import com.telstra.health.shot.dao.LogisticsDAO;
import com.telstra.health.shot.dao.UserDAO;
import com.telstra.health.shot.dto.DeliveryDateTimeDTO;
import com.telstra.health.shot.dto.DeliveryGroupDTO;
import com.telstra.health.shot.dto.DeliveryLocationDTO;
import com.telstra.health.shot.entity.Customer;
import com.telstra.health.shot.entity.Logistic;
import com.telstra.health.shot.entity.reference.Facility;
import com.telstra.health.shot.resources.LogisticsTestData;
import com.telstra.health.shot.service.impl.LogisticsServiceImpl;

@RunWith(SpringRunner.class)
public class LogisticsServiceImplTest {

	@InjectMocks
	private LogisticsServiceImpl logisticsService;

	@Mock
	private FacilityDAO facilityDAO;
	
	@Mock
	private LogisticsDAO logisticsDAO;

	@Mock
	Facility facility;
	
	@Mock
	UserDAO userRepository;
	
	private String customerKey = "101MEL103";

	@Before
	public void setup() {
		logisticsService.setMaxDays(7);
	}

	/**
	 * Order Date:  Mon 6 Nov 9:30 AM < 10:00 AM Cutoff1 Day
	 * Compounding Facility: Australia/NSW
	 * Public Holiday on Thu 9 Nov
	 * 
	 * Logistics Details:
	 * Cutoff1 Day/Time: D - 0, 10 AM
	 * Dispatch Days: MON, TUE, WED, THU
	 * Delivery Day Time: D + 1 - 8 AM

	 * Cutoff1 Day/Time: D - 2, 10 AM
	 * Dispatch Days: SUN
	 * Delivery Day Time: D + 1 - 8 AM

	 * Expected Dispatch Days: TUE, WED, THU, SUN, MON
	 * Expected Delivery Dates : Tue 7 Nov 8:00 AM, Wed 8 Nov 8:00 AM, Thu 9 Nov 8:00 AM, Mon 13 Nov
	 */
	@Test
	public void testBendigoLogistics_MondayOrder()  {
		List < Logistic > logistics = LogisticsTestData.getBendigoLogistics();
		prepareMockObjects("06-11-2017T09:30", "Australia/NSW", logistics, Arrays.asList("11-09"));

		DeliveryGroupDTO dGroupDTO = 
				logisticsService.calculateOrderDeliveryDateTimes(null);
						//customerKey, OrderType.NON_FORMULATED, null, null, null);
		SortedSet<DeliveryDateTimeDTO> deliveryResults = dGroupDTO.getDeliveryDateTimes();
		SortedSet<DeliveryDateTimeDTO> expectedResults = new TreeSet < DeliveryDateTimeDTO >(
				LogisticsTestData.getDeliveryDateTimes(
						Arrays.asList(
								new Object[]{"07-11-2017T08:00", true, false}, 
								new Object[]{"08-11-2017T08:00", true, true}, 
								new Object[]{"09-11-2017T08:00", true, true},
								new Object[]{"13-11-2017T08:00", true, true}
						)));

		Assert.assertNotNull(deliveryResults);
		Assert.assertTrue("The Delivery Results are not as expected",
				areDeliveryResultsEqual(deliveryResults, expectedResults));
	}

	/**
	 * Order Date:  Mon 6 Nov 9:30 AM < 10:00 AM Cutoff1 Day
	 * Compounding Facility: Australia/NSW
	 * Public Holiday on Wed 8 Nov
	 * 
	 * Logistics Details:
	 * Cutoff1 Day/Time: D - 2, 10 AM
	 * Dispatch Days: WED, THU, FRI
	 * Delivery Day Time: D + 0 - 5 PM

	 * Expected Dispatch Days: THU, FRI, WED
	 * Expected Delivery Dates: Thu 9 Nov 17:00 PM, Fri 10 Nov 17:00 PM, Wed 15 Nov 17:00 PM
	 */
	@Test
	public void testFrankstonLogistics_MondayOrderHolidayOnFirstDispatchDay()  {
		List < Logistic > logistics = LogisticsTestData.getFrankstonLogistics();
		prepareMockObjects("06-11-2017T09:30", "Australia/NSW", logistics, Arrays.asList("11-08"));

		DeliveryGroupDTO dGroupDTO = 
				logisticsService.calculateOrderDeliveryDateTimes(null);
//						customerKey, OrderType.NON_FORMULATED, null, null);
		SortedSet<DeliveryDateTimeDTO> deliveryResults = dGroupDTO.getDeliveryDateTimes();
		SortedSet<DeliveryDateTimeDTO> expectedResults = new TreeSet < DeliveryDateTimeDTO >(
				LogisticsTestData.getDeliveryDateTimes(
						Arrays.asList(
								new Object[]{"09-11-2017T17:00", true, false}, 
								new Object[]{"10-11-2017T17:00", true, false}, 
								new Object[]{"15-11-2017T17:00", true, false}
						)));

		Assert.assertNotNull(deliveryResults);
		Assert.assertTrue("The Delivery Results are not as expected",
				areDeliveryResultsEqual(deliveryResults, expectedResults));
	}

	/**
	 * Order Date:  Mon 6 Nov 9:30 AM < 10:00 AM Cutoff1 Day
	 * Compounding Facility: Australia/NSW
	 * Public Holiday on Wed 8 Nov
	 * 
	 * Logistics Details:
	 * Cutoff1 Day/Time: D - 2, 10 AM
	 * Dispatch Days: WED, THU, FRI
	 * Delivery Day Time: D + 0 - 5 PM

	 * Expected Dispatch Days: THU, FRI, WED
	 * Expected Delivery Dates: Thu 9 Nov 17:00 PM, Fri 10 Nov 17:00 PM, Wed 15 Nov 17:00 PM
	 */
	@Test
	public void testFrankstonLogistics_MondayOrder1HolidayBeforeFirstDispatchDay()  {
		List < Logistic > logistics = LogisticsTestData.getFrankstonLogistics();
		prepareMockObjects("06-11-2017T09:30", "Australia/NSW", logistics, Arrays.asList("11-07"));

		DeliveryGroupDTO dGroupDTO = 
				logisticsService.calculateOrderDeliveryDateTimes(null);
						//customerKey, OrderType.NON_FORMULATED, null, null);
		SortedSet<DeliveryDateTimeDTO> deliveryResults = dGroupDTO.getDeliveryDateTimes();

		Assert.assertNotNull(deliveryResults);
		SortedSet<DeliveryDateTimeDTO> expectedResults = new TreeSet < DeliveryDateTimeDTO >(
				LogisticsTestData.getDeliveryDateTimes(
						Arrays.asList(
								new Object[]{"09-11-2017T17:00", true, false}, 
								new Object[]{"10-11-2017T17:00", true, false}, 
								new Object[]{"15-11-2017T17:00", true, false}
						)));

		Assert.assertNotNull(deliveryResults);
		Assert.assertTrue("The Delivery Results are not as expected",
				areDeliveryResultsEqual(deliveryResults, expectedResults));
	}

	/**
	 * Order Date:  Mon 6 Nov 9:30 AM < 10:00 AM Cutoff1 Day
	 * Compounding Facility: Australia/NSW
	 * Public Holiday on Wed 8 Nov
	 * 
	 * Logistics Details:
	 * Cutoff1 Day/Time: D - 2, 10 AM
	 * Dispatch Days: WED, THU, FRI
	 * Delivery Day Time: D + 0 - 5 PM

	 * Expected Dispatch Days: THU, FRI, WED
	 * Expected Delivery Dates: Fri 10 Nov 17:00 PM, Wed 15 Nov 17:00 PM, Thu 16 Nov 17:00 PM
	 */
	@Test
	public void testFrankstonLogistics_MondayOrder2HolidaysBeforeFirstDispatchDay()  {
		List < Logistic > logistics = LogisticsTestData.getFrankstonLogistics();
		prepareMockObjects("06-11-2017T09:30", "Australia/NSW", logistics, Arrays.asList("11-07", "11-08"));

		DeliveryGroupDTO dGroupDTO = 
				logisticsService.calculateOrderDeliveryDateTimes(null);
						//customerKey, OrderType.NON_FORMULATED, null, null);
		SortedSet<DeliveryDateTimeDTO> deliveryResults = dGroupDTO.getDeliveryDateTimes();

		Assert.assertNotNull(deliveryResults);
		SortedSet<DeliveryDateTimeDTO> expectedResults = new TreeSet < DeliveryDateTimeDTO >(
				LogisticsTestData.getDeliveryDateTimes(
						Arrays.asList(
								new Object[]{"10-11-2017T17:00", true, false},
								new Object[]{"15-11-2017T17:00", true, false},
								new Object[]{"16-11-2017T17:00", true, false}
						)));

		Assert.assertNotNull(deliveryResults);
		Assert.assertTrue("The Delivery Results are not as expected",
				areDeliveryResultsEqual(deliveryResults, expectedResults));
	}

	/**
	 * Order Date:  Mon 6 Nov 9:30 AM < 10:00 AM Cutoff1 Day
	 * Compounding Facility: Australia/NSW
	 * Public Holiday on Thu 9 Nov
	 * 
	 * Logistics Details:
	 * Cutoff1 Day/Time: D - 0, 10 AM
	 * Cutoff2 Day/Time: D - 0, 14 PM, QtyLimit: 4
	 * Dispatch Days: MON, TUE, WED, THU
	 * Delivery Day Time: D + 1 - 8 AM

	 * Cutoff1 Day/Time: D - 2, 10 AM
	 * Dispatch Days: SUN
	 * Delivery Day Time: D + 1 - 8 AM

	 * Expected Dispatch Days: TUE, WED, THU, SUN, MON
	 * Expected Delivery Dates: Tue 7 Nov 8:00 AM, Wed 8 Nov 8:00 AM, Thu 9 Nov 8:00 AM, Mon 13 Nov
	 */
	@Test
	public void testBendigoLogistics_BtwCutoff1AndCutoff2Times()  {
		List < Logistic > logistics = LogisticsTestData.getBendigoLogistics();
		prepareMockObjects("06-11-2017T13:30", "Australia/NSW", logistics, Arrays.asList("11-09"));

		DeliveryGroupDTO dGroupDTO = 
				logisticsService.calculateOrderDeliveryDateTimes(null);
						//customerKey, OrderType.NON_FORMULATED, null, 4);
		SortedSet<DeliveryDateTimeDTO> deliveryResults = dGroupDTO.getDeliveryDateTimes();

		Assert.assertNotNull(deliveryResults);
		SortedSet<DeliveryDateTimeDTO> expectedResults = new TreeSet < DeliveryDateTimeDTO >(
				LogisticsTestData.getDeliveryDateTimes(
						Arrays.asList(
								new Object[]{"07-11-2017T08:00", false, false}, 
								new Object[]{"08-11-2017T08:00", true, true}, 
								new Object[]{"09-11-2017T08:00", true, true},
								new Object[]{"13-11-2017T08:00", true, true}
						)));

		Assert.assertNotNull(deliveryResults);
		Assert.assertTrue("The Delivery Results are not as expected",
				areDeliveryResultsEqual(deliveryResults, expectedResults));
	}

	/**
	 * Order Date:  Mon 6 Nov 9:30 AM < 10:00 AM Cutoff1 Day
	 * Compounding Facility: Australia/NSW
	 * Public Holiday on Thu 9 Nov
	 * 
	 * Logistics Details:
	 * Cutoff1 Day/Time: D - 0, 10 AM
	 * Cutoff2 Day/Time: D - 0, 14 PM, QtyLimit: 4
	 * Dispatch Days: MON, TUE, WED, THU
	 * Delivery Day Time: D + 1 - 8 AM

	 * Cutoff1 Day/Time: D - 2, 10 AM
	 * Dispatch Days: SUN
	 * Delivery Day Time: D + 1 - 8 AM

	 * Expected Dispatch Days: TUE, WED, THU, SUN, MON
	 * Expected Delivery Dates: Tue 7 Nov 8:00 AM, Wed 8 Nov 8:00 AM, Thu 9 Nov 8:00 AM, Mon 13 Nov
	 */
	@Test
	public void testBendigoLogistics_BeyondCutoff2Time()  {
		List < Logistic > logistics = LogisticsTestData.getBendigoLogistics();
		prepareMockObjects("06-11-2017T15:30", "Australia/NSW", logistics, Arrays.asList("11-09"));

		DeliveryGroupDTO dGroupDTO = 
				logisticsService.calculateOrderDeliveryDateTimes(null);
						//customerKey, OrderType.NON_FORMULATED, null, 4);
		SortedSet<DeliveryDateTimeDTO> deliveryResults = dGroupDTO.getDeliveryDateTimes();

		Assert.assertNotNull(deliveryResults);
		SortedSet<DeliveryDateTimeDTO> expectedResults = new TreeSet < DeliveryDateTimeDTO >(
				LogisticsTestData.getDeliveryDateTimes(
						Arrays.asList(
								new Object[]{"08-11-2017T08:00", true, false}, 
								new Object[]{"09-11-2017T08:00", true, true},
								new Object[]{"13-11-2017T08:00", true, true}
						)));

		Assert.assertNotNull(deliveryResults);
		Assert.assertTrue("The Delivery Results are not as expected",
				areDeliveryResultsEqual(deliveryResults, expectedResults));
	}

	private void prepareMockObjects(String orderDateTime, String zoneId, List < Logistic > logistics, List < String > holidays) {
		ZonedDateTime zonedDateTime = LogisticsTestData.getZonedDateTime(orderDateTime, zoneId);
		facility.setStateName(zonedDateTime.getZone().getId());
		//CustomerFacility customerFacility = LogisticsTestData.getCustomerFacility(customerKey, facility);

		// Mock classes
		when(facility.getCurrentLocalFacilityTime()).thenReturn(zonedDateTime);
//		when(facility.getHolidayMonthDays()).thenReturn(LogisticsTestData.getHolidays(holidays));
//		when(facilityDAO.findCustomerFacilityByCustomerKey(customerKey)).thenReturn(customerFacility);
		//when(facilityDAO.findCustomerFacilityByCustomerKey(customerKey)).thenReturn(customerFacility);
		when(logisticsDAO.findByCustomerAndOrderTypeAndFacility(customerKey, OrderType.NON_FORMULATED, facility))
		.thenReturn(logistics);
	}
	
	private boolean areDeliveryResultsEqual(SortedSet<DeliveryDateTimeDTO> actualResults,
			SortedSet<DeliveryDateTimeDTO> expectedResults) {
		boolean areResultsEqual = true;
		
		DeliveryDateTimeDTO actualArray[] = actualResults.toArray(new DeliveryDateTimeDTO[actualResults.size()]);
		DeliveryDateTimeDTO expectedArray[] = expectedResults.toArray(new DeliveryDateTimeDTO[expectedResults.size()]);
		if (actualArray.length == expectedArray.length) {
			for (int i = 0; i < actualArray.length; i++) {
				DeliveryDateTimeDTO actual = actualArray[i];
				DeliveryDateTimeDTO expected = expectedArray[i];
				if (!actual.equals(expected)) {
					areResultsEqual = false;
					break;
				}
			}
		}
		
		return areResultsEqual;

	}

	/**
	 * Test getting of Delivery Locations from DAO class and returning a list of DTOs.
	 */
	@Test
	public void testGetDeliveryLocations() {
		Customer dl1 = new Customer();
		dl1.setCusKey("CUST002");
		dl1.setCusName("Bellarat Ward");

		Customer dl2 = new Customer();
		dl2.setCusKey("CUST003");
		dl2.setCusName("Bellarat ER");
//		when(userRepository.getCustomerLocationsByCustomer("CUST001")).thenReturn(Arrays.asList(dl1, dl2));
		
		List < DeliveryLocationDTO > locationDTOs = logisticsService.findDeliveryLocations("CUST001");
		Assert.assertNotNull(locationDTOs);
		Assert.assertTrue(locationDTOs.size() == 2);
		Assert.assertTrue(locationDTOs.get(0).getLocationKey().equals("CUST003"));
	}

	/**
	 * Test getting of Delivery Locations from DAO class and when Delivery locations do not exist.
	 */
	@Test
	public void testGetDeliveryLocationsNoLocationsExist() {
//		when(userRepository.getCustomerLocationsByCustomer("CUST001")).thenReturn(new ArrayList<>());
		
		List < DeliveryLocationDTO > locationDTOs = logisticsService.findDeliveryLocations("CUST001");
		Assert.assertNotNull(locationDTOs);
		Assert.assertTrue(locationDTOs.isEmpty());
	}
}