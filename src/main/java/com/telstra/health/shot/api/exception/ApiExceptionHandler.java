package com.telstra.health.shot.api.exception;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.telstra.health.shot.service.exception.ShotServiceException;

@RestControllerAdvice
public class ApiExceptionHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);
	
	@Autowired
	public Environment env;

    @ExceptionHandler(value = { NoHandlerFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiExcepionMsg NoHandlerFoundException(Exception ex) {
    	logger.error(ex.getCause().toString());
        return new ApiExcepionMsg(HttpStatus.NOT_FOUND, "Resource Not Found");
    }

    @ExceptionHandler(value = { ApiException.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<APIError> ServiceException(ApiException ex) {
    	
// BSU - To add specific error codes for exception handling - Start Comment
    	
//    	if(StringUtils.isNotEmpty(ex.getErrorMessage())) {
//    		logger.error(ex.getCause().toString());
//    		return new ApiExcepionMsg(HttpStatus.INTERNAL_SERVER_ERROR, ex.getErrorMessage());
//    	}
//    	else if ( !CollectionUtils.isEmpty(ex.getErrorMessages()))
//    		return new ApiExcepionMsg(HttpStatus.INTERNAL_SERVER_ERROR, ex.getErrorMessages());
    	
		return new ResponseEntity<>(new APIError(ex.getErrorCode(), ex.getErrorMessages(), HttpStatus.INTERNAL_SERVER_ERROR.value()),HttpStatus.INTERNAL_SERVER_ERROR);

// BSU - To add specific error codes for exception handling - End Comment
    	
     } 
    
	@ExceptionHandler(value = { ShotServiceException.class })
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<APIError> ServiceException(ShotServiceException ex) {

		if (ex.getMessageKey() != null) {
			return new ResponseEntity<>(new APIError(ex.getErrorCode(), Arrays.asList(env.getProperty(ex.getMessageKey())), HttpStatus.INTERNAL_SERVER_ERROR.value()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			return new ResponseEntity<>(new APIError(ex.getErrorCode(), Arrays.asList(ex.getMessageKey()), HttpStatus.INTERNAL_SERVER_ERROR.value()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

    @ExceptionHandler(value = { UserAPIException.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiExcepionMsg UserException(UserAPIException ex) {

    	if(StringUtils.isNotEmpty(ex.getMessage())) {
    		logger.error(ex.getCause().toString());
    		return new ApiExcepionMsg(ex.getStatus(), ex.getMessage());
    	}
    	
		return null;
    }

}
