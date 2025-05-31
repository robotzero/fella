package com.queen.application.service;

import com.queen.adapters.persistance.UserMapper;
import com.queen.application.ports.in.CreateUserCommand;
import com.queen.application.ports.in.CreateUserUseCase;
import com.queen.application.ports.in.UserQuery;
import com.queen.application.ports.out.CreateUserPort;
import com.queen.application.ports.out.LoadSpringUserPort;
import com.queen.application.ports.out.LoadUserPort;
import com.queen.domain.user.FellaUser;
import com.queen.infrastructure.persistence.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

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
	public UserDetails loadUserByUsername(final String username) {
		//@TODO proper exception for user not found
		return userMapper.mapToDomain(loadUserPort.loadUser(username).orElseThrow());
	}

	@Override
	@Transactional
	public FellaUser createUser(final CreateUserCommand createUserCommand) {
		return loadUserPort.loadUser(createUserCommand.username())
				.map(userMapper::mapToDomain)
				.orElseGet(() -> userMapper.mapToDomain(createUserPort.createUser(new User(null, createUserCommand.username(), true)))
				);
	}

	@Override
	public FellaUser getUserByUsername(String username) {
		return loadUserPort.loadUser(username).map(userMapper::mapToDomain).orElseThrow();
	}
}
