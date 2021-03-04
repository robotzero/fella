package com.queen.infrastructure.persitence;

import com.queen.adapters.persistance.UserMapper;
import com.queen.domain.user.FellaUser;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import reactor.core.publisher.Mono;

public class SpringSecurityAuditorAware implements ReactiveAuditorAware<String> {
	private final UserMapper userMapper;

	public SpringSecurityAuditorAware(final UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	@Override
	public Mono<String> getCurrentAuditor() {
		System.out.println("CURRENT AUDIT");
		ReactiveSecurityContextHolder.getContext().hasElement().filter(a -> a == false).subscribe(c -> System.out.println("NOT!!!!!!!!!!!!!"));
		return ReactiveSecurityContextHolder.getContext().map(securityContext -> {
			System.out.println("GETTING THE FUCKING DETAILS");
			return (FellaUser) securityContext.getAuthentication().getDetails();
		}).map(userMapper::mapToPersistence).map(persistentceUser -> {
			System.out.println(persistentceUser.getId());
			return persistentceUser.getId();
		});
	}
}
