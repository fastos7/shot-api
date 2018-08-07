package com.telstra.health.shot.service;

import java.util.List;

import com.telstra.health.shot.dto.TrialDTO;
import com.telstra.health.shot.service.exception.ShotServiceException;

public interface TrialsService {

	List<TrialDTO> searchTrials(String customerId, String searchStr) throws ShotServiceException;

}
