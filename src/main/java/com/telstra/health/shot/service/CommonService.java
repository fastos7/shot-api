package com.telstra.health.shot.service;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Multimap;
import com.telstra.health.shot.dao.exception.DataAccessException;
import com.telstra.health.shot.dto.HomePageActionsDto;
import com.telstra.health.shot.service.exception.ShotServiceException;

public interface CommonService {

	Multimap<String, String> getAppWebPageAceeses() throws ShotServiceException;

	List<HomePageActionsDto> getHomePageActions() throws ShotServiceException;

	Multimap < String, String > getURLPermissions();
}
