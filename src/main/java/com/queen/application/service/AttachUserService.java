package com.queen.application.service;

import com.queen.adapters.persistance.UserMapper;
import com.queen.application.ports.in.AttachUserCommand;
import com.queen.application.ports.out.LoadUserPort;
import com.queen.infrastructure.persitence.User;

public class AttachUserService implements com.queen.application.ports.in.AttachNewUserUseCase {
	private final LoadUserPort loadUser;
	private final UserMapper userMapper;

	public AttachUserService(final LoadUserPort loadUser, final UserMapper userMapper) {
		this.loadUser = loadUser;
		this.userMapper = userMapper;
	}

	@Override
	public void attachNewUserDetails(final AttachUserCommand attachNewUserCommand) {
		final var disposable = this.loadUser.loadUser(attachNewUserCommand.jwtAuthenticationToken().getName()).single().subscribe((user) -> {
			attachUserDetailsToToken(user, attachNewUserCommand);
		}, (throwable) -> {
			throw new RuntimeException("Unknown Error");
		});
		disposable.dispose();
	}

	private void attachUserDetailsToToken(final User user, final AttachUserCommand attachNewUserCommand) {
		attachNewUserCommand.jwtAuthenticationToken().setDetails(userMapper.mapToDomain(user));
	}
}
