package com.telstra.health.shot.security;

import java.security.Security;
import java.util.Arrays;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import com.telstra.health.shot.service.UserServiceImpl;
import static com.telstra.health.shot.security.SecurityConstants.AUTHORIZATION_EXPIRY_HEADER_NAME;
import static com.telstra.health.shot.security.SecurityConstants.AUTHORIZATION_HEADER_NAME;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserServiceImpl authProvider;

	@Autowired
	protected void configure(AuthenticationManagerBuilder builder) throws Exception {
		builder.authenticationProvider(authProvider);
	}
	
	@Value("${com.telstra.health.shot.security.authorization-token-expires-in}")
	private int tokenExpiresIn;

	@Value("${com.telstra.health.shot.security.encryption-secret-key}")
	private String encryptionSecretKey;

	@Value("${spring.ldap.urls}")
	private String ldapUrls;

	@Value("${spring.ldap.base}")
	private String ldapBase;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
        .authorizeRequests()
        .antMatchers("/health", "/password/**","/**/contactus/**/").permitAll()
        .anyRequest().authenticated().and() // all APIs are protected
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        .csrf().disable() // enabling the CSRF protection will mean we will have to keep some kind of session to address it - thus making our APIs stateful.
        .addFilter(new JWTAuthenticationFilter(authenticationManager(), encryptionSecretKey, tokenExpiresIn)) // this filter is invoked only for URI "/login".
        .addFilter(new JWTAuthorizationFilter(authenticationManager(), encryptionSecretKey, tokenExpiresIn)); // this filter is invoked for all APIs that are protected.
        
        Security.addProvider(new BouncyCastleProvider());
	}

	@Bean
	public DefaultSpringSecurityContextSource contextSource() {
		return new DefaultSpringSecurityContextSource(Arrays.asList(ldapUrls), ldapBase);
	}
	
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
      final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      CorsConfiguration config = new CorsConfiguration().applyPermitDefaultValues();
      config.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","PATCH","OPTIONS"));
      config.addExposedHeader(AUTHORIZATION_HEADER_NAME); // expose these headers so the they can be accessed by JavaScript
      config.addExposedHeader(AUTHORIZATION_EXPIRY_HEADER_NAME);
      source.registerCorsConfiguration("/**", config);
      return source;
    }
    
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}