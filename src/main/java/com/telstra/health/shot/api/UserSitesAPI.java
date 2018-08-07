package com.telstra.health.shot.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.telstra.health.shot.api.exception.ApiException;
import com.telstra.health.shot.api.exception.ApiExceptionHandler;
import com.telstra.health.shot.common.enums.UserRole;
import com.telstra.health.shot.dto.UserAuthoritiesDTO;
import com.telstra.health.shot.dto.UserDTO;
import com.telstra.health.shot.dto.UserRoleDTO;
import com.telstra.health.shot.service.UserService;

@RestController
@RequestMapping("/api/users/{userId}/sites/")
public class UserSitesAPI {
	private final Logger logger = LoggerFactory.getLogger(UserSitesAPI.class);
	
	@Autowired
	private UserService userService;
	
	@SuppressWarnings("serial")
	@GetMapping("/")
	@ResponseStatus(HttpStatus.OK)
	public Map < String, Object > getUserSitesByRole(@PathVariable("userId") Long userId, @RequestParam("roleName") String roleName) {
		List < UserAuthoritiesDTO > userSites = null;
		if (UserRole.CUSTOMER_SUPER_USER.getRoleName().equals(roleName)) {
			userSites = this.userService.getUserSitesByRole(userId, UserRole.CUSTOMER_SUPER_USER.getRoleId());

		} else if (UserRole.SLADE_ADMIN.getRoleName().equals(roleName)) {
			userSites =  this.userService.getAllUserSites();
		}
		List < UserRoleDTO > roles = this.userService.getAllRoles();
		
		Map < String, Object > responseMap = new HashMap< String, Object >();
		responseMap.put("userSites", userSites);
		responseMap.put("roles", roles);

		return responseMap;
	}
	
	@PutMapping(value = "/preferences/", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateSitePreferences(@PathVariable("userId") Long userId, @RequestBody UserDTO userDTO) {
		try {
			// Save the User instance's site preferences
			this.userService.updateSitePreferences(userDTO);

		} catch (Exception ex) {
			logger.error("Error occurred in updating User Account Preferences", ex);
			throw new ApiException(ex.getMessage());
		}

	}
}
