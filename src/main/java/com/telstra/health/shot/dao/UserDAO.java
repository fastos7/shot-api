package com.telstra.health.shot.dao;

import java.util.List;
import java.util.Set;
import javax.transaction.Transactional;
import org.springframework.ldap.core.CollectingAuthenticationErrorCallback;

import com.telstra.health.shot.dao.exception.DataAccessException;
import com.telstra.health.shot.dto.UserAuthoritiesDTO;
import com.telstra.health.shot.dto.UserDTO;
import com.telstra.health.shot.dto.UserSearchDTO;
import com.telstra.health.shot.entity.Users;

@Transactional
public interface UserDAO {

	boolean authenticateUser(String sUsername, String password, CollectingAuthenticationErrorCallback errorcallback);

	List<UserDTO> findUser(String userName) throws DataAccessException;

	Set<UserAuthoritiesDTO> getUserAuthorities(String email) throws DataAccessException;

	String getDefaultSite(String email) throws DataAccessException;

	void createUser(String email, String password);

	void deleteUser(String email);

	void updateUserPassword(String email, String userNewPassword);

	List < Users > searchUsers(UserSearchDTO userSearchDTO, Boolean includeSladeAdmin, List < String > userSiteKeys);
}
