package com.queen.configuration;

import com.queen.application.ports.in.AttachNewUserCommand;
import com.queen.application.service.AttachNewUserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.WebFilterChainServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

public class CustomWebFilter implements WebFilter {
	private final AttachNewUserService attachNewUserService;
	private final WebSessionServerSecurityContextRepository securityContextRepository;
	private final ServerAuthenticationSuccessHandler authenticationSuccessHandler;

	public CustomWebFilter(final AttachNewUserService attachNewUserService) {
		this.attachNewUserService = attachNewUserService;
		this.securityContextRepository = new WebSessionServerSecurityContextRepository();
		this.authenticationSuccessHandler = new WebFilterChainServerAuthenticationSuccessHandler();
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
				return exchange.getPrincipal()
				.filter(token -> token instanceof JwtAuthenticationToken).flatMap(sc -> {
							Authentication authentication = (Authentication) sc;
							SecurityContextImpl securityContext = new SecurityContextImpl();
							securityContext.setAuthentication(authentication);
//							attachNewUserService.attachNewUserDetails(new AttachNewUserCommand((JwtAuthenticationToken) sc));
							return this.securityContextRepository.save(exchange, securityContext).then(authenticationSuccessHandler.onAuthenticationSuccess(new WebFilterExchange(exchange, chain), authentication))
									.subscriberContext(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(securityContext)));
//					return chain.filter(exchange).subscriberContext(ReactiveSecurityContextHolder.withAuthentication(authentication));
				});
	}
}
