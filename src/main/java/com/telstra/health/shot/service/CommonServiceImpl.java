package com.telstra.health.shot.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.telstra.health.shot.dao.CommonDAO;
import com.telstra.health.shot.dao.exception.DataAccessException;
import com.telstra.health.shot.dto.HomePageActionsDto;
import com.telstra.health.shot.entity.HomePageActions;
import com.telstra.health.shot.service.exception.ShotServiceException;


@Service
public class CommonServiceImpl implements CommonService{
	
	
	@Autowired
	CommonDAO appRepositor;
	
	
    @Autowired
    private ModelMapper modelMapper;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Multimap<String, String> getAppWebPageAceeses () throws ShotServiceException {
		
		try{
	 		
			Multimap<String, String> map = ArrayListMultimap.create();

			List<Object[]> webPageAceesesList = appRepositor.getAppWebPageAceeses();
			
			for(Object[]  webPageAceeses: webPageAceesesList) {
				map.put(String.valueOf(webPageAceeses[0]), String.valueOf(webPageAceeses[1]));
			}
			
			return map;
			
 		}catch(DataAccessException ex){
 			throw new ShotServiceException(ex.getMessage());
		} 
		
	}
	
	public Multimap < String, String > getURLPermissions() {
		Multimap<String, String> urlPermissionMap = ArrayListMultimap.create();
		List <Object[]> urlRoleAccessList = appRepositor.findURLRoleAccesses();
		urlRoleAccessList.forEach(urlAccess -> {
			urlPermissionMap.put(String.valueOf(urlAccess[0]), String.valueOf(urlAccess[1]));
		});
		return urlPermissionMap;
	}

	@Override
	public List<HomePageActionsDto> getHomePageActions() throws ShotServiceException {
		
		try{
			List<HomePageActions> actionsList = appRepositor.getHomePageActions();
			
			return actionsList.stream()
			          .map(patient -> convertToDto(patient))
			          .collect(Collectors.toList()); 
			
 			
 		}catch(DataAccessException ex){
 			throw new ShotServiceException(ex.getMessage());
		} 
		
	}
	
	private HomePageActionsDto convertToDto(HomePageActions homePageActions) {
		if(null == homePageActions ) return null;
		else{
			HomePageActionsDto homePageActionsDto = modelMapper.map(homePageActions, HomePageActionsDto.class); 
		    return homePageActionsDto;
		}
	}

}
