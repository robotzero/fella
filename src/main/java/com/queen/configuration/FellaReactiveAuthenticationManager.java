package com.queen.configuration;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import reactor.core.publisher.Mono;

public class FellaReactiveAuthenticationManager implements ReactiveAuthenticationManager {
	@Override
	public Mono<Authentication> authenticate(Authentication authentication) {
		System.out.println("ABCDF******************************");
		JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication.getPrincipal();
		return Mono.just(new UsernamePasswordAuthenticationToken(
				jwtAuthenticationToken,
				jwtAuthenticationToken.getToken(),
				authentication.getAuthorities())
		);
	}
}
