package com.queen.application.service;

import com.queen.adapters.persistance.UserMapper;
import com.queen.application.ports.in.UserQuery;
import com.queen.application.ports.out.LoadUser;
import com.queen.domain.user.User;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;
import java.util.UUID;

public class UserService implements UserQuery {
	private final LoadUser loadUser;
	private final UserMapper userMapper;

	public UserService(final LoadUser loadUser, final UserMapper userMapper) {
		this.loadUser = loadUser;
		this.userMapper = userMapper;
	}

	@Override
	public Mono<User> load() {
		return loadUser.loadUser("blah").map(user -> userMapper.mapToDomain(user));
	}

//	public void attachUserDetails(JwtAuthenticationToken jwtAuthenticationToken) {
//		final var disposable = this.loadUser.loadUser("blaha").single().subscribe((user) -> {
//			jwtAuthenticationToken.setDetails(userMapper.mapToDomain(user));
//		}, (throwable) -> {
//			if (throwable instanceof NoSuchElementException) {
//				final var user = new com.queen.infrastructure.persitence.User(UUID.randomUUID().toString(), jwtAuthenticationToken.getName());
//				jwtAuthenticationToken.setDetails(userMapper.mapToDomain(user));
//			} else {
//				throw new RuntimeException("Error", throwable);
//			}
//		});
//		disposable.dispose();
//	}
}
