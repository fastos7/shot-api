package com.telstra.health.shot.dao;

import java.util.List;

import com.telstra.health.shot.entity.Trial;
import com.telstra.health.shot.service.exception.ShotServiceException;

public interface TrialsDAOCustom {
	
	List<Trial> searchTrials(String customerId, String searchStr) throws ShotServiceException;
	
}
