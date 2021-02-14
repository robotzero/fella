package com.queen.configuration;

import com.queen.application.service.UserService;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

public class CustomWebFilter implements WebFilter {
	private final UserService userService;

	public CustomWebFilter(final UserService userService) {
		this.userService = userService;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
				return exchange.getPrincipal()
				.filter(token -> token instanceof JwtAuthenticationToken).flatMap(sc -> {

					return chain.filter(exchange);
				});
	}
}
