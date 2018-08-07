package com.telstra.health.shot.resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.telstra.health.shot.dto.UserAuthoritiesDTO;
import com.telstra.health.shot.dto.UserDTO;

public class DummyUsers {
	
	public static UserDTO getTestUserDto() {		 
		UserDTO userDto = new UserDTO("deep","fred","deep.joshi@pharmatel.com.au", "defaultSite"); 
		userDto.setUserAuthorities(getMergedAuthorities());
		return userDto;
	}
	
	
	public static List<UserDTO> getTestUserDtoList() {		 
		 List<UserDTO> userDtoList = new ArrayList<>();
		 userDtoList.add(getTestUserDto());
		 return  userDtoList;
 	}
	
	public static Set<UserAuthoritiesDTO> getUserAuthorities(){
		Set<UserAuthoritiesDTO> userAuthorities =  new HashSet<UserAuthoritiesDTO>();
		
		userAuthorities.add(new UserAuthoritiesDTO("customerKey1", "Customer1", "ORDERS"));
		userAuthorities.add(new UserAuthoritiesDTO("customerKey2", "Customer2", "ORDERS_READ_ONLY"));
		userAuthorities.add(new UserAuthoritiesDTO("customerKey2", "Customer2", "REPORTS")); 
		
		return userAuthorities; 
	}
	
	public static List<UserAuthoritiesDTO> getMergedAuthorities(){
		List<UserAuthoritiesDTO> userAuthorities =  new ArrayList<>();
		
		UserAuthoritiesDTO auth1 = new UserAuthoritiesDTO("customerKey1", "Customer1", null);
		auth1.setRoles( new ArrayList<String>(Arrays.asList("ORDERS")));
		
		UserAuthoritiesDTO auth2 = new UserAuthoritiesDTO("customerKey2", "Customer2", null);
		auth2.setRoles( new ArrayList<String>(Arrays.asList("ORDERS_READ_ONLY", "REPORTS")));
		
		userAuthorities.add(auth1);
		userAuthorities.add(auth2); 
		
		return userAuthorities; 
	}

}
