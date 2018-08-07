package com.telstra.health.shot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
//properties
@Configuration
public class ApplicationConfigLDAP {
	
	@Value("${ldap.jks.location}")
    private String ldapKeyStoreLocation;
	
	@Value("${ldap.jks.password}")
    private String ldapKeyStorePassword;
	

	@Value("${spring.ldap.userDn}")
    private String ldapUserDn;
	
	
	@Bean
    @ConfigurationProperties(prefix="spring.ldap")
	public LdapContextSource getContextSource() throws Exception{
		LdapContextSource ldapContextSource = new LdapContextSource(); 
		ldapContextSource.setUserDn(ldapUserDn);
		return ldapContextSource;
	}  
	
	@Bean
	public LdapTemplate ldapTemplate() throws Exception{		 
		System.setProperty("javax.net.ssl.trustStore",ldapKeyStoreLocation );
		System.setProperty("javax.net.ssl.trustStorePassword", ldapKeyStorePassword);
		LdapTemplate ldapTemplate = new LdapTemplate(getContextSource());
		ldapTemplate.setIgnorePartialResultException(true);
		ldapTemplate.setContextSource(getContextSource());
		return ldapTemplate;
	}

}
