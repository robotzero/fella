package com.queen.configuration;

import com.queen.infrastructure.persitence.UserRepository;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * @author Rob Winch
 * @since 5.0
 */
public class FellaAuthenticationManager
		implements ReactiveAuthenticationManager {
//	private final UserRepository users;
//	private final ReactiveAuthenticationManager delegate;
//	private final PasswordEncoder encoder;

//	FellaAuthenticationManager(
//			UserRepository users,
//			ReactiveUserDetailsService userDetailsService, PasswordEncoder encoder) {
//		this.users = users;
//		this.delegate = createDelegate(userDetailsService, encoder);
//		this.encoder = encoder;
//	}

	@Override
	public Mono<Authentication> authenticate(Authentication authentication) {
		System.out.println("BLAsfsfsfsffAA!!!!!!!!!!!!!!A");
		return Mono.just(authentication);
//		return this.delegate.authenticate(authentication);
//				.delayUntil(a -> updatePassword(authentication));
	}

//	private Mono<User> updatePassword(Authentication authentication) {
//		return this.users.findByEmail(authentication.getName())
//				.publishOn(Schedulers.parallel()).doOnSuccess(u -> u.setPassword(
//						this.encoder.encode(authentication.getCredentials().toString())))
//				.flatMap(this.users::save);
//	}

	private static ReactiveAuthenticationManager createDelegate(
			ReactiveUserDetailsService userDetailsService, PasswordEncoder encoder) {
		UserDetailsRepositoryReactiveAuthenticationManager result = new UserDetailsRepositoryReactiveAuthenticationManager(
				userDetailsService);
		result.setPasswordEncoder(encoder);
		return result;
	}
}
