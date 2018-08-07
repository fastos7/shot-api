package com.telstra.health.shot.entity;

import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.telstra.health.shot.common.enums.OrderType;
import com.telstra.health.shot.entity.reference.Facility;

@Entity
@Table(name = "SHOT_REF_LOGISTIC")
public class Logistic {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "LOGISTIC_ID")
	private Long id;
	
//	@ManyToOne
//	private Customer customer;
	@Column(name = "CUSTOMER_KEY")
	private String customer;	 

	@Enumerated
	@Column(name = "ORDER_TYPE_ID")
	private OrderType orderType;

	@ManyToOne
	@JoinColumn(name = "ENT_KEY")
	private Facility facility;

	// Relative to Dispatch Day
	@Column(name = "INCENTIVE_DAY_OFFSET")
	private Integer incentiveDayOffset;
	
	// Relative to Dispatch Day
	@Column(name = "INCENTIVE_TIME_OFFSET")
	private Integer incentiveTimeOffset;

	// Relative to Dispatch Day
	@Column(name = "CUTOFF1_DAY_OFFSET")
	private int cutOff1DayOffset;
	
	// Relative to Dispatch Day
	@Column(name = "CUTOFF1_TIME_OFFSET")
	private int cutOff1TimeOffset;
	
	// Relative to Dispatch Day
	@Column(name = "CUTOFF2_DAY_OFFSET")
	private int cutOff2DayOffset;
	
	// Relative to Dispatch Day
	@Column(name = "CUTOFF2_TIME_OFFSET")
	private int cutOff2TimeOffset;
	
	@Column(name = "CUTOFF2_QTY_LIMIT")
	private int cutOff2QuantityLimit;

	@Column(name = "DISPATCH_TIME")
	private int dispatchTime;
	
	// Relative to Dispatch Day
	@Column(name = "DELIVERY_DAY_OFFSET")
	private int deliveryDayOffset;
	
	// Customer local delivery time 
	@Column(name = "DELIVERY_TIME")
	private int deliveryTime;

	@Column(name = "SELECTED_DAYS_OF_THE_WEEK")
	private String dispatchDaysList;

	@Column(name = "COURIER")
	private String courier;

//	@ElementCollection(targetClass = DayOfWeek.class)
//	@JoinTable(
//		    name="SELECTED_DAYS_OF_THE_WEEK",
//		    joinColumns=@JoinColumn(name="LOGISTIC_ID")
//		)
//	@Enumerated(EnumType.STRING)
	@Transient
	private SortedSet< DayOfWeek > dispatchDays;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	public Facility getFacility() {
		return facility;
	}

	public void setFacility(Facility facility) {
		this.facility = facility;
	}

	public int getCutOff1DayOffset() {
		return cutOff1DayOffset;
	}

	public void setCutOff1DayOffset(int cutOff1DayOffset) {
		this.cutOff1DayOffset = cutOff1DayOffset;
	}

	public int getCutOff1TimeOffset() {
		return cutOff1TimeOffset;
	}

	public void setCutOff1TimeOffset(int cutOff1TimeOffset) {
		this.cutOff1TimeOffset = cutOff1TimeOffset;
	}

	public int getCutOff2DayOffset() {
		return cutOff2DayOffset;
	}

	public void setCutOff2DayOffset(int cutOff2DayOffset) {
		this.cutOff2DayOffset = cutOff2DayOffset;
	}

	public int getCutOff2TimeOffset() {
		return cutOff2TimeOffset;
	}

	public void setCutOff2TimeOffset(int cutOff2TimeOffset) {
		this.cutOff2TimeOffset = cutOff2TimeOffset;
	}

	public int getDispatchTime() {
		return dispatchTime;
	}

	public void setDispatchTime(int dispatchTime) {
		this.dispatchTime = dispatchTime;
	}

	public int getDeliveryDayOffset() {
		return deliveryDayOffset;
	}

	public void setDeliveryDayOffset(int deliveryDayOffset) {
		this.deliveryDayOffset = deliveryDayOffset;
	}

	public int getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(int deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public SortedSet<DayOfWeek> getDispatchDays() {
		if (this.dispatchDays == null) {
			this.dispatchDays = new TreeSet<>();
			StringTokenizer tokenizer = new StringTokenizer(this.dispatchDaysList, ",");
			while (tokenizer.hasMoreTokens()) {
				this.dispatchDays.add(DayOfWeek.valueOf(tokenizer.nextToken()));
			}
		}
		return dispatchDays;
	}

	public void setDispatchDays(SortedSet<DayOfWeek> dispatchDays) {
		this.dispatchDays = dispatchDays;
	}

	public int getCutOff2QuantityLimit() {
		return cutOff2QuantityLimit;
	}

	public void setCutOff2QuantityLimit(int cutOff2QuantityLimit) {
		this.cutOff2QuantityLimit = cutOff2QuantityLimit;
	}

	public String getDispatchDaysList() {
		return dispatchDaysList;
	}

	public void setDispatchDaysList(String dispatchDaysList) {
		this.dispatchDaysList = dispatchDaysList;
	}

	public Integer getIncentiveDayOffset() {
		return incentiveDayOffset;
	}

	public void setIncentiveDayOffset(Integer incentiveDayOffset) {
		this.incentiveDayOffset = incentiveDayOffset;
	}

	public Integer getIncentiveTimeOffset() {
		return incentiveTimeOffset;
	}

	public void setIncentiveTimeOffset(Integer incentiveTimeOffset) {
		this.incentiveTimeOffset = incentiveTimeOffset;
	}

	public String getCourier() {
		return courier;
	}

	public void setCourier(String courier) {
		this.courier = courier;
	}

	public boolean isDispatchDay(ZonedDateTime dispatchDate) {
		return this.getDispatchDays().contains(dispatchDate.getDayOfWeek());
	}
}
