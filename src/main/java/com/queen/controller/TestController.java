package com.queen.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class TestController {
  @GetMapping("/messages")
  @ResponseStatus(HttpStatus.OK)
  public Mono<String[]> test(Authentication authentication) {
	  JwtAuthenticationToken jwtAuthentication = (JwtAuthenticationToken) authentication;
    return getProduct(jwtAuthentication.getToken().getTokenValue());
  }

  private Mono<String[]> getProduct(String token) {
    return Mono.fromSupplier(() -> {
//      final var product = new Product();
//      product.setId(14);
//      return product;
      return new String[] {"Message 1", "Message 2", "Message 4", token};
    });
  }
}
