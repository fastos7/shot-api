package com.telstra.health.shot.entity.converter;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.persistence.AttributeConverter;

/**
 * This is used to convert any DB data which are represented in <code>Timestamp
 * </code> datatype to be converted to String data type. This is needed so that 
 * we can avoid to use the @jsonFormat annotation in ModelMapper which format 
 * timestamp datatype in UTC timezone.
 *   
 * @author Marlon Cenita
 *
 */
public class TimestampToStringConverter implements AttributeConverter<String, Timestamp> {
	
	private SimpleDateFormat simpleDateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
	@Override
	public Timestamp convertToDatabaseColumn(String attribute) {
		/*
		 * As of this writing there is no requirement to convert the DTO 
		 */
		return null;
	}

	@Override
	public String convertToEntityAttribute(Timestamp dbData) {
		if (dbData != null) {			
			return simpleDateFormatter.format(dbData);
		}
		return null;
	}


}
