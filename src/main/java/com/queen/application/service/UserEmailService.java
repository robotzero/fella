package com.queen.application.service;

import com.queen.adapters.persistance.UserMapper;
import com.queen.application.ports.in.UserEmailQuery;
import com.queen.application.ports.out.LoadUserPort;
import com.queen.domain.user.FellaUser;
import reactor.core.publisher.Mono;

public class UserEmailService implements UserEmailQuery {
	private final LoadUserPort loadUserPort;
	private final UserMapper userMapper;

	public UserEmailService(final LoadUserPort loadUserPort, final UserMapper userMapper) {
		this.loadUserPort = loadUserPort;
		this.userMapper = userMapper;
	}

	@Override
	public Mono<FellaUser> getUserByEmail(final String email) {
		return loadUserPort.loadUser(email).map(userMapper::mapToDomain);
	}
}
