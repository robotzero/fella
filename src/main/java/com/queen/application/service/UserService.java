package com.queen.application.service;

import com.queen.adapters.persistance.UserMapper;
import com.queen.application.ports.in.CreateMonitorTypeCommand;
import com.queen.application.ports.in.CreateUserCommand;
import com.queen.application.ports.in.CreateUserUseCase;
import com.queen.application.ports.in.UserEmailQuery;
import com.queen.application.ports.out.CreateUserPort;
import com.queen.application.ports.out.LoadSpringUserPort;
import com.queen.application.ports.out.LoadUserPort;
import com.queen.application.service.dto.FieldsDTO;
import com.queen.application.service.dto.MonitorTypeDTO;
import com.queen.application.service.exception.UserServiceException;
import com.queen.domain.user.FellaUser;
import com.queen.infrastructure.persitence.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

public class UserService implements LoadSpringUserPort, CreateUserUseCase, UserEmailQuery {
	private final LoadUserPort loadUserPort;
	private final UserMapper userMapper;
	private final MonitorTypeService monitorTypeService;
	private final CreateUserPort createUserPort;
	private static String PERIOD = "Period";
	private static String STOMACH = "Stomach";

	public UserService(final LoadUserPort loadUserPort, final UserMapper userMapper, final MonitorTypeService monitorTypeService, final CreateUserPort createUserPort) {
		this.loadUserPort = loadUserPort;
		this.userMapper = userMapper;
		this.monitorTypeService = monitorTypeService;
		this.createUserPort = createUserPort;
	}

	@Override
	public Mono<UserDetails> findByUsername(final String username) {
		return loadUserPort.loadUser(username).map(userMapper::mapToDomain);
	}

	@Override
	@Transactional
	public Mono<FellaUser> createUser(final CreateUserCommand createUserCommand) {
		return loadUserPort.loadUser(createUserCommand.username()).map(userMapper::mapToDomain).switchIfEmpty(Mono.defer(() -> {
			final String monitorTypeIdPeriod = UUID.randomUUID().toString();
			final String monitorTypeIdStomach = UUID.randomUUID().toString();

			final FieldsDTO field1 = new FieldsDTO(UUID.randomUUID().toString(), monitorTypeIdPeriod, "1");
			final FieldsDTO field2 = new FieldsDTO(UUID.randomUUID().toString(), monitorTypeIdPeriod, "2");
			final FieldsDTO field3 = new FieldsDTO(UUID.randomUUID().toString(), monitorTypeIdPeriod, "3");
			final FieldsDTO field4 = new FieldsDTO(UUID.randomUUID().toString(), monitorTypeIdPeriod, "4");
			final FieldsDTO field5 = new FieldsDTO(UUID.randomUUID().toString(), monitorTypeIdPeriod, "5");

			final FieldsDTO field6 = new FieldsDTO(UUID.randomUUID().toString(), monitorTypeIdStomach, "1");
			final FieldsDTO field7 = new FieldsDTO(UUID.randomUUID().toString(), monitorTypeIdStomach, "2");
			final FieldsDTO field8 = new FieldsDTO(UUID.randomUUID().toString(), monitorTypeIdStomach, "4");
			final FieldsDTO field9 = new FieldsDTO(UUID.randomUUID().toString(), monitorTypeIdStomach, "5");

			final var userId = UUID.randomUUID().toString();

			final MonitorTypeDTO monitorTypePeriod = new MonitorTypeDTO(monitorTypeIdPeriod, PERIOD, List.of(field1, field2, field3, field4, field5), userId);
			final MonitorTypeDTO monitorTypeStomach = new MonitorTypeDTO(monitorTypeIdStomach, STOMACH, List.of(field6, field7, field8, field9), userId);

			final var user = createUserPort.createUser(new User(userId, createUserCommand.username())).map(userMapper::mapToDomain).doOnError(error -> {
				throw new UserServiceException("Failed to create new user", error);
			});

			return monitorTypeService.createManyMonitorTypes(new CreateMonitorTypeCommand(List.of(monitorTypePeriod, monitorTypeStomach), userId)).collectList().then(user);
		}));
	}

	@Override
	public Mono<FellaUser> getUserByEmail(String email) {
		return loadUserPort.loadUser(email).map(userMapper::mapToDomain);
	}
}
