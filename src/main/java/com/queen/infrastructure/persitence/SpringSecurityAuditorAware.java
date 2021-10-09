package com.queen.infrastructure.persitence;

import com.queen.configuration.FellaJwtAuthenticationToken;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import reactor.core.publisher.Mono;

public class SpringSecurityAuditorAware implements ReactiveAuditorAware<String> {
	@Override
	public Mono<String> getCurrentAuditor() {
		return ReactiveSecurityContextHolder.getContext().map(context -> {
			FellaJwtAuthenticationToken token = (FellaJwtAuthenticationToken) context.getAuthentication().getCredentials();
			return token.getUserId();
		});
	}
}
