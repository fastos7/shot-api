package com.telstra.health.shot.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import static com.telstra.health.shot.security.SecurityConstants.AUTHORIZATION_EXPIRY_HEADER_NAME;
import static com.telstra.health.shot.security.SecurityConstants.AUTHORIZATION_HEADER_NAME;
import static com.telstra.health.shot.security.SecurityConstants.AUTHORIZATION_TOKEN_PREFIX;
import static com.telstra.health.shot.security.SecurityConstants.AUTHORIZATION_CLAIM_LITERAL;
import static com.telstra.health.shot.security.SecurityConstants.SLADE_ADMIN;


public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	private int tokenExpiresIn;
	private String encryptionSecretKey;

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, String encryptionSecretKey,
			int tokenExpiresIn) {
		super(authenticationManager);
		this.tokenExpiresIn = tokenExpiresIn;
		this.encryptionSecretKey = encryptionSecretKey;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		// Extract the token
		String jwt = req.getHeader(AUTHORIZATION_HEADER_NAME);

		// If no token found, move to the next filter; spring security will take care of
		// the rest
		if (jwt == null || !jwt.startsWith(AUTHORIZATION_TOKEN_PREFIX)) {
			chain.doFilter(req, res);
			return;
		}
		
		try {
			// Get the claims from token. Note: the claims also include the subject, i.e.
			// user email id.
			Claims claims = Jwts.parser().setSigningKey(encryptionSecretKey.getBytes())
					.parseClaimsJws(jwt.replace(AUTHORIZATION_TOKEN_PREFIX, "")).getBody();

			// Get customer Id that the operation is performed for.
			// All our APIs that pertain to changing state of application for a given customer include customer key in the URI.
			// Extract the customer key from URI so that we can perform authorization check.
			String customerKey = getCustomerKeyFromUri(req.getRequestURI());
			String customerList = (String) claims.get(AUTHORIZATION_CLAIM_LITERAL);

			// Allow the user to perform this operation only if s/he belongs to the customer
			authorizeUser(customerKey, customerList);

			SecurityContextHolder.getContext().setAuthentication(
					new UsernamePasswordAuthenticationToken(claims.getSubject(), null, new ArrayList<>()));

			// Update the token to extend the expiry-time
			jwt = Jwts.builder().setClaims(claims).setExpiration(new Date(System.currentTimeMillis() + tokenExpiresIn))
					.signWith(SignatureAlgorithm.HS512, encryptionSecretKey.getBytes()).compact();

			// Update the response with updated JWT
			res.addHeader(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_TOKEN_PREFIX + jwt);
			res.addIntHeader(AUTHORIZATION_EXPIRY_HEADER_NAME, tokenExpiresIn);

			// Pass the baton on
			chain.doFilter(req, res);
		} catch (Exception ex) {
			// if the token is not parsed successfully or if the user is unauthorized
			res.setStatus(HttpStatus.UNAUTHORIZED.value());
		}
	}

	private void authorizeUser(String customerKey, String customerList) throws Exception {
		// Absence of customer key indicates an operation that is not specific to any customer.
		// In that case, we don't need to perform authorization check.
		if (customerKey == null) return;
		// For Slade admin, the customer list reads "Slade_Admin".
		// Slade admin is authorized to perform all operations in the system
		if (SLADE_ADMIN.equals(customerList)) return;
		// If the user has access to the customer key he is perform operation on
		if (isCustomerKeyPresentInList(customerKey, customerList)) return;
		// If no conditions met thus far, the user is unauthorized
		throw new Exception();
	}

	private boolean isCustomerKeyPresentInList(String customerKey, String customerList) {
		String newList = "," + customerList + ",";
		return newList.contains("," + customerKey + ",");
	}

	private String getCustomerKeyFromUri(String uri) {
		Pattern p = Pattern.compile("customers/(.*?)\\/");
		Matcher m = p.matcher(uri);
		if (m.find()) return m.group(1).trim();
		return null;
	}
}
