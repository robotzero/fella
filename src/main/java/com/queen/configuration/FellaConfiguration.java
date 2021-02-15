package com.queen.configuration;

import com.queen.adapters.persistance.LoadMonitorTypes;
import com.queen.adapters.persistance.LoadMonitors;
import com.queen.adapters.persistance.MonitorMapper;
import com.queen.adapters.persistance.MonitorTypeMapper;
import com.queen.adapters.persistance.UserMapper;
import com.queen.adapters.web.MonitorToDTO;
import com.queen.adapters.web.MonitorTypeToDTO;
import com.queen.application.ports.out.CreateUser;
import com.queen.application.ports.out.LoadAllMonitorTypes;
import com.queen.application.ports.out.LoadAllMonitors;
import com.queen.application.ports.out.LoadUser;
import com.queen.application.service.AttachNewUserService;
import com.queen.application.service.MonitorService;
import com.queen.application.service.MonitorTypeService;
import com.queen.application.service.UserService;
import com.queen.infrastructure.persitence.MonitorRepository;
import com.queen.infrastructure.persitence.MonitorTypeRepository;
import com.queen.infrastructure.persitence.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FellaConfiguration {
	// Monitor Types
	@Bean
	MonitorTypeService monitorTypeService(final MonitorTypeMapper monitorTypeMapper, final LoadAllMonitorTypes loadAllMonitorTypes) {
		return new MonitorTypeService(loadAllMonitorTypes, monitorTypeMapper);
	}

	@Bean
	MonitorTypeMapper monitorTypeMapper() {
		return new MonitorTypeMapper();
	}

	@Bean
	LoadAllMonitorTypes loadAllMonitorTypes(final MonitorTypeRepository monitorTypeRepository) {
		return new LoadMonitorTypes(monitorTypeRepository);
	}

	@Bean
	MonitorTypeToDTO monitorTypeToDTO() {
		return new MonitorTypeToDTO();
	}

	// Monitors
	@Bean
	MonitorService monitorService(final MonitorMapper monitorMapper, final LoadAllMonitors loadAllMonitors) {
		return new MonitorService(loadAllMonitors, monitorMapper);
	}

	@Bean
	MonitorMapper monitorMapper() {
		return new MonitorMapper();
	}

	@Bean
	LoadAllMonitors loadAllMonitors(final MonitorRepository monitorRepository) {
		return new LoadMonitors(monitorRepository);
	}

	@Bean
	MonitorToDTO monitorToDTO() {
		return new MonitorToDTO();
	}

	// User
	@Bean
	UserService userService(final UserMapper userMapper, final LoadUser loadUser) {
		return new UserService(loadUser, userMapper);
	}

	@Bean
	UserMapper userMapper() {
		return new UserMapper();
	}

	@Bean
	LoadUser loadUser(final UserRepository userRepository) {
		return new com.queen.adapters.persistance.LoadUser(userRepository);
	}

	@Bean
	CreateUser createUser(final UserRepository userRepository) {
		return new com.queen.adapters.persistance.CreateUser(userRepository);
	}

	@Bean
	AttachNewUserService attachNewUserService(final LoadUser loadUser, final CreateUser createUser, final UserMapper userMapper) {
		return new AttachNewUserService(loadUser, createUser, userMapper);
	}
}
