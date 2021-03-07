package com.queen.controller;

import com.queen.configuration.FellaJwtAuthenticationToken;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class TestController {
	@GetMapping("/messages")
  @ResponseStatus(HttpStatus.OK)
  public Mono<String[]> test(Authentication authentication) {
	  ReactiveSecurityContextHolder.withAuthentication(authentication);
	  FellaJwtAuthenticationToken jwtAuthentication = (FellaJwtAuthenticationToken) authentication;
	  return Mono.fromSupplier(() -> new String[] {"Message 1", "Message 2", "Message 4", jwtAuthentication.getToken().getTokenValue() });
  }
}
