package com.queen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FellaApp {
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(FellaApp.class);
		app.setWebApplicationType(WebApplicationType.REACTIVE); // Set to reactive mode
		app.run(args);
	}
}
