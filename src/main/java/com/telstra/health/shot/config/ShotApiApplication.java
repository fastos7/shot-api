package com.telstra.health.shot.config;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.telstra.health.shot.service.exception.ShotServiceException;
 
@SpringBootApplication
@EnableJpaRepositories(basePackages="com.telstra.health.shot")
@ComponentScan("com.telstra.health.shot")
@EntityScan("com.telstra.health.shot.entity")
@EnableScheduling
public class ShotApiApplication extends SpringBootServletInitializer {
	static Logger logger = LoggerFactory.getLogger(ShotApiApplication.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = null;
		try {
			ctx = SpringApplication.run(ShotApiApplication.class, args);
		} catch (ShotServiceException ex) {
			logger.error("Error occurred while starting SHOT API application. Shutting down Application.", ex);
			if(ctx != null) {
				ctx.close();
			}
		} catch (Exception ex) {
			if (!ex.getClass().getName().equals("org.springframework.boot.devtools.restart.SilentExitExceptionHandler$SilentExitException")) {
				logger.error("Error occurred while starting SHOT API application", ex);
				throw new RuntimeException(ex);
			}
		}
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ShotApiApplication.class);
	}
	
	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}
}
