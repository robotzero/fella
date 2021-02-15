package com.queen.application.service;

import com.queen.adapters.persistance.UserMapper;
import com.queen.application.ports.in.AttachNewUserCommand;
import com.queen.application.ports.in.AttachNewUserDetails;
import com.queen.application.ports.out.CreateUser;
import com.queen.application.ports.out.LoadUser;

import java.util.NoSuchElementException;
import java.util.UUID;

public class AttachNewUserService implements AttachNewUserDetails {
	private final LoadUser loadUser;
	private final CreateUser createUser;
	private final UserMapper userMapper;

	public AttachNewUserService(final LoadUser loadUser, final CreateUser createUser, final UserMapper userMapper) {
		this.loadUser = loadUser;
		this.createUser = createUser;
		this.userMapper = userMapper;
	}

	@Override
	public void attachNewUserDetails(AttachNewUserCommand createNewUserCommand) {
		final var disposable = this.loadUser.loadUser(createNewUserCommand.jwtAuthenticationToken().getName()).single().subscribe((user) -> {
			createNewUserCommand.jwtAuthenticationToken().setDetails(userMapper.mapToDomain(user));
		}, (throwable) -> {
			if (throwable instanceof NoSuchElementException) {
				final var user = new com.queen.infrastructure.persitence.User(UUID.randomUUID().toString(), createNewUserCommand.jwtAuthenticationToken().getName());
				createNewUserCommand.jwtAuthenticationToken().setDetails(userMapper.mapToDomain(user));
				createUser.createUser(user);
			} else {
				throw new RuntimeException("Error", throwable);
			}
		});
		disposable.dispose();
	}
}
