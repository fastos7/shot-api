package com.telstra.health.shot.service;

import java.util.List;

import com.telstra.health.shot.dto.UserAuthoritiesDTO;
import com.telstra.health.shot.dto.UserDTO;
import com.telstra.health.shot.dto.UserRoleDTO;
import com.telstra.health.shot.dto.UserSearchDTO;
import com.telstra.health.shot.entity.Users;

public interface UserService {

	boolean createUser(String email, String userPassword);

	boolean deleteUser(String email);

	boolean updateUserPassword(String email, String newPassword); 
	
	/**
	 * Creates a User alongwith assigns the specified authorities to the selected
	 * Customer sites.
	 * 
	 * @param userDTO
	 *            DTO instance that contains both the basic User info as well as
	 *            Customer site authorities to assign.
	 */
	UserDTO createUserAccountAndAuthorities(UserDTO userDTO);
	
	/**
	 * Updates a User alongwith assigns the specified authorities to the selected Customer sites.
	 * @param userDTO DTO instance that contains both the basic User info as well as
	 *            Customer site authorities to assign.
	 */
	void updateUserAccountAndAuthorities(UserDTO userDTO);
	
	void deleteUserAccount(UserDTO userDTO);

	List < UserAuthoritiesDTO > getUserSitesByRole(Long userId, Integer roleId);
	
	List < UserAuthoritiesDTO > getAllUserSites();

	List < UserRoleDTO > getAllRoles();
	
	Users getUserByEmail(String email);
	
	UserSearchDTO searchUsers(UserSearchDTO userSearchDTO);
	
	void updateSitePreferences(UserDTO userDTO);
	
	List < Long > getActiveUsersToDeActivate(Integer disableUserTime);
	
	void deActivateUser(Long userId);
}
