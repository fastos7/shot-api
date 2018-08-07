package com.telstra.health.shot.api;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.telstra.health.shot.api.exception.ApiException;
import com.telstra.health.shot.dto.HomePageActionsDto;
import com.telstra.health.shot.service.CommonService;
import com.telstra.health.shot.service.exception.ShotServiceException;

@RestController
@RequestMapping("/api/common/")
public class CommonAPI {
	
	@Autowired
	CommonService commonService;
	
	@RequestMapping(value ="/webpageAccesses/", method = RequestMethod.GET)
 	public @ResponseBody Map < String, Map<String, Collection<String>> > getAppWebPageAceeses () { 
		try {
			Map<String, Collection<String>> webPageAccessMap = commonService.getAppWebPageAceeses().asMap();
			Map<String, Collection<String>> urlRoleAccessMap = commonService.getURLPermissions().asMap();
			
			Map < String, Map<String, Collection<String>> > resultMap = new HashMap<>();
			resultMap.put("webpageAccesses", webPageAccessMap);
			resultMap.put("urlRoleAccesses", urlRoleAccessMap);
			
			return resultMap;

 		} catch (ShotServiceException ex) {
			throw new ApiException(ex.getMessage());
		}
	}
	
	@RequestMapping(value ="/homePageActions/", method = RequestMethod.GET)
 	public @ResponseBody  List<HomePageActionsDto> getHomePageActions() { 
		try {
			return commonService.getHomePageActions();
			
 		} catch (ShotServiceException ex) {
			throw new ApiException(ex.getMessage());
		}
	}

}
