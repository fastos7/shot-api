package com.telstra.health.shot.service.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.telstra.health.shot.common.enums.AusState;
import com.telstra.health.shot.common.enums.OrderType;
import com.telstra.health.shot.dao.CustomerDAO;
import com.telstra.health.shot.dao.FacilityDAO;
import com.telstra.health.shot.dao.LogisticsDAO;
import com.telstra.health.shot.dto.DeliveryDateTimeDTO;
import com.telstra.health.shot.dto.DeliveryGroupDTO;
import com.telstra.health.shot.dto.DeliveryLocationDTO;
import com.telstra.health.shot.dto.DeliveryRunRequestDTO;
import com.telstra.health.shot.dto.DeliveryRunRequestDTO.DeliveryRunQuantityDTO;
import com.telstra.health.shot.entity.Customer;
import com.telstra.health.shot.entity.Logistic;
import com.telstra.health.shot.entity.reference.Facility;
import com.telstra.health.shot.entity.reference.FacilityHoliday;
import com.telstra.health.shot.service.LogisticsService;
import com.telstra.health.shot.util.ShotUtils;

@Service("logisticsService")
@Transactional
public class LogisticsServiceImpl implements LogisticsService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private LogisticsDAO logisticsDAO;

	@Autowired
	private CustomerDAO customerRepository;
	
	@Autowired
	private FacilityDAO facilityDAO;

	@Value("${delivery.datetime.maxDays}")
	private Integer maxDays;
	
	@Value("${delivery.run.is_restricted.display}")
	private Boolean displayIsRestrictedFlag;

	/**
	 * Calulcates 90 days of allowed Delivery Runs (Date Times) for the given 
	 * Customer, Type of Order, Entity  and Quantity represented in requestDTO.
	 * @param deliveryRunRequestDTO
	 * @return DeliveryGroupDTO instance containing list of Delivery Runs.
	 */
	public DeliveryGroupDTO calculateOrderDeliveryDateTimes(DeliveryRunRequestDTO requestDTO) {
		DeliveryGroupDTO deliveryDateGroup = new DeliveryGroupDTO();
		deliveryDateGroup.setDisplayIsRestrictedFlag(this.displayIsRestrictedFlag);
		SortedSet<DeliveryDateTimeDTO> deliveryDateTimes = new TreeSet<>();

		requestDTO.setDeliveryRunQtyMap(this.deriveDeliveryRunQtyMap(requestDTO.getDeliveryRunQuantites(), requestDTO.getQuantity()));

		// Get Customer instance
		Customer customer = customerRepository.findCustomer(requestDTO.getCustomerKey());

		// Get Manufacturing Facility instance
		Facility facility = facilityDAO.getOne(requestDTO.getEntityKey());

		// Get Facility local date and time
		ZonedDateTime orderDateTime = facility.getCurrentLocalFacilityTime();
		OrderDate orderDateObj = new OrderDate(orderDateTime);

		// Get Facility Holidays
		List < MonthDay > holidays = this.getHolidayDaysByRange(facility, orderDateTime);

		// Get Order Logistics reference data
		List<Logistic> logistics = logisticsDAO.findByCustomerAndOrderTypeAndFacility(
				requestDTO.getCustomerKey(), OrderType.valueOf(requestDTO.getOrderType()), facility);

		logger.info("Calculating Delivery Runs for Customer: {}, Order Type: {}, Quantity: {}, Entity: {}",
				requestDTO.getCustomerKey(), requestDTO.getOrderType(), requestDTO.getQuantity(),
				requestDTO.getEntityKey());
		if (!CollectionUtils.isEmpty(logistics)) {
			// Calculate weekly delivery date times for upto maxDays
			for (int i = 0; i < this.maxDays; i += 7) {

				// For all logistics configured for the given Customer, Order type and Facility
				// get the list of possible delivery dates and times
				deliveryDateTimes.addAll(getAllWeeklyDeliveryDateTimes(orderDateObj, orderDateTime,
						logistics, requestDTO, AusState.getTimeZonebyValue(customer.getTimeZone()), holidays, (i < 7)));

				// Forward Order Date time by 7 days
				orderDateTime = ZonedDateTime.of(orderDateTime.plusDays(7).toLocalDate(), LocalTime.MIDNIGHT,
						orderDateTime.getZone());
			}
		}
		logger.info("Delivery Runs Calculated for Customer: {}, Order Type: {}, Quantity: {}, Entity: {}",
				requestDTO.getCustomerKey(), requestDTO.getOrderType(), requestDTO.getQuantity(),
				requestDTO.getEntityKey());
		
		// Prepare the result DTO to return
		deliveryDateGroup.setFacilityName(facility.getName());
		deliveryDateGroup.setFacilityContactNo(facility.getPhoneNo());
		deliveryDateGroup.setDeliveryDateTimes(deliveryDateTimes);
		return deliveryDateGroup;
	}

	/**
	 * Finds list of Delivery Locations for the given Customer
	 * @param customerKey Represents the Customer's Key
	 * @return List of DeliveryLocation DTOs.
	 */
	public List < DeliveryLocationDTO > findDeliveryLocations(String customerKey) {
		List < DeliveryLocationDTO > locationDTOs = new ArrayList<>();
		List < Customer > cusLocations = this.customerRepository.getCustomerLocationsByCustomer(customerKey);
		if (!CollectionUtils.isEmpty(cusLocations)) {
			logger.info("{} Delivery Locations found for Customer: {}", cusLocations.size(), customerKey);
			
			// Create DTO instance for each Delivery Location.
			cusLocations.forEach(customer -> {
				locationDTOs.add(
						new DeliveryLocationDTO(customer.getCusKey(), customer.getCusName()));
				
			});
			locationDTOs.sort((DeliveryLocationDTO dto1, DeliveryLocationDTO dto2) -> 
				dto1.getLocationName().compareTo(dto2.getLocationName())
			);
		} else {
			logger.info("N Delivery Locations found for Customer: {}", customerKey);
		}
		return locationDTOs;
	}

	/**
	 * Get Delivery Runs for a week based on the Order Date Time, Logistics and other information.
	 * @param originalOrderDate The original Order Date Time.
	 * @param orderDateTime The current Order Date Time.
	 * @param logistics List of Logistics configuration.
	 * @param requestDTO Teh Delivery Run request.
	 * @param customerTimeZone Customer's Timezone
	 * @param holidays List of configured holidays.
	 * @param isFirstWeek Flag that indicates Delivery Runs are calculated for the 1st week.
	 * @return Collection of Delivery Runs for a week.
	 */
	private SortedSet<DeliveryDateTimeDTO> getAllWeeklyDeliveryDateTimes(
			OrderDate originalOrderDate, ZonedDateTime orderDateTime, 
			final List<Logistic> logistics, DeliveryRunRequestDTO requestDTO, ZoneId customerTimeZone,
			List < MonthDay > holidays, final boolean isFirstWeek) {
		SortedSet<DeliveryDateTimeDTO> deliveryDateTimes = new TreeSet<>();
		ZonedDateTime tempOrderDateTime = null;
	
		// Calculate a week's Delivery Runs for each Logistic instance.
		for (Logistic logistic : logistics) {
			tempOrderDateTime = orderDateTime;
			ZonedDateTime dispatchDate = getFirstDispatchDate(tempOrderDateTime, logistic, requestDTO, holidays, isFirstWeek);
			originalOrderDate.reset();
			originalOrderDate.addIncentiveDay(logistic.getIncentiveDayOffset());
			DeliveryDateTimeDTO deliveryDateTimeVO = new DeliveryDateTimeDTO();

			// Keep incrementing the order date by 1 day until dispatch date time gets validated
			while (!validateDispatchDate(tempOrderDateTime, originalOrderDate, dispatchDate, logistic,
					requestDTO, deliveryDateTimeVO, holidays)) {

				tempOrderDateTime = tempOrderDateTime.plusDays(1).with(LocalTime.MIDNIGHT);

				// Calculate Dispatch Day: Order Day + Cutoff1DayOffset
				dispatchDate = dispatchDate.plusDays(1);
			}

			// Calculate customer specific Delivery Date Time
			ZonedDateTime deliveryDateTime = this.calculateDeliveryDateTimeByTimeZone(dispatchDate, logistic,
					customerTimeZone);
			ZonedDateTime dispatchDateTime = dispatchDate;
			deliveryDateTimeVO.setDispatchDateTime(dispatchDateTime);
			deliveryDateTimeVO.setLocalDateTime(deliveryDateTime);
			deliveryDateTimes.add(deliveryDateTimeVO);

			// Calculate more possible delivery date times for the week
			int dispatchDayValue = dispatchDate.getDayOfWeek().getValue();
			for (DayOfWeek dayOfWeek : logistic.getDispatchDays()) {

				int dateComparisonValue = dayOfWeek.compareTo(dispatchDate.getDayOfWeek());

				if (dateComparisonValue != 0) {
					ZonedDateTime futureDispatchDate = null;

					// For days later than the dispatch day
					if (dateComparisonValue > 0) {
						futureDispatchDate = addDays(dispatchDate, (dayOfWeek.getValue() - dispatchDayValue));

					} else { // For all days before the dispatch day, but in next week
						futureDispatchDate = addDays(dispatchDate, (7 - (dispatchDayValue - dayOfWeek.getValue())));
					}

					// If this future dispatch date falls on a Public Holiday then skip that dispatch date
					if (this.isDateOnPublicHoliday(futureDispatchDate, holidays)) {
						continue;
					}

					deliveryDateTime = this.calculateDeliveryDateTimeByTimeZone(futureDispatchDate, logistic,
							customerTimeZone);
					dispatchDateTime = futureDispatchDate;

					deliveryDateTimes.add(new DeliveryDateTimeDTO(deliveryDateTime, dispatchDateTime, true,
							isWithinIncentiveCutoffTime(originalOrderDate, futureDispatchDate, logistic.getIncentiveDayOffset(),
									logistic.getIncentiveTimeOffset(), holidays)));
				}
			}
		}
		return deliveryDateTimes;
	}

	/**
	 * Perform validation whether Dispatch Date and time falls within Logistics
	 * Dispatch Days and Cutoff times
	 * @param orderDateTime
	 * @param dispatchDate
	 * @param logistic
	 * @return
	 */
	private boolean validateDispatchDate(final ZonedDateTime orderDateTime, OrderDate originalOrderDate,
			final ZonedDateTime dispatchDate, final Logistic logistic, DeliveryRunRequestDTO requestDTO, 
			DeliveryDateTimeDTO ddtVO, final List < MonthDay > holidays) {
		boolean isDispatchDateConfirmed = false;
		boolean isWithinCutoff1Time = false;

		// Check if Dispatch day falls in the Logistics dispatch days range
		if (logistic.isDispatchDay(dispatchDate)) {

			// Check if order day hour is within the cutoff1 time
			if (this.isWithinCutoff1Time(orderDateTime, dispatchDate, logistic)) {
				isWithinCutoff1Time = true;
				isDispatchDateConfirmed = true;
			} else {
				// Check if order day hour is between the cutoff1 day-time and cutoff2 day-time 
				if (this.isWithinCutoff2Time(orderDateTime, dispatchDate, logistic, requestDTO)) {
					if (this.isWithinCutoff2Qty(orderDateTime, dispatchDate, logistic, requestDTO)) {
						isWithinCutoff1Time = false;
						isDispatchDateConfirmed = true;
					}
				}
			}

			// If Dispatch Date Time could not be confirmed due to invalid Cutoff1/2 times
			// then find the new Order Date
			if (!isDispatchDateConfirmed) {
				originalOrderDate.incrementOrderDate(1);
			} else {
				// If the dispatch date is not on public holiday
				// then check if Dispatch Date can be confirmed based on 
				// holidays in between original date and dispatch date
				if (!this.isDateOnPublicHoliday(dispatchDate, holidays)) {
					// TODO This code for Future uses.
					//isDispatchDateConfirmed = this.validateHolidaysRange(logistic.getCutOff1DayOffset(),
							//originalOrderDate.orderDateTime.toLocalDate(), dispatchDate, holidays);
				} else {
					isDispatchDateConfirmed = false;
				}
				
				if (isDispatchDateConfirmed) {
					ddtVO.setWithinCutOff1Time(isWithinCutoff1Time);

					// Check if Incentive applies and set incentive flag in DTO
					ddtVO.setWithinIncentiveCutoffTime(isWithinIncentiveCutoffTime(originalOrderDate, dispatchDate,
							logistic.getIncentiveDayOffset(), logistic.getIncentiveTimeOffset(), holidays));
				}
			}
		}
		return isDispatchDateConfirmed;
	}

	/**
	 * Validate for the number of available working days from actual order date to dispatch date based on Holidays.
	 * @param advOrderDaysReq
	 * @param originalOrderDate
	 * @param dispatchDate
	 * @param holidays
	 * @return true if no. of working days available in the date range is equal or greater than the adv days required.
	 */
	private boolean validateHolidaysRange(int advOrderDaysReq, ZonedDateTime originalOrderDate, ZonedDateTime dispatchDate,
			final List<MonthDay> holidays) {

		// Get difference in days: dispatch Date - original order date
		int daysDiff = (int)ChronoUnit.DAYS.between(originalOrderDate, dispatchDate);
		
		// Find number of holidays within this date range: from original order to dispatch date
		int holidayCount = this.getHolidayCountInRange(originalOrderDate, dispatchDate, holidays);
		
		if (holidayCount > 0) {
			// Find no. of working days
			int noOfWorkingDays = daysDiff - holidayCount;
			
			// Return true if no. of working days available 
			// in the date range is equal or greater than the adv days required.
			return noOfWorkingDays >= advOrderDaysReq;
		} else {
			return true;
		}
	}

	/**
	 * Check if order day hour is within the cutoff1 time
	 * @param orderDateTime
	 * @param dispatchDate
	 * @param logistic
	 * @return
	 */
	protected boolean isWithinCutoff1Time(final ZonedDateTime orderDateTime, final ZonedDateTime dispatchDate,
			final Logistic logistic) {
		ZonedDateTime cutoff1DateTime = dispatchDate.minusDays(logistic.getCutOff1DayOffset())
				.with(LocalTime.of(logistic.getCutOff1TimeOffset(), 0));
		return orderDateTime.isBefore(cutoff1DateTime);
	}

	/**
 	 * Check if order day hour is between the cutoff1 and cutoff2 time
	 * @param orderDateTime Date Time of ordering
	 * @param dispatchDate Date when the order will be dispatched
	 * @param logistic Logistic configuration instance being used for DeliveryRun calculation.
	 * @param requestDTO DTO of the Logistics request
	 * @return true if order day hour is between the cutoff1 and cutoff2 time, else false.
	 */
	protected boolean isWithinCutoff2Time(final ZonedDateTime orderDateTime, final ZonedDateTime dispatchDate,
			final Logistic logistic, DeliveryRunRequestDTO requestDTO) {
		
		// Calculate Cuttoff1 and Cuttoff2 date times from the dispatch date.
		ZonedDateTime cutOff1DateTime = dispatchDate.minusDays(logistic.getCutOff1DayOffset())
				.with(LocalTime.of(logistic.getCutOff1TimeOffset(), 0));
		ZonedDateTime cutOff2DateTime = dispatchDate.minusDays(logistic.getCutOff2DayOffset())
				.with(LocalTime.of(logistic.getCutOff2TimeOffset(), 0));

		// If order date is between the cutoff1 and cutoff2 date times
		return orderDateTime.isAfter(cutOff1DateTime) && orderDateTime.isBefore(cutOff2DateTime);
	}

	/**
	 * Check if the Batch Quantity is within Logistics configured Quantity threshold.
	 * @param orderDateTime Current Order Date Time.
	 * @param dispatchDate Dispatch Date Time of the Batch.
	 * @param logistic Logistic configuration instance being used for DeliveryRun calculation.
	 * @param requestDTO DTO of the Logistics request
	 * @return true if Batch Quantity is within Logistics configured Quantity threshold, else false.
	 */
	protected boolean isWithinCutoff2Qty(final ZonedDateTime orderDateTime, final ZonedDateTime dispatchDate,
			final Logistic logistic, DeliveryRunRequestDTO requestDTO) {
		// Check if total Batches Quantity is within configured Quantity threshold
		Integer totalOrderQty = requestDTO.getQuantity();	// Quantity of Batch
		
		// Get and add total quantity of Batch based on Delivery Date Time
		Map < String, Integer > deliveryRunQtyMap = requestDTO.getDeliveryRunQtyMap();
		if (deliveryRunQtyMap != null) {
			String dispatchDateStr = ShotUtils.formatDateToJS(dispatchDate, true);
			Integer deliveryQty = deliveryRunQtyMap.get(dispatchDateStr);
			totalOrderQty += (deliveryQty != null && deliveryQty != 0 ? (deliveryQty - requestDTO.getQuantity()) : 0);
		}
		
		// Get and add total quantity of all existing Batches in Pre-Production/Production states
		Integer savedBatchQty = this.logisticsDAO.findDeliveryRunQty(
				requestDTO.getCustomerKey(), requestDTO.getEntityKey(), Date.from(dispatchDate.toInstant()));
		totalOrderQty = totalOrderQty + (savedBatchQty != null? savedBatchQty : 0);

		// Check if total Batches Quantity is within configured Quantity threshold
		if (totalOrderQty <= logistic.getCutOff2QuantityLimit()) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if the given date falls on a public holiday.
	 * @param date The given date to check.
	 * @param holidays List of holidays.
	 * @return true if the given date falls on a public holiday, else false.
	 */
	protected boolean isDateOnPublicHoliday(ZonedDateTime date, List<MonthDay> holidays) {
		return holidays.contains(MonthDay.of(date.getMonth(), date.getDayOfMonth()));
	}

	/**
	 * Utility method to add the given days to the specified local date.
	 * @param localDate date instance to which days need to be added.
	 * @param days 
	 * @return New ZonedDateTime with the given days added.
	 */
	private ZonedDateTime addDays(ZonedDateTime localDate, int days) {
		return localDate.plusDays(days);
	}

	public void setMaxDays(Integer maxDays) {
		this.maxDays = maxDays;
	}
	
	/**
	 * Validates if incentive applies for the given dispatch date
	 * @param origOrderDate
	 * @param dispatchDateTime
	 * @param incentiveCutoffTime
	 * @return true if Incentive applies to the given dispatch date.
	 */
	protected boolean isWithinIncentiveCutoffTime(OrderDate origOrderDate, ZonedDateTime dispatchDateTime,
			Integer incentiveCutoffOffset, Integer incentiveCutoffTime, final List<MonthDay> holidays) {
		boolean incentiveApplies = false;

		// Check if Incentive applies
		if (origOrderDate.incentiveDispatchDate != null) {
			LocalDate dispatchDate = dispatchDateTime.toLocalDate();
			if (origOrderDate.incentiveDispatchDate.isBefore(dispatchDate)) {
				incentiveApplies = true;

			} else if (origOrderDate.incentiveDispatchDate.isEqual(dispatchDate)) {
				if (origOrderDate.originalOrderDateTime.getHour() < incentiveCutoffTime) {
					incentiveApplies = true;
				}
			}
			if (incentiveApplies) {
//				incentiveApplies = this.validateHolidaysRange(incentiveCutoffOffset,
//						origOrderDate.originalOrderDateTime.toLocalDate(), dispatchDate, holidays);
			}
		}
		return incentiveApplies;
	}

	/**
	 * Get the first dispatch date to start calculations from.
	 * @param orderDateTime Current Order Date Time.
	 * @param logistic Logistic configuration instance being used for DeliveryRun calculation.
	 * @param requestDTO DTO of the Logistics request
	 * @param holidays
	 * @param isFirstWeek
	 * @return Dispatch Date Time.
	 */
	private ZonedDateTime getFirstDispatchDate(ZonedDateTime orderDateTime, Logistic logistic, DeliveryRunRequestDTO requestDTO,
			final List<MonthDay> holidays, boolean isFirstWeek) {
		if (isFirstWeek) {
			ZonedDateTime dispatchDate2 = orderDateTime.plusDays(logistic.getCutOff2DayOffset()).with(LocalTime.of(logistic.getDispatchTime(), 0));
			if (logistic.isDispatchDay(dispatchDate2) && !this.isDateOnPublicHoliday(dispatchDate2, holidays)) {
				if (logistic.getCutOff1DayOffset() > logistic.getCutOff2DayOffset()) {
					if (this.isWithinCutoff2Time(orderDateTime, dispatchDate2, logistic, requestDTO)) {
						if (this.isWithinCutoff2Qty(orderDateTime, dispatchDate2, logistic, requestDTO)) {
							return dispatchDate2;
						}
					}
				}
			}
		}
		return orderDateTime.plusDays(logistic.getCutOff1DayOffset()).with(LocalTime.of(logistic.getDispatchTime(), 0));
	}

	private int getHolidayCountInRange(ZonedDateTime fromDateTime, ZonedDateTime toDateTime, final List<MonthDay> holidays) {
		int holidayCount = 0;
		ZonedDateTime date = fromDateTime;
		while (date.toLocalDate().isBefore(toDateTime.toLocalDate())) {
			if (this.isDateOnPublicHoliday(date, holidays)) {
				holidayCount++;
			}
			date = date.plusDays(1);
		}
		return holidayCount;
	}

	/**
	 * Convert the timezone of the given Date Time instance by keeping the Instant constant.
	 * @param dateTime
	 * @param timeZone
	 * @return New Date Time with the given Timezone.
	 */
	private ZonedDateTime toDateTimeByZone(ZonedDateTime dateTime, ZoneId timeZone) {
		return dateTime.withZoneSameInstant(timeZone);
	}
	
	/**
	 * Convert the given list of Delivery Run Qty instances to Map < Dispatch Time , Quantity >
	 * @param deliveryRunQuantityDTOs
	 * @param qty
	 * @return Converted Map instance of Delivery Run Qty instances.
	 */
	protected Map < String, Integer > deriveDeliveryRunQtyMap(List < DeliveryRunQuantityDTO > deliveryRunQuantityDTOs, Integer qty) {
		if (deliveryRunQuantityDTOs != null) {
			return deliveryRunQuantityDTOs.stream()
					.collect(Collectors.toMap(
							dto -> dto.getDispatchDateTime(), dto -> (dto.getTotalQuantity() + qty)));
		}
		return null;
	}

	/**
	 * Get list of Holidays from the current date time and relative to the Compounding Facility.
	 * @param facility
	 * @param fromDateTime
	 * @return List of Holidays applicable to the Compounding Facility.
	 */
	private List < MonthDay > getHolidayDaysByRange(Facility facility, ZonedDateTime fromDateTime) {
		Date fromOrderDate = facility.getCurrentLocalFacilityDate(fromDateTime);
		List < FacilityHoliday > facHolidays =
				facilityDAO.findFacilityHolidays(
						facility, fromOrderDate, new Date(fromOrderDate.getTime() + (maxDays*24L*3600000L)));
		return FacilityHoliday.getMonthDays(facHolidays);
	}

	/**
	 * Utility method to convert the Delivery Date Time from Dispatch Date and relative to Customer's Time zone.
	 * @param dispatchDate
	 * @param logistic
	 * @param timeZone
	 * @return
	 */
	private ZonedDateTime calculateDeliveryDateTimeByTimeZone(ZonedDateTime dispatchDate, Logistic logistic, ZoneId timeZone) {
		return this.toDateTimeByZone(addDays(dispatchDate, logistic.getDeliveryDayOffset()), timeZone)
				.with(LocalTime.of(logistic.getDeliveryTime(), 0));
	}

	protected class OrderDate {
		private final ZonedDateTime originalOrderDateTime;
		private ZonedDateTime orderDateTime;
		private LocalDate incentiveDispatchDate; // original order date + incentive cutoff day
		
		public OrderDate(ZonedDateTime orderDateTime) {
			this.originalOrderDateTime = orderDateTime;
			this.orderDateTime = orderDateTime;
		}
		
		public void addIncentiveDay(Integer incentiveCutOffDay) {
			if (incentiveCutOffDay != null) {
				this.incentiveDispatchDate = originalOrderDateTime.plusDays(incentiveCutOffDay).toLocalDate();
			} else {
				this.incentiveDispatchDate = null;
			}
		}
		
		public void incrementOrderDate(int days) {
			if (orderDateTime != null) {
				this.orderDateTime = orderDateTime.plusDays(days).with(LocalTime.MIDNIGHT);
			}
		}
		
		public void reset() {
			this.orderDateTime = this.originalOrderDateTime;
			this.incentiveDispatchDate = null;
		}
	}
}
