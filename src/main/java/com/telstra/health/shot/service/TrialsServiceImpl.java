package com.telstra.health.shot.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telstra.health.shot.dao.TrialsDAO;
import com.telstra.health.shot.dto.TrialDTO;
import com.telstra.health.shot.entity.Trial;
import com.telstra.health.shot.service.exception.ShotServiceException;

@Service
public class TrialsServiceImpl implements TrialsService {
	
	private static final Logger logger = LoggerFactory.getLogger(TrialsServiceImpl.class);
	
	@Autowired
	TrialsDAO trialsDAO;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public List<TrialDTO> searchTrials(String customerId, String searchStr)
			throws ShotServiceException {
		
		logger.debug("Getting trials for customerId [{}] and search string [{}].",customerId,searchStr);
		
		List<Trial> trials = this.trialsDAO.searchTrials(customerId,searchStr); 
		
		List<TrialDTO> trialsDTO = trials.stream()
									     .map( trial -> {
									    	if(null == trial ) return null;
											else{
												
												TrialDTO trialDTO 
													= modelMapper.map(trial, TrialDTO.class);
												
											    return trialDTO;
											}
									      }).collect(Collectors.toList());
		return trialsDTO;
	}

}
