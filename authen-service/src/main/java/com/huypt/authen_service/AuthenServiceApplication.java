package com.huypt.authen_service;

import com.huypt.authen_service.config.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties.class)
public class AuthenServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthenServiceApplication.class, args);
	}

}
