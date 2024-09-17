//package com.queen.configuration;
//
//import com.queen.application.service.AttachUserService;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.core.log.LogMessage;
//import org.springframework.security.core.context.ReactiveSecurityContextHolder;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextImpl;
//import org.springframework.web.server.ServerWebExchange;
//import org.springframework.web.server.WebFilter;
//import org.springframework.web.server.WebFilterChain;
//import reactor.core.publisher.Mono;
//
//public class CustomWebFilter implements WebFilter {
//	private final AttachUserService attachUserService;
//	private static final Log logger = LogFactory.getLog(CustomWebFilter.class);
//
//	public CustomWebFilter(final AttachUserService attachUserService) {
//		this.attachUserService = attachUserService;
//	}
//
//	@Override
//	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//		return exchange.getPrincipal().flatMap(token -> {
//			System.out.println("SECOND!!!!");
//			FellaJwtAuthenticationToken t = (FellaJwtAuthenticationToken) token;
//			return ReactiveSecurityContextHolder.getContext().defaultIfEmpty(new SecurityContextImpl(t)).flatMap((securityContext) -> {
//				logger.info(LogMessage.format("SecurityContext contains anonymous token: '%s'",
//						securityContext.getAuthentication()));
//				return chain.filter(exchange);
//			});
//		});
//	}
//}
