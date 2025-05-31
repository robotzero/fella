package com.queen.application.service;

import com.queen.adapters.persistance.UserMapper;
import com.queen.application.ports.in.AttachUserCommand;
import com.queen.application.ports.out.LoadUserPort;
import com.queen.domain.user.FellaUser;

public class AttachUserService implements com.queen.application.ports.in.AttachNewUserUseCase {
	private final LoadUserPort loadUser;
	private final UserMapper userMapper;

	public AttachUserService(final LoadUserPort loadUser, final UserMapper userMapper) {
		this.loadUser = loadUser;
		this.userMapper = userMapper;
	}

	@Override
	public FellaUser attachNewUserDetails(final AttachUserCommand attachNewUserCommand) {
		return userMapper.mapToDomain(this.loadUser.loadUser(attachNewUserCommand.jwt().getSubject()).orElseThrow());
	}
}
