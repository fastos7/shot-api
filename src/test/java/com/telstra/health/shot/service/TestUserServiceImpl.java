package com.telstra.health.shot.service;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ldap.core.CollectingAuthenticationErrorCallback;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.telstra.health.shot.config.ShotApiApplicationTests;
import com.telstra.health.shot.dao.UserDAOImpl;
import com.telstra.health.shot.dao.exception.DataAccessException;
import com.telstra.health.shot.resources.DummyUsers;
import com.telstra.health.shot.service.UserAuthTokenService;
import com.telstra.health.shot.service.UserServiceImpl;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { ShotApiApplicationTests.class })
public class TestUserServiceImpl {

	@Mock
	private UserDAOImpl userDAO;

	@Autowired
	@InjectMocks
	private UserServiceImpl userService;

	@Mock
	private Authentication authHeader;

	@Before
	public void before() {
		Exception exeObj = new Exception(
				"[LDAP: error code 49 - 80090308: LdapErr: DSID-0C0903A9, comment: AcceptSecurityContext error, data 52e, v1db0");

	}

	@Test
	public void testAuthenticateUser() throws Exception {
		when(userDAO.authenticateUser(Mockito.anyString(), Mockito.anyString(), Mockito.any())).thenReturn(true);
		when(userDAO.findUser(Mockito.anyString())).thenReturn((DummyUsers.getTestUserDtoList()));
		when(userDAO.getDefaultSite(Mockito.anyString())).thenReturn(DummyUsers.getTestUserDto().getDefaultSite());
		when(userDAO.getUserAuthorities(Mockito.anyString())).thenReturn(DummyUsers.getUserAuthorities());

		UserAuthTokenService authHeader = (UserAuthTokenService) userService
				.authenticate(new UsernamePasswordAuthenticationToken("deep", "fred"));

		Assert.assertNotNull(authHeader);
		Assert.assertEquals(authHeader.getUserObject(), DummyUsers.getTestUserDto());
		Assert.assertEquals(authHeader.getUserObject().getDefaultSite(), DummyUsers.getTestUserDto().getDefaultSite());
		Assert.assertEquals(authHeader.getUserObject().getUserAuthorities(), DummyUsers.getMergedAuthorities());
	}

	@Test
	public void testNoUserAuthorieites() throws Exception {
		when(userDAO.authenticateUser(Mockito.anyString(), Mockito.anyString(), Mockito.any())).thenReturn(true);
		when(userDAO.findUser(Mockito.anyString())).thenReturn((DummyUsers.getTestUserDtoList()));
		when(userDAO.getUserAuthorities(Mockito.anyString())).thenReturn(null);

		try {
			userService.authenticate(new UsernamePasswordAuthenticationToken("deep", "fred"));
		} catch (AuthenticationException e) {
			Assert.assertEquals(e.getMessage(), "Failed to get User Authorities. Please contact support.");
		}
	}

	// @Test
	public void testInvalidPasswd() throws Exception {
		// define the mock data when user then return true
		Exception exeObj = new Exception(
				"[LDAP: error code 49 - 80090308: LdapErr: DSID-0C0903A9, comment: AcceptSecurityContext error, data 52e, v1db0");
		CollectingAuthenticationErrorCallback mockerrorcallback = Mockito
				.mock(CollectingAuthenticationErrorCallback.class);
		mockerrorcallback.execute(exeObj);
		when(userDAO.authenticateUser(Mockito.anyString(), Mockito.anyString(), mockerrorcallback)).thenReturn(false);

		try {
			UserAuthTokenService authHeader = (UserAuthTokenService) userService
					.authenticate(new UsernamePasswordAuthenticationToken("deep", "fred"));
		} catch (Exception ex) {
			Assert.assertEquals("You have entered invalid credentials", ex.getMessage());
		}

	}

	@Test
	public void testValidUserinAD() {
		doNothing().when(userDAO).createUser(Mockito.anyString(), Mockito.anyString());
		boolean returnvalue = userService.createUser("dummy.user", "password");
		Assert.assertEquals(true, returnvalue);

	}

	@Test
	public void testUserWithExceptioninAD() {
		String errorMessage = "User email already exists in the system.";
		doThrow(new DataAccessException(
				"[LDAP: error code 68 - 00002071: UpdErr: DSID-03050328, problem 6005 (ENTRY_EXISTS), data 0 value "))
						.when(userDAO).createUser(Mockito.anyString(), Mockito.anyString());
		try {
			boolean returnvalue = userService.createUser("dummy.user", "password");
		} catch (Exception e) {
			Assert.assertThat(e.getMessage(), is(errorMessage));
		}

	}

}
