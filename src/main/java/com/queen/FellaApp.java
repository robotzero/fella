package com.queen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FellaApp {
	static void main(String[] args) {
		SpringApplication app = new SpringApplication(FellaApp.class);
		app.run(args);
	}
}
