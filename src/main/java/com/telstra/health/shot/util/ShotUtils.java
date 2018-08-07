package com.telstra.health.shot.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;

import com.telstra.health.shot.entity.CustomerPreference;

public class ShotUtils {
	
	static Map<String,String> adErrorCodes;

	private static DateTimeFormatter dateFormatterWithZeroSeconds = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:00");

	private static DateTimeFormatter jsDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	private static DateTimeFormatter utcDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("UTC"));

	private static SimpleDateFormat simpleDateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static Map<String,String>  getAdErrorCodes() {
		if(CollectionUtils.isEmpty(adErrorCodes)) {
			adErrorCodes = new HashMap<>();
			adErrorCodes.put("775", "Your account has been locked for security reasons due to multiple unsuccessful attempts to login. Please contact your SHOT admin.");
			adErrorCodes.put("533", "Your account is locked, Please contact your system administrator.");
			adErrorCodes.put("52e", "Please check the username, password and make sure your account is currently active in order to login."); 
			adErrorCodes.put("68", "User with the provided email already exists in the system. Please contact your Slade Admin.");
		}
		
		return adErrorCodes;
	}
	
	/**
	 * This method is an extension of the <code>StringUtils.isEmpty</code> where it also considers the string "null" as
	 * empty.
	 * 
	 * @author Marlon Cenita
	 *  
	 * @param val
	 * @return
	 */
	public static boolean isEmpty(String val) {				
		return (StringUtils.isEmpty(val) || val.equals("null")); 
	}
	
	/**
	 * This method will determine if the product is a formulation. A formulation product will have a product type 
	 * of either <code>CustomerPreference.ProductType.FORMULATION</> or <code>CustomerPreference.ProductType.CNF_FORMULATION</>.
	 * 
	 * @author Marlon Cenita
	 *  
	 * @param productType
	 * @return true if the product type is formulation.
	 */
	public static boolean isFormulationProductType(String productType) {
		return productType.equals(CustomerPreference.ProductType.FORMULATION) || 
			   productType.equals(CustomerPreference.ProductType.CNF_FORMULATION);
	}
	
	public static String getLoginUserKey() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.getPrincipal() != null) {
			return String.valueOf(auth.getPrincipal());
		}
		return null;
	}
	
	public static String formatDateToJS(TemporalAccessor date, boolean withZeroSeconds) {
		if (withZeroSeconds) {
			return date != null? dateFormatterWithZeroSeconds.toFormat().format(date): null;
		}
		return date != null? jsDateFormatter.toFormat().format(date): null;
	}

	public static String formatDateToJS(Date date) {
		try {
			return simpleDateFormatter.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String truncateString(String str, int maxLength) {
		String truncStr = str;
		if (!StringUtils.isEmpty(truncStr)) {
			if (str.length() > maxLength) {
				truncStr = truncStr.substring(0, maxLength);
			}
		}
		return truncStr;
	}
	
	public static String formatDatetoUTC(ZonedDateTime date) {
//		DateTimeFormatter dateFormatterUTC = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//		date = date.withZoneSameInstant(ZoneId.of("UTC"));
		String utcDateStr = utcDateFormatter.toFormat().format(date);
		return utcDateStr;
	}

	public static ZonedDateTime parseUTCDate(String utcDateStr) {
		return utcDateFormatter.parse(utcDateStr, ZonedDateTime::from);
	}

	public static ZonedDateTime parseJSDate(String jsDateStr, ZoneId zoneId) {
		return jsDateFormatter.withZone(zoneId).parse(jsDateStr, ZonedDateTime::from);
	}
	
	public static LocalDateTime parseJSDate(String jsDateStr) {
		return jsDateFormatter.parse(jsDateStr, LocalDateTime::from);
	}
}
