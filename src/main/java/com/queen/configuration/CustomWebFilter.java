package com.queen.configuration;

import com.queen.application.ports.in.AttachUserCommand;
import com.queen.application.service.AttachUserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.annotation.Order;
import org.springframework.core.log.LogMessage;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.authentication.AnonymousAuthenticationWebFilter;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Objects;

public class CustomWebFilter implements WebFilter {
	private final AttachUserService attachUserService;
	private static final Log logger = LogFactory.getLog(CustomWebFilter.class);
	public CustomWebFilter(final AttachUserService attachUserService) {
		this.attachUserService = attachUserService;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		logger.info("AAAAAAAAAAAAAAAA");
		return exchange.getPrincipal().flatMap(token -> {
			FellaJwtAuthenticationToken t = (FellaJwtAuthenticationToken) token;
			return ReactiveSecurityContextHolder.getContext().switchIfEmpty(Mono.defer(() -> {
				SecurityContext securityContext = new SecurityContextImpl(t);
				logger.info(LogMessage.format("Populated SecurityContext with anonymous token: '%s'", t));
				return chain.filter(exchange)
						.subscriberContext(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(securityContext)))
						.then(Mono.empty());
			})).flatMap((securityContext) -> {
				logger.info(LogMessage.format("SecurityContext contains anonymous token: '%s'",
						securityContext.getAuthentication()));
				return chain.filter(exchange).and(attachUserService.attachNewUserDetails(new AttachUserCommand(t)));
			});

			//return chain.filter(exchange);
		});
//		return ReactiveSecurityContextHolder.getContext().switchIfEmpty(Mono.defer(() -> {
//			return exchange.getPrincipal().flatMap(token -> {
//				SecurityContext securityContext = new SecurityContextImpl((FellaJwtAuthenticationToken) token);
//				logger.debug(LogMessage.format("Populated SecurityContext with anonymous token: '%s'", token));
//				return chain.filter(exchange)
//						.subscriberContext(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(securityContext)))
//						.then(Mono.empty());
//			}).flatMap((securityContext) -> {
//						logger.debug(LogMessage.format("SecurityContext contains anonymous token: '%s'",
//								securityContext));
//						return chain.filter(exchange);
//					});
//			}));



//		ReactiveSecurityContextHolder.getContext().switchIfEmpty(Mono.defer(() -> {
//			exchange.getPrincipal().flatMap(sc -> {
//				FellaJwtAuthenticationToken token = (FellaJwtAuthenticationToken) sc;
////						if (!("/user".equals(exchange.getRequest().getPath().pathWithinApplication().value()) &&
////								Objects.requireNonNull(exchange.getRequest().getMethod()).name().equals(HttpMethod.POST.name()))) {
////
////							//@TODO get rid of when proper client is implemented
////							if (!"/messages".equals(exchange.getRequest().getPath().pathWithinApplication().value())) {
////								SecurityContextImpl securityContext = new SecurityContextImpl();
////								securityContext.setAuthentication((FellaJwtAuthenticationToken) sc);
////								return chain.filter(exchange).and(attachUserService.attachNewUserDetails(new AttachUserCommand(token))
////										.subscriberContext(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(securityContext))));
//////						return chain.filter(exchange).and(setUpSecurityHolder(token)).and(attachUserService.attachNewUserDetails(new AttachUserCommand(token)));
////							}
////						}
//				SecurityContextImpl securityContext = new SecurityContextImpl();
//				securityContext.setAuthentication((FellaJwtAuthenticationToken) sc);
//				return chain.filter(exchange).and(attachUserService.attachNewUserDetails(new AttachUserCommand(token))
//						.subscriberContext(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(securityContext))).then(Mono.empty()));
////							SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_GLOBAL);
////							SecurityContextImpl securityContext = new SecurityContextImpl();
////							securityContext.setAuthentication((FellaJwtAuthenticationToken) sc);
////							SecurityContextHolder.setContext(securityContext);
//
//			}).flatMap(a -> a));
//			return null;
//		}));
//		return Mono.empty();
	}
}
//			Authentication authentication = createAuthentication(exchange);
//			SecurityContext securityContext = new SecurityContextImpl(authentication);
//			logger.debug(LogMessage.format("Populated SecurityContext with anonymous token: '%s'", authentication));
//			return chain.filter(exchange)
//					.subscriberContext(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(securityContext)))
//					.then(Mono.empty());
//		})).flatMap((securityContext) -> {
//			logger.debug(LogMessage.format("SecurityContext contains anonymous token: '%s'",
//					securityContext.getAuthentication()));
//			return chain.filter(exchange);
//		});


//		return exchange.getPrincipal()
//				.filter(token -> token instanceof FellaJwtAuthenticationToken).flatMap(sc -> {
//							FellaJwtAuthenticationToken token = (FellaJwtAuthenticationToken) sc;
//							if (!("/user".equals(exchange.getRequest().getPath().pathWithinApplication().value()) &&
//								Objects.requireNonNull(exchange.getRequest().getMethod()).name().equals(HttpMethod.POST.name()))) {
//
//								//@TODO get rid of when proper client is implemented
//								if (!"/messages".equals(exchange.getRequest().getPath().pathWithinApplication().value())) {
//									return chain.filter(exchange).and(setUpSecurityHolder(token)).and(attachUserService.attachNewUserDetails(new AttachUserCommand(token)));
//								}
//							}
////							SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_GLOBAL);
////							SecurityContextImpl securityContext = new SecurityContextImpl();
////							securityContext.setAuthentication((FellaJwtAuthenticationToken) sc);
////							SecurityContextHolder.setContext(securityContext);
//							return chain.filter(exchange).doFirst(() -> setUpSecurityHolder(token));
//				});
//	}

//	private Mono<Void> setUpSecurityHolder(FellaJwtAuthenticationToken token) {
//		return Mono.fromCallable(() -> {
//			SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_GLOBAL);
//			SecurityContextImpl securityContext = new SecurityContextImpl();
//			securityContext.setAuthentication(token);
//			SecurityContextHolder.setContext(securityContext);
//			return null;
//		});
//	}
