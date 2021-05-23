package com.queen.configuration;

import com.queen.application.ports.in.AttachUserCommand;
import com.queen.application.service.AttachUserService;
import com.queen.domain.user.FellaUser;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Objects;

public class CustomWebFilter implements WebFilter {
	private final AttachUserService attachUserService;
	public CustomWebFilter(final AttachUserService attachUserService) {
		this.attachUserService = attachUserService;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		return exchange.getPrincipal()
				.filter(token -> token instanceof FellaJwtAuthenticationToken).flatMap(sc -> {
							Authentication authentication = (Authentication) sc;
							if (!("/user".equals(exchange.getRequest().getPath().pathWithinApplication().value()) &&
									(Objects.requireNonNull(exchange.getRequest().getMethod()).name().equals(HttpMethod.POST.name()) ||
											Objects.requireNonNull(exchange.getRequest().getMethod()).name().equals(HttpMethod.GET.name())))) {
								attachUserService.attachNewUserDetails(new AttachUserCommand((FellaJwtAuthenticationToken) sc));
								FellaUser fellaUser = (FellaUser) authentication.getDetails();
								FellaJwtAuthenticationToken token = (FellaJwtAuthenticationToken) sc;
								token.setUserId(fellaUser.getId());
							}
							SecurityContextImpl securityContext = new SecurityContextImpl();
							securityContext.setAuthentication(authentication);
							SecurityContextHolder.setContext(securityContext);
							return chain.filter(exchange);
				});
	}
}
