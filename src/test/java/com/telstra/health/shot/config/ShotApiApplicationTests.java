package com.telstra.health.shot.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class) 
@Configuration
@PropertySource("classpath:global.properties")
@ComponentScan("com.telstra.health.shot")
public class ShotApiApplicationTests {

	@Test
	public void contextLoads() {
	}

}
