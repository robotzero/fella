package com.queen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class FellaApp {
	public static void main(String[] args) {
		SpringApplication.run(FellaApp.class, args);
	}
}
