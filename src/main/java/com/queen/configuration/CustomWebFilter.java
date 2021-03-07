package com.queen.configuration;

import com.queen.application.ports.in.CreateUserTemplateEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

public class CustomWebFilter implements WebFilter {
	private final CreateUserTemplateEvent createUserTemplateEvent;
	public CustomWebFilter(final CreateUserTemplateEvent createUserTemplateEvent) {
		this.createUserTemplateEvent = createUserTemplateEvent;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
				return exchange.getPrincipal()
				.filter(token -> token instanceof FellaJwtAuthenticationToken).flatMap(sc -> {
							Authentication authentication = (Authentication) sc;
							SecurityContextImpl securityContext = new SecurityContextImpl();
							securityContext.setAuthentication(authentication);
							SecurityContextHolder.setContext(securityContext);
							createUserTemplateEvent.publishCreateUserTemplateEvent(List.of());
							return chain.filter(exchange);
				});
	}
}
