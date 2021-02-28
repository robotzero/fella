package com.queen.application.service;

import com.queen.adapters.persistance.UserMapper;
import com.queen.application.ports.out.LoadSpringUserPort;
import com.queen.application.ports.out.LoadUserPort;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

public class UserService implements LoadSpringUserPort {
	private final LoadUserPort loadUserPort;
	private final UserMapper userMapper;

	public UserService(final LoadUserPort loadUserPort, final UserMapper userMapper) {
		this.loadUserPort = loadUserPort;
		this.userMapper = userMapper;
	}

	@Override
	public Mono<UserDetails> findByUsername(String username) {
		return loadUserPort.loadUser(username).map(userMapper::mapToDomain);
	}
}
