package com.telstra.health.shot.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.telstra.health.shot.service.exception.ShotServiceException;

import au.com.bytecode.opencsv.CSVReader;

@Component
public class CSVUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(CSVUtil.class);
	
	@Autowired
    private Environment env;
    
	
	public List<String> validateCsvFile(MultipartFile csvFile, String fileTemplate ) throws ShotServiceException{ 
		List<String> invalidRecords = new ArrayList<String>();
		try{
		 
//			File templateFile = new ClassPathResource(fileTemplate).getFile(); 

			ClassPathResource classPathResource = new ClassPathResource(fileTemplate);

			InputStream inputStream = classPathResource.getInputStream();
			File templateFile = File.createTempFile("patientTemplateFile", ".json");
			try {
			    FileUtils.copyInputStreamToFile(inputStream, templateFile);
			} finally {
			    IOUtils.closeQuietly(inputStream);
			}			
			
			ObjectMapper objectMapper = new ObjectMapper(); 
	 		JsonNode rootNode = objectMapper.readTree(templateFile); 
	 		JsonNode columnTemplateNode = rootNode.path("columnTemplate"); 

			if(validateHeader(csvFile, columnTemplateNode)){
				
				 List<String[]> csvData = getCsvData(csvFile);
		 		
				 for(int rec = 0 ; rec< csvData.size() ; rec ++ ){
					 
					 String[] csvRecord = csvData.get(rec);					 
					 
					 List<String> csvColumnList = Arrays.asList(csvRecord); 
 					 String errorMsg = null;
					 
					 for(int col = 0 ; col< csvColumnList.size() ; col ++){						 
						 String csvColumn = csvColumnList.get(col);			
						 JsonNode colNode =  columnTemplateNode.get(col);
						 String columnHeade = colNode.findValue("name").asText();	

						 if( StringUtils.isEmpty(csvColumn)){
							 if(colNode.findValue("required") != null && colNode.findValue("required").asBoolean())								 
 								 errorMsg =  getErrorMsg(errorMsg, String.format(env.getProperty("error.csvutil.required.missing"), columnHeade) , (rec+1)); 
							 
						 }else{
								 
							 if (colNode.findValue("type").asText().equals("DATE")){								 
								 String dateFormat = colNode.findValue("dateFormat").asText();								 
								 SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
								 try{
									 formatter.parse(csvColumn);
								 }catch(ParseException ex){							 
									 String colErrorMsg = String.format(env.getProperty("error.csvutil.invalid.date"), columnHeade, dateFormat);
									 errorMsg =  getErrorMsg(errorMsg, colErrorMsg , (rec+1)); 
								 }
							 }else if (colNode.findValue("type").asText().equals("ENUM")){								 
								 Iterator<JsonNode> elementList = colNode.get("enumValues").elements(); 
								 boolean validType = false; 
								 while(elementList.hasNext()){
									 if(elementList.next().asText().equals(csvColumn)){
										 validType = true; break;
									 }
								 }
								 
								 if(!validType) {
 									 String colErrorMsg = String.format(env.getProperty("error.csvutil.invalid.value"), columnHeade, colNode.get("enumValues")); 
									 errorMsg =  getErrorMsg(errorMsg, colErrorMsg, (rec+1)); 
								 }
							 }else if (colNode.findValue("type").asText().equals("NUMBER")){ 
								 try{
									 Integer.parseInt(csvColumn);
								 }catch(NumberFormatException ex){
 									 errorMsg =  getErrorMsg(errorMsg, String.format(env.getProperty("error.csvutil.invalid.number"), columnHeade) , (rec+1)); 
								 }
							 } 
						 } 
						 
					 }
					 
					 if (StringUtils.isNotEmpty(errorMsg)){
						 invalidRecords.add(errorMsg);
					 }
					 
					 if(invalidRecords.size() > 10)	 break;
					 
				 }
			}
			
		}catch(IOException ex){
			logger.error("Error in Uploading : {}",ex.getMessage(),ex);
  			throw new ShotServiceException(env.getProperty("error.csvutil.read.failed"));
		} 		
		 
		return invalidRecords; 
	}
	
	
 	public boolean validateHeader(MultipartFile csvFile, JsonNode columnTemplateNode ) throws ShotServiceException{ 
 		
		List<String> csvHeaders = getCsvHeader(csvFile);   
 		List<String> missingColumns = new ArrayList<String>();
 		
 		if(!"CSV".equalsIgnoreCase(FilenameUtils.getExtension(csvFile.getOriginalFilename())))
 			throw new ShotServiceException(env.getProperty("error.csvutil.read.failed"));
 	 
 		if(columnTemplateNode.size() == csvHeaders.size()){
 			
 			for(String csvHeader : csvHeaders ){
 	 			int index = csvHeaders.indexOf(csvHeader);
 	 			JsonNode currentNode = columnTemplateNode.get(index);
 	  			if(currentNode.findValue("columnNo").asInt() != (index+1 ) || !csvHeader.equalsIgnoreCase(currentNode.findValue("name").asText()))
 	  				missingColumns.add(csvHeader);
 	 		} 
 			
 			if(CollectionUtils.isEmpty(missingColumns)){
 	 			return true;
 	 		}else{
 	 			throw new ShotServiceException(String.format(env.getProperty("error.csvutil.header.wrong"), missingColumns ));
 	 		}
 		}else{
  			throw new ShotServiceException(String.format(env.getProperty("error.csvutil.header.missing"), columnTemplateNode.findValues("name") ));

 		} 
		
	}
	
	
	public List<String> getCsvHeader(MultipartFile csvFile) throws ShotServiceException{		
		try{
			CSVReader csvReader = new CSVReader(new InputStreamReader(csvFile.getInputStream()));
			String[] header = csvReader.readNext();		
			csvReader.close();		
			return Arrays.asList(header);
		}catch(IOException ex){
  			throw new ShotServiceException(env.getProperty("error.csvutil.read.failed"));
		}
		
		
	}
	
	public List<String[]> getCsvData(MultipartFile csvFile) throws ShotServiceException{		
		try{
			CSVReader csvReader = new CSVReader(new InputStreamReader(csvFile.getInputStream()));		
			List<String[]> csvData = csvReader.readAll();		
			csvReader.close();
			return csvData.subList(1, csvData.size()); 
		}catch(IOException ex){
  			throw new ShotServiceException(env.getProperty("error.csvutil.read.failed"));
		}
		
	}
	
	private String getErrorMsg(String errorMsg, String colErrorMsg, int rec){ 
		
 		 errorMsg = StringUtils.isEmpty(errorMsg) 
 				 		? new StringBuffer(String.format(env.getProperty("error.csvutil.initial.error"), rec)).append(colErrorMsg).toString() 
 				 		: new StringBuffer(errorMsg).append(", ").append(colErrorMsg).toString(); 
		 
		 return errorMsg;

	}
	
 
}
