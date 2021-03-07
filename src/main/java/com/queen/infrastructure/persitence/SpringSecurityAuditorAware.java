package com.queen.infrastructure.persitence;

import com.queen.configuration.FellaJwtAuthenticationToken;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import reactor.core.publisher.Mono;

public class SpringSecurityAuditorAware implements ReactiveAuditorAware<String> {
	@Override
	public Mono<String> getCurrentAuditor() {
		FellaJwtAuthenticationToken token = (FellaJwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		return Mono.just(token.getUserId());
	}
}
