//package com.queen.controller;
//
////import com.queen.configuration.FellaJwtAuthenticationToken;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.context.ReactiveSecurityContextHolder;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
//import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
//import org.springframework.security.oauth2.core.OAuth2AccessToken;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestController;
//import reactor.core.publisher.Mono;
//
//import java.security.Principal;
//
//@RestController
//public class TestController {
//	@GetMapping("/")
//  @ResponseStatus(HttpStatus.OK)
//  public Mono<String[]> test(Authentication authentication, Principal principal, @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient,
//			@AuthenticationPrincipal OAuth2User oauth2User) {
//	  ReactiveSecurityContextHolder.withAuthentication(authentication);
//		OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
////	  FellaJwtAuthenticationToken jwtAuthentication = (FellaJwtAuthenticationToken) authentication;
//	  return Mono.fromSupplier(() -> new String[] {"Message 1", "Message 2", "Message 4", accessToken.getTokenValue()});
//  }
//}
