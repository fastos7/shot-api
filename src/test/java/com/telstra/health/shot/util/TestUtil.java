package com.telstra.health.shot.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtil {
	
	public static String formatTimestampInUTC(Timestamp timestamp) {
		
		SimpleDateFormat datetimeFormatter =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		datetimeFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		
		return datetimeFormatter.format(timestamp);
	}
	
	public static String asJsonString(Object o) {
		ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(o);
            return json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
	}
}
