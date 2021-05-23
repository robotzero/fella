package com.queen.application.service;

import com.queen.adapters.persistance.UserMapper;
import com.queen.adapters.web.dto.FieldsDTO;
import com.queen.adapters.web.dto.MonitorTypeDTO;
import com.queen.application.ports.in.CreateMonitorTypeCommand;
import com.queen.application.ports.in.CreateUserCommand;
import com.queen.application.ports.in.CreateUserUseCase;
import com.queen.application.ports.in.UserEmailQuery;
import com.queen.application.ports.out.CreateUserPort;
import com.queen.application.ports.out.LoadSpringUserPort;
import com.queen.application.ports.out.LoadUserPort;
import com.queen.configuration.FellaJwtAuthenticationToken;
import com.queen.domain.user.FellaUser;
import com.queen.infrastructure.persitence.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
	public Mono<FellaUser> createUser(final CreateUserCommand createUserCommand) {
		return loadUserPort.loadUser(createUserCommand.username()).map(userMapper::mapToDomain).switchIfEmpty(Mono.defer(() -> {
			final String monitorTypeIdPeriod = UUID.randomUUID().toString();
			final String monitorTypeIdStomach = UUID.randomUUID().toString();

			final FieldsDTO fieldType1 = new FieldsDTO(UUID.randomUUID().toString(), monitorTypeIdPeriod, "1");
			final FieldsDTO fieldType2 = new FieldsDTO(UUID.randomUUID().toString(), monitorTypeIdPeriod, "2");
			final FieldsDTO fieldType3 = new FieldsDTO(UUID.randomUUID().toString(), monitorTypeIdPeriod, "3");
			final FieldsDTO fieldType4 = new FieldsDTO(UUID.randomUUID().toString(), monitorTypeIdPeriod, "4");
			final FieldsDTO fieldType5 = new FieldsDTO(UUID.randomUUID().toString(), monitorTypeIdPeriod, "5");

			final FieldsDTO fieldType6 = new FieldsDTO(UUID.randomUUID().toString(), monitorTypeIdStomach, "1");
			final FieldsDTO fieldType7 = new FieldsDTO(UUID.randomUUID().toString(), monitorTypeIdStomach, "2");
			final FieldsDTO fieldType8 = new FieldsDTO(UUID.randomUUID().toString(), monitorTypeIdStomach, "4");
			final FieldsDTO fieldType9 = new FieldsDTO(UUID.randomUUID().toString(), monitorTypeIdStomach, "5");

			final var userId = UUID.randomUUID().toString();

			final MonitorTypeDTO monitorTypePeriod = new MonitorTypeDTO(monitorTypeIdPeriod, PERIOD, List.of(fieldType1, fieldType2, fieldType3, fieldType4, fieldType5), List.of(), userId);
			final MonitorTypeDTO monitorTypeStomach = new MonitorTypeDTO(monitorTypeIdStomach, STOMACH, List.of(fieldType6, fieldType7, fieldType8, fieldType9), List.of(), userId);

			final var user = createUserPort.createUser(new User(userId, createUserCommand.username())).map(userMapper::mapToDomain);
			final var authentication = SecurityContextHolder.getContext().getAuthentication();
			FellaJwtAuthenticationToken token = (FellaJwtAuthenticationToken) authentication;
			token.setUserId(userId);
			//TODO change to ports maybe?
			monitorTypeService.createManyMonitorTypes(new CreateMonitorTypeCommand(List.of(monitorTypePeriod, monitorTypeStomach)));
			return user;
		}));
	}

	@Override
	public Mono<FellaUser> getUserByEmail(String email) {
		return loadUserPort.loadUser(email).map(userMapper::mapToDomain);
	}
}
