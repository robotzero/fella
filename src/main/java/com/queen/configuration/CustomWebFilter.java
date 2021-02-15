package com.queen.configuration;

import com.queen.application.ports.in.AttachNewUserCommand;
import com.queen.application.service.AttachNewUserService;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

public class CustomWebFilter implements WebFilter {
	private final AttachNewUserService attachNewUserService;

	public CustomWebFilter(final AttachNewUserService attachNewUserService) {
		this.attachNewUserService = attachNewUserService;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
				return exchange.getPrincipal()
				.filter(token -> token instanceof JwtAuthenticationToken).flatMap(sc -> {
					attachNewUserService.attachNewUserDetails(new AttachNewUserCommand((JwtAuthenticationToken) sc));
					return chain.filter(exchange);
				});
	}
}
