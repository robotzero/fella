package com.queen.configuration;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

public class CustomWebFilter implements WebFilter {
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
				return exchange.getPrincipal()
				.filter(token -> token instanceof FellaJwtAuthenticationToken).flatMap(sc -> {
							Authentication authentication = (Authentication) sc;
							SecurityContextImpl securityContext = new SecurityContextImpl();
							securityContext.setAuthentication(authentication);
							SecurityContextHolder.setContext(securityContext);
							return chain.filter(exchange);
				});
	}
}
