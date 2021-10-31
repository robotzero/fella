package com.queen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.support.WebStack;

@SpringBootApplication
@EnableHypermediaSupport(
		type = {
				EnableHypermediaSupport.HypermediaType.HAL,
				EnableHypermediaSupport.HypermediaType.COLLECTION_JSON,
				EnableHypermediaSupport.HypermediaType.HAL_FORMS
		},
		stacks = {
				WebStack.WEBFLUX
		})
public class FellaApp {
	public static void main(String[] args) {
		SpringApplication.run(FellaApp.class, args);
	}
}
