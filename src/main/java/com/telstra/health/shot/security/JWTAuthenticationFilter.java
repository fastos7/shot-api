package com.telstra.health.shot.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.telstra.health.shot.dto.UserAuthoritiesDTO;
import com.telstra.health.shot.dto.UserDTO;
import com.telstra.health.shot.service.UserAuthTokenService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import static com.telstra.health.shot.security.SecurityConstants.AUTHORIZATION_EXPIRY_HEADER_NAME;
import static com.telstra.health.shot.security.SecurityConstants.AUTHORIZATION_HEADER_NAME;
import static com.telstra.health.shot.security.SecurityConstants.AUTHORIZATION_TOKEN_PREFIX;
import static com.telstra.health.shot.security.SecurityConstants.AUTHORIZATION_CLAIM_LITERAL;
import static com.telstra.health.shot.security.SecurityConstants.SLADE_ADMIN;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;
	private int tokenExpiresIn;
	private String encryptionSecretKey;
	private static final Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, String encryptionSecretKey,
			int tokenExpiresIn) {
		this.authenticationManager = authenticationManager;
		this.tokenExpiresIn = tokenExpiresIn;
		this.encryptionSecretKey = encryptionSecretKey;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException {
		try {
			ObjectMapper mapper = new ObjectMapper();
			ApplicationUser creds = mapper.readValue(req.getInputStream(), ApplicationUser.class);
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(creds.getUsername(),
					creds.getPassword(), new ArrayList<>()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {

		String userIdentity = auth.getPrincipal().toString();
		UserDTO user = ((UserAuthTokenService) auth).getUserObject();
		Map<String, Object> claims = new HashMap<String, Object>();

		// SHOT users belong to Slade customers.
		// As such, a user can perform an action only if s/he is associated with the
		// customer in question.
		// The JWT token will contain a list of customers that the user has access to.
		if (Boolean.TRUE.equals(user.getIsShotAdmin())) {
			// If the user is SHOT Admin user, then s/he can perform any operation.
			claims.put(AUTHORIZATION_CLAIM_LITERAL, SLADE_ADMIN);
		} else {
			// Add comma separated customer keys
			// During authorization, we will check if the user belongs to the customer for
			// which s/he is performing operation
			claims.put(AUTHORIZATION_CLAIM_LITERAL, getCommaSeperatedCustomerList(user));
		}

		String token = Jwts.builder().setClaims(claims).setSubject(userIdentity)
				.setExpiration(new Date(System.currentTimeMillis() + tokenExpiresIn))
				.signWith(SignatureAlgorithm.HS512, encryptionSecretKey.getBytes()).compact();

		res.addHeader(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_TOKEN_PREFIX + token);
		res.addIntHeader(AUTHORIZATION_EXPIRY_HEADER_NAME, tokenExpiresIn);
		// Send the user object in response body; this is required for the UI to
		// function (i.e. show only the appropriate menus, etc.)
		res.getWriter().write(new ObjectMapper().writeValueAsString(user));

		logger.info("User [{}] logged in successfully.", userIdentity);
	}

	// In case of the failure login then display the custom error message
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
	}

	private String getCommaSeperatedCustomerList(UserDTO user) {
		List<UserAuthoritiesDTO> userAuthorities = user.getUserAuthorities();
		String customers[] = userAuthorities.stream().map(UserAuthoritiesDTO::getCustomerKey).toArray(String[]::new);
		String commaSeperatedCustomersList = String.join(",", customers);
		return commaSeperatedCustomersList;
	}

}
