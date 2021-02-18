package com.queen.application.service;

import com.queen.adapters.persistance.UserMapper;
import com.queen.adapters.web.MonitorTypeDTO;
import com.queen.application.ports.in.AttachNewUserCommand;
import com.queen.application.ports.in.CreateUserTemplateUseCase;
import com.queen.application.ports.out.CreateUserPort;
import com.queen.application.ports.out.LoadUserPort;
import com.queen.infrastructure.persitence.User;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public class AttachNewUserService implements com.queen.application.ports.in.AttachNewUserUseCase {
	private final LoadUserPort loadUser;
	private final CreateUserPort createUser;
	private final CreateUserTemplateUseCase createUserTemplateUseCase;
	private final UserMapper userMapper;

	public AttachNewUserService(final LoadUserPort loadUser, final CreateUserPort createUser, final UserMapper userMapper, final CreateUserTemplateUseCase createUserTemplateUseCase) {
		this.loadUser = loadUser;
		this.createUser = createUser;
		this.userMapper = userMapper;
		this.createUserTemplateUseCase = createUserTemplateUseCase;
	}

	@Override
	public void attachNewUserDetails(final AttachNewUserCommand createNewUserCommand) {
		final var disposable = this.loadUser.loadUser(createNewUserCommand.jwtAuthenticationToken().getName()).single().subscribe((user) -> {
			attachUserDetailsToToken(user, createNewUserCommand);
		}, (throwable) -> {
			if (throwable instanceof NoSuchElementException) {
				final var user = new com.queen.infrastructure.persitence.User(UUID.randomUUID().toString(), createNewUserCommand.jwtAuthenticationToken().getName());
				attachUserDetailsToToken(user, createNewUserCommand);
				createUser.createUser(user);
				MonitorTypeDTO monitorTypePeriod  = new MonitorTypeDTO(UUID.randomUUID().toString(), "Period", user.getId());
				MonitorTypeDTO monitorTypeStomach = new MonitorTypeDTO(UUID.randomUUID().toString(), "Stomach", user.getId());
				createUserTemplateUseCase.publishCreateUserTemplateEvent(List.of(monitorTypePeriod, monitorTypeStomach));
			} else {
				throw new RuntimeException("Error", throwable);
			}
		});
		disposable.dispose();
	}

	private void attachUserDetailsToToken(final User user, final AttachNewUserCommand attachNewUserCommand) {
		attachNewUserCommand.jwtAuthenticationToken().setDetails(userMapper.mapToDomain(user));
	}
}
