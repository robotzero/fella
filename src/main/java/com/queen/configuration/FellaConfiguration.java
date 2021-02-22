package com.queen.configuration;

import com.queen.adapters.event.StartUpTemplateEventHandler;
import com.queen.adapters.persistance.FieldTypeMapper;
import com.queen.adapters.persistance.FieldTypePersistenceAdapter;
import com.queen.adapters.persistance.FieldsMapper;
import com.queen.adapters.persistance.FieldsPersistenceAdapter;
import com.queen.adapters.persistance.MonitorTypePersistenceAdapter;
import com.queen.adapters.persistance.MonitorPersistenceAdapter;
import com.queen.adapters.persistance.MonitorMapper;
import com.queen.adapters.persistance.MonitorTypeMapper;
import com.queen.adapters.persistance.UserMapper;
import com.queen.adapters.persistance.UserPersistenceAdapter;
import com.queen.adapters.web.FieldTypeToDTO;
import com.queen.adapters.web.MonitorToDTO;
import com.queen.adapters.web.MonitorTypeToDTO;
import com.queen.application.ports.in.CreateUserTemplateEvent;
import com.queen.application.ports.out.CreateFieldsPort;
import com.queen.application.ports.out.CreateManyMonitorTypesPort;
import com.queen.application.ports.out.CreateMonitorTypePort;
import com.queen.application.ports.out.CreateUserPort;
import com.queen.application.ports.out.LoadAllMonitorTypesPort;
import com.queen.application.ports.out.LoadAllMonitorsPort;
import com.queen.application.ports.out.LoadFieldTypesPort;
import com.queen.application.ports.out.LoadUserPort;
import com.queen.application.service.AttachNewUserService;
import com.queen.application.service.MonitorService;
import com.queen.application.service.MonitorTypeService;
import com.queen.infrastructure.persitence.FieldTypesRepository;
import com.queen.infrastructure.persitence.FieldsRepository;
import com.queen.infrastructure.persitence.MonitorRepository;
import com.queen.infrastructure.persitence.MonitorTypeRepository;
import com.queen.infrastructure.persitence.UserRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
	MonitorTypeService monitorTypeService(final MonitorTypeMapper monitorTypeMapper, final ApplicationEventPublisher applicationEventPublisher, final LoadAllMonitorTypesPort loadAllMonitorTypes, final CreateMonitorTypePort createMonitorTypePort, final CreateManyMonitorTypesPort createManyMonitorTypesPort, final CreateFieldsPort createFieldsPort, final LoadFieldTypesPort loadFieldTypesPort, final FieldsMapper fieldsMapper, final FieldTypeMapper fieldTypeMapper) {
		return new MonitorTypeService(loadAllMonitorTypes, applicationEventPublisher, createMonitorTypePort, createManyMonitorTypesPort, createFieldsPort, loadFieldTypesPort, monitorTypeMapper, fieldsMapper, fieldTypeMapper);
	}

	@Bean
	MonitorTypeMapper monitorTypeMapper() {
		return new MonitorTypeMapper();
	}

	@Bean
	LoadAllMonitorTypesPort loadAllMonitorTypes(final MonitorTypeRepository monitorTypeRepository) {
		return new MonitorTypePersistenceAdapter(monitorTypeRepository);
	}

	@Bean
	CreateMonitorTypePort createMonitorTypePort(final MonitorTypeRepository monitorTypeRepository) {
		return new MonitorTypePersistenceAdapter(monitorTypeRepository);
	}

	@Bean
	CreateManyMonitorTypesPort createManyMonitorTypesPort(final MonitorTypeRepository monitorTypeRepository) {
		return new MonitorTypePersistenceAdapter(monitorTypeRepository);
	}

	@Bean
	CreateUserTemplateEvent createUserTemplateUseCase(final MonitorTypeMapper monitorTypeMapper, final ApplicationEventPublisher applicationEventPublisher, final LoadAllMonitorTypesPort loadAllMonitorTypes, final CreateMonitorTypePort createMonitorTypePort, final CreateManyMonitorTypesPort createManyMonitorTypesPort, final CreateFieldsPort createFieldsPort, final LoadFieldTypesPort loadFieldTypesPort, final FieldsMapper fieldsMapper, final FieldTypeMapper fieldTypeMapper) {
		return new MonitorTypeService(loadAllMonitorTypes, applicationEventPublisher, createMonitorTypePort, createManyMonitorTypesPort, createFieldsPort, loadFieldTypesPort, monitorTypeMapper, fieldsMapper, fieldTypeMapper);
	}

	@Bean
	MonitorTypeToDTO monitorTypeToDTO(final FieldTypeToDTO fieldTypeToDTO) {
		return new MonitorTypeToDTO(fieldTypeToDTO);
	}

	// Monitors
	@Bean
	MonitorService monitorService(final MonitorMapper monitorMapper, final LoadAllMonitorsPort loadAllMonitors) {
		return new MonitorService(loadAllMonitors, monitorMapper);
	}

	@Bean
	MonitorMapper monitorMapper() {
		return new MonitorMapper();
	}

	@Bean
	LoadAllMonitorsPort loadAllMonitors(final MonitorRepository monitorRepository) {
		return new MonitorPersistenceAdapter(monitorRepository);
	}

	@Bean
	MonitorToDTO monitorToDTO() {
		return new MonitorToDTO();
	}

	@Bean
	UserMapper userMapper() {
		return new UserMapper();
	}

	@Bean
	LoadUserPort loadUser(final UserRepository userRepository) {
		return new UserPersistenceAdapter(userRepository);
	}

	@Bean
	CreateUserPort createUser(final UserRepository userRepository) {
		return new UserPersistenceAdapter(userRepository);
	}

	@Bean
	AttachNewUserService attachNewUserService(final LoadUserPort loadUser, final CreateUserPort createUser, final UserMapper userMapper, final CreateUserTemplateEvent createUserTemplateUseCase) {
		return new AttachNewUserService(loadUser, createUser, userMapper, createUserTemplateUseCase);
	}

	@Bean
	StartUpTemplateEventHandler genericEventHandler(final MonitorTypeService monitorTypeService) {
		return new StartUpTemplateEventHandler(monitorTypeService);
	}
}
