package com.queen.application.service;

import com.queen.adapters.persistance.UserMapper;
import com.queen.adapters.web.FieldsDTO;
import com.queen.adapters.web.MonitorTypeDTO;
import com.queen.application.ports.in.AttachNewUserCommand;
import com.queen.application.ports.in.CreateUserTemplateEvent;
import com.queen.application.ports.out.CreateUserPort;
import com.queen.application.ports.out.LoadUserPort;
import com.queen.infrastructure.persitence.User;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public class AttachNewUserService implements com.queen.application.ports.in.AttachNewUserUseCase {
	private final LoadUserPort loadUser;
	private final CreateUserPort createUser;
	private final CreateUserTemplateEvent createUserTemplateUseCase;
	private final UserMapper userMapper;

	public AttachNewUserService(final LoadUserPort loadUser, final CreateUserPort createUser, final UserMapper userMapper, final CreateUserTemplateEvent createUserTemplateUseCase) {
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
				//@TODO delegate somewhere else
				String monitorTypeIdPeriod = UUID.randomUUID().toString();
				String monitorTypeIdStomach = UUID.randomUUID().toString();

				FieldsDTO fieldType1 = new FieldsDTO(UUID.randomUUID().toString(), monitorTypeIdPeriod, "1");
				FieldsDTO fieldType2 = new FieldsDTO(UUID.randomUUID().toString(), monitorTypeIdPeriod, "2");
				FieldsDTO fieldType3 = new FieldsDTO(UUID.randomUUID().toString(), monitorTypeIdPeriod, "3");
				FieldsDTO fieldType4 = new FieldsDTO(UUID.randomUUID().toString(), monitorTypeIdPeriod, "4");
				FieldsDTO fieldType5 = new FieldsDTO(UUID.randomUUID().toString(), monitorTypeIdPeriod, "5");

				FieldsDTO fieldType6 = new FieldsDTO(UUID.randomUUID().toString(), monitorTypeIdStomach, "1");
				FieldsDTO fieldType7 = new FieldsDTO(UUID.randomUUID().toString(), monitorTypeIdStomach, "2");
				FieldsDTO fieldType8 = new FieldsDTO(UUID.randomUUID().toString(), monitorTypeIdStomach, "3");
				FieldsDTO fieldType9 = new FieldsDTO(UUID.randomUUID().toString(), monitorTypeIdStomach, "5");

				MonitorTypeDTO monitorTypePeriod  = new MonitorTypeDTO(monitorTypeIdPeriod, "Period", user.getId(), List.of(fieldType1, fieldType2, fieldType3, fieldType4, fieldType5));
				MonitorTypeDTO monitorTypeStomach = new MonitorTypeDTO(monitorTypeIdStomach, "Stomach", user.getId(), List.of(fieldType6, fieldType7, fieldType8, fieldType9));
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
