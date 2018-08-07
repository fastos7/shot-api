package com.telstra.health.shot.common.enums;

import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

public enum AusState {
	NSW, ACT, VIC, SA, TAS, WA, NT, QLD;

	private static Map<String, ZoneId> timeZoneMap = new HashMap<String, ZoneId>() {
		{
			put("NSW", ZoneId.of("Australia/Sydney"));
			put("ACT", ZoneId.of("Australia/ACT"));
			put("VIC", ZoneId.of("Australia/Melbourne"));
			put("SA", ZoneId.of("Australia/Adelaide"));
			put("TAS", ZoneId.of("Australia/Tasmania"));
			put("WA", ZoneId.of("Australia/Perth"));
			put("NT", ZoneId.of("Australia/Darwin"));
			put("QLD", ZoneId.of("Australia/Brisbane"));
		}
	};

	public static ZoneId getTimeZoneByState(String stateName) {
		try {
			return AusState.timeZoneMap.get(stateName);
		} catch (IllegalArgumentException ex) {
			throw new RuntimeException(new StringBuilder("Invalid State Name: ").append(stateName).toString());
		}
	}

	public static ZoneId getTimeZone(AusState ausState) {
		if (ausState != null) {
			return AusState.getTimeZoneByState(ausState.name());
		}
		return null;
	}
	
	public static ZoneId getTimeZonebyValue(String timeZone) {
		try {
			return ZoneId.of(timeZone);
		} catch (Exception ex) {
			throw new RuntimeException(new StringBuilder("Invalid TimeZone: ").append(timeZone).toString());
		}
	}
}
