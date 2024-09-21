package com.queen.application.service;

import com.queen.adapters.persistance.UserMapper;
import com.queen.application.ports.in.CreateUserCommand;
import com.queen.application.ports.in.CreateUserUseCase;
import com.queen.application.ports.in.UserQuery;
import com.queen.application.ports.out.CreateUserPort;
import com.queen.application.ports.out.LoadSpringUserPort;
import com.queen.application.ports.out.LoadUserPort;
import com.queen.application.service.exception.UserServiceException;
import com.queen.domain.user.FellaUser;
import com.queen.infrastructure.persistence.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

public class UserService implements LoadSpringUserPort, CreateUserUseCase, UserQuery {
	private final LoadUserPort loadUserPort;
	private final UserMapper userMapper;
	private final CreateUserPort createUserPort;

	public UserService(final LoadUserPort loadUserPort, final UserMapper userMapper, final CreateUserPort createUserPort) {
		this.loadUserPort = loadUserPort;
		this.userMapper = userMapper;
		this.createUserPort = createUserPort;
	}

	@Override
	public Mono<UserDetails> findByUsername(final String username) {
		return loadUserPort.loadUser(username).map(userMapper::mapToDomain);
	}

	@Override
	@Transactional
	public Mono<FellaUser> createUser(final CreateUserCommand createUserCommand) {
		return loadUserPort.loadUser(createUserCommand.username())
				.map(userMapper::mapToDomain)
				.switchIfEmpty(
						Mono.defer(() -> createUserPort.createUser(new User(null, createUserCommand.username(), true))
								.map(userMapper::mapToDomain)
								.doOnError(error -> {
									throw new UserServiceException("Failed to create new user", error);
								})));
	}

	@Override
	public Mono<FellaUser> getUserByUsername(String username) {
		return loadUserPort.loadUser(username).map(userMapper::mapToDomain);
	}
}
