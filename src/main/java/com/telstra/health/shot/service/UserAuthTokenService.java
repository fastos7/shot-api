package com.telstra.health.shot.service;

import java.util.Collection;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import com.telstra.health.shot.dto.UserDTO;


public class UserAuthTokenService extends UsernamePasswordAuthenticationToken{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7234909457659420870L;
	
	private UserDTO userObject;
	
	public UserAuthTokenService(Object principal, Object credentials) {
	    super(principal, credentials);
	    this.setUserObject(null);

	}

	public UserAuthTokenService(Object principal, Object credentials, UserDTO customObject) {
	        super(principal, credentials);
	        this.setUserObject(customObject);
	        }
	
	
	public UserAuthTokenService(String principal, String credentials,  Collection<? extends GrantedAuthority> authorities, UserDTO object) {
        super(principal, credentials, authorities);

        this.setUserObject(object);
	}

	public UserDTO getUserObject() {
		return userObject;
	}

	public void setUserObject(UserDTO userObject) {
		this.userObject = userObject;
	}

	


}