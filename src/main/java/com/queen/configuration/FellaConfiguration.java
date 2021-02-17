package com.queen.configuration;

import com.queen.adapters.persistance.MonitorTypePersistenceAdapter;
import com.queen.adapters.persistance.MonitorPersistenceAdapter;
import com.queen.adapters.persistance.MonitorMapper;
import com.queen.adapters.persistance.MonitorTypeMapper;
import com.queen.adapters.persistance.UserMapper;
import com.queen.adapters.persistance.UserPersistenceAdapter;
import com.queen.adapters.web.MonitorToDTO;
import com.queen.adapters.web.MonitorTypeToDTO;
import com.queen.application.ports.out.CreateMonitorTypePort;
import com.queen.application.ports.out.CreateUserPort;
import com.queen.application.ports.out.LoadAllMonitorTypesPort;
import com.queen.application.ports.out.LoadAllMonitorsPort;
import com.queen.application.ports.out.LoadUserPort;
import com.queen.application.service.AttachNewUserService;
import com.queen.application.service.MonitorService;
import com.queen.application.service.MonitorTypeService;
import com.queen.infrastructure.persitence.MonitorRepository;
import com.queen.infrastructure.persitence.MonitorTypeRepository;
import com.queen.infrastructure.persitence.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FellaConfiguration {
	// Monitor Types
	@Bean
	MonitorTypeService monitorTypeService(final MonitorTypeMapper monitorTypeMapper, final LoadAllMonitorTypesPort loadAllMonitorTypes) {
		return new MonitorTypeService(loadAllMonitorTypes, monitorTypeMapper);
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
	MonitorTypeToDTO monitorTypeToDTO() {
		return new MonitorTypeToDTO();
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
	AttachNewUserService attachNewUserService(final LoadUserPort loadUser, final CreateUserPort createUser, final UserMapper userMapper, final CreateMonitorTypePort createMonitorTypePort) {
		return new AttachNewUserService(loadUser, createUser, userMapper, createMonitorTypePort);
	}
}
