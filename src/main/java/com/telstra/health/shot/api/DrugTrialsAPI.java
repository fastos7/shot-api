package com.telstra.health.shot.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.telstra.health.shot.api.exception.ApiException;
import com.telstra.health.shot.dto.TrialDTO;
import com.telstra.health.shot.service.TrialsService;
import com.telstra.health.shot.service.exception.ShotServiceException;

@RestController
@RequestMapping("/api/customers/{customerId}/trials")
public class DrugTrialsAPI {
	
	private static final Logger logger = LoggerFactory.getLogger(DrugTrialsAPI.class);
	
	@Autowired
	TrialsService trialsService;
	
	@RequestMapping(value="/searches",
			method=RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<TrialDTO>> searchTrials(@PathVariable("customerId") String customerId,
													   @RequestParam(name="searchStr", required=true) String searchStr	){

		logger.info("Getting trials for customerId [{}] and search string [{}].",customerId,searchStr);
		
		if (customerId == null || customerId.equals("")) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		try {
	
			List<TrialDTO> trialsDTO = this.trialsService.searchTrials(customerId,searchStr);				
			logger.info("Found [{}] trials for customer [{}] and search string [{}].",trialsDTO.size(),searchStr,customerId);
			return new ResponseEntity<List<TrialDTO>>(trialsDTO,HttpStatus.OK);
		} catch (ShotServiceException e) {
			logger.error("Error getting trials for customer [{}] and search string [{}]. Error : {}",customerId,searchStr,e.getMessage());
			throw new ApiException(e.getMessage());
		}

	}
}
