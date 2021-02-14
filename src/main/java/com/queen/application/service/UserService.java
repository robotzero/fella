package com.queen.application.service;

import com.queen.adapters.persistance.UserMapper;
import com.queen.application.ports.in.UserQuery;
import com.queen.application.ports.out.LoadUser;
import com.queen.domain.user.User;
import reactor.core.publisher.Mono;

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
}
