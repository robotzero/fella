package com.queen.controller;

import com.queen.infrastructure.PeriodsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class TestController {
	private final PeriodsRepository periodsRepository;

	public TestController(PeriodsRepository periodsRepository) {
		this.periodsRepository = periodsRepository;
	}

	@GetMapping("/messages")
  @ResponseStatus(HttpStatus.OK)
  public Mono<String[]> test(Authentication authentication) {
	  JwtAuthenticationToken jwtAuthentication = (JwtAuthenticationToken) authentication;
	  return Mono.fromSupplier(() -> new String[] {"Message 1", "Message 2", "Message 4", jwtAuthentication.getToken().getTokenValue() });
  }

	@GetMapping("/periods")
	@ResponseStatus(HttpStatus.OK)
	public Mono<Period> periods() {
		return periodsRepository.findById(1L);
	}
}
