package com.queen.configuration;

import com.queen.adapters.persistance.FieldTypeMapper;
import com.queen.adapters.persistance.FieldTypePersistenceAdapter;
import com.queen.adapters.persistance.FieldsMapper;
import com.queen.adapters.persistance.FieldsPersistenceAdapter;
import com.queen.adapters.persistance.MonitorMapper;
import com.queen.adapters.persistance.MonitorPersistenceAdapter;
import com.queen.adapters.persistance.MonitorTypeMapper;
import com.queen.adapters.persistance.MonitorTypePersistenceAdapter;
import com.queen.adapters.persistance.UserMapper;
import com.queen.adapters.persistance.UserPersistenceAdapter;
import com.queen.adapters.web.dto.FieldTypeToDTO;
import com.queen.adapters.web.dto.MonitorToDTO;
import com.queen.adapters.web.dto.MonitorTypeToDTO;
import com.queen.adapters.web.dto.UserToDTO;
import com.queen.application.ports.out.CreateFieldsPort;
import com.queen.application.ports.out.CreateMonitorPort;
import com.queen.application.ports.out.CreateMonitorTypesPort;
import com.queen.application.ports.out.CreateUserPort;
import com.queen.application.ports.out.LoadMonitorTypesPort;
import com.queen.application.ports.out.LoadMonitorsPort;
import com.queen.application.ports.out.LoadFieldTypesPort;
import com.queen.application.ports.out.LoadUserPort;
import com.queen.application.service.AttachUserService;
import com.queen.application.service.MonitorService;
import com.queen.application.service.MonitorTypeService;
import com.queen.application.service.UserService;
import com.queen.infrastructure.persitence.FieldTypesRepository;
import com.queen.infrastructure.persitence.FieldsRepository;
import com.queen.infrastructure.persitence.MonitorRepository;
import com.queen.infrastructure.persitence.MonitorTypeRepository;
import com.queen.infrastructure.persitence.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class FellaConfiguration {
	// Field
	@Bean
	FieldsMapper fieldsMapper() {
		return new FieldsMapper();
	}

	@Bean
	CreateFieldsPort createFieldsPort(final FieldsRepository fieldsRepository) {
		return new FieldsPersistenceAdapter(fieldsRepository);
	}

	// Field Types
	@Bean
	FieldTypeMapper fieldTypeMapper() {
		return new FieldTypeMapper();
	}

	@Bean
	FieldTypeToDTO fieldTypeToDTO() {
		return new FieldTypeToDTO();
	}

	@Bean
	LoadFieldTypesPort loadFieldTypesPort(final FieldTypesRepository fieldTypesRepository) {
		return new FieldTypePersistenceAdapter(fieldTypesRepository);
	}

	// Monitor Types
	@Bean
	MonitorTypeService monitorTypeService(final MonitorTypeMapper monitorTypeMapper, final LoadMonitorTypesPort loadAllMonitorTypes, final CreateMonitorTypesPort createManyMonitorTypesPort, final CreateFieldsPort createFieldsPort, final LoadFieldTypesPort loadFieldTypesPort, final FieldsMapper fieldsMapper, final FieldTypeMapper fieldTypeMapper) {
		return new MonitorTypeService(loadAllMonitorTypes, createManyMonitorTypesPort, createFieldsPort, loadFieldTypesPort, monitorTypeMapper, fieldsMapper, fieldTypeMapper);
	}

	@Bean
	MonitorTypeMapper monitorTypeMapper() {
		return new MonitorTypeMapper();
	}

	@Bean
	LoadMonitorTypesPort loadAllMonitorTypes(final MonitorTypeRepository monitorTypeRepository) {
		return new MonitorTypePersistenceAdapter(monitorTypeRepository);
	}

	@Bean
	CreateMonitorTypesPort createManyMonitorTypesPort(final MonitorTypeRepository monitorTypeRepository) {
		return new MonitorTypePersistenceAdapter(monitorTypeRepository);
	}



	@Bean
	MonitorTypeToDTO monitorTypeToDTO(final FieldTypeToDTO fieldTypeToDTO) {
		return new MonitorTypeToDTO(fieldTypeToDTO);
	}

	// Monitors
	@Bean
	MonitorService monitorService(final MonitorMapper monitorMapper, final LoadMonitorsPort loadAllMonitors, final CreateMonitorPort createMonitorPort) {
		return new MonitorService(loadAllMonitors, monitorMapper, createMonitorPort);
	}

	@Bean
	MonitorMapper monitorMapper() {
		return new MonitorMapper();
	}

	@Bean
    LoadMonitorsPort loadMonitors(final MonitorRepository monitorRepository) {
		return new MonitorPersistenceAdapter(monitorRepository);
	}

	@Bean
	CreateMonitorPort createMonitorPort(final MonitorRepository monitorRepository) {
		return new MonitorPersistenceAdapter(monitorRepository);
	}

	@Bean
	MonitorToDTO monitorToDTO() {
		return new MonitorToDTO();
	}


	// User
	@Bean
	UserMapper userMapper() {
		return new UserMapper();
	}

	@Bean
	@Primary
	LoadUserPort loadUser(final UserRepository userRepository) {
		return new UserPersistenceAdapter(userRepository);
	}

	@Bean
	CreateUserPort createUser(final UserRepository userRepository) {
		return new UserPersistenceAdapter(userRepository);
	}

	@Bean
	AttachUserService attachNewUserService(final LoadUserPort loadUser, final UserMapper userMapper) {
		return new AttachUserService(loadUser, userMapper);
	}

	@Bean
	UserToDTO userToDTO() {
		return new UserToDTO();
	}

	@Bean
	UserService userService(final LoadUserPort loadUserPort, final UserMapper userMapper, final MonitorTypeService monitorTypeService, final CreateUserPort createUserPort) {
		return new UserService(loadUserPort, userMapper, monitorTypeService, createUserPort);
	}
}
