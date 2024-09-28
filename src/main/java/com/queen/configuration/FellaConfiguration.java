package com.queen.configuration;

import com.queen.adapters.persistance.DailyTrackingPersistenceAdapter;
import com.queen.adapters.persistance.FieldTypeMapper;
import com.queen.adapters.persistance.FieldTypePersistenceAdapter;
import com.queen.adapters.persistance.FieldsMapper;
import com.queen.adapters.persistance.FieldsPersistenceAdapter;
import com.queen.adapters.persistance.MigrainePersistenceAdapter;
import com.queen.adapters.persistance.PeriodMonitorMapper;
import com.queen.adapters.persistance.PeriodMonitorPersistenceAdapter;
import com.queen.adapters.persistance.MonitorTypeMapper;
import com.queen.adapters.persistance.MonitorTypePersistenceAdapter;
import com.queen.adapters.persistance.PeriodPersistenceAdapter;
import com.queen.adapters.persistance.UserMapper;
import com.queen.adapters.persistance.UserPersistenceAdapter;
import com.queen.adapters.web.dto.DailyTrackingMapper;
import com.queen.adapters.web.dto.FieldTypeToDTO;
import com.queen.adapters.web.dto.MigraineMapper;
import com.queen.adapters.web.dto.PeriodMonitorToDTO;
import com.queen.adapters.web.dto.MonitorTypeToDTO;
import com.queen.adapters.web.dto.PeriodMapper;
import com.queen.adapters.web.dto.UserToDTO;
import com.queen.application.ports.out.CreateFieldsPort;
import com.queen.application.ports.out.CreatePeriodMonitorPort;
import com.queen.application.ports.out.CreateMonitorTypesPort;
import com.queen.application.ports.out.CreateUserPort;
import com.queen.application.ports.out.LoadFieldTypesPort;
import com.queen.application.ports.out.LoadMonitorTypesPort;
import com.queen.application.ports.out.LoadPeriodMonitorsPort;
import com.queen.application.ports.out.LoadUserPort;
import com.queen.application.service.AttachUserService;
import com.queen.application.service.MonitorTypeService;
import com.queen.application.service.PeriodMonitorService;
import com.queen.application.service.PeriodService;
import com.queen.application.service.UserService;
import com.queen.domain.DailyTrackingPersistencePort;
import com.queen.domain.MigrainePersistencePort;
import com.queen.domain.PeriodPersistencePort;
import com.queen.infrastructure.persistence.DailyTrackingRepository;
import com.queen.infrastructure.persistence.FieldTypesRepository;
import com.queen.infrastructure.persistence.FieldsRepository;
import com.queen.infrastructure.persistence.MigraineRepository;
import com.queen.infrastructure.persistence.MonitorTypeRepository;
import com.queen.infrastructure.persistence.PeriodRepository;
import com.queen.infrastructure.persistence.UserRepository;
import com.queen.infrastructure.persistence.monitor.PeriodMonitorRepository;
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
	PeriodMonitorService periodMonitorService(final PeriodMonitorMapper monitorMapper, final LoadPeriodMonitorsPort loadAllMonitors, final CreatePeriodMonitorPort createMonitorPort) {
		return new PeriodMonitorService(loadAllMonitors, monitorMapper, createMonitorPort);
	}

	@Bean
	PeriodMonitorMapper monitorMapper() {
		return new PeriodMonitorMapper();
	}

	@Bean
	LoadPeriodMonitorsPort loadMonitors(final PeriodMonitorRepository periodMonitorRepository) {
		return new PeriodMonitorPersistenceAdapter(periodMonitorRepository);
	}

	@Bean
	CreatePeriodMonitorPort createMonitorPort(final PeriodMonitorRepository periodMonitorRepository) {
		return new PeriodMonitorPersistenceAdapter(periodMonitorRepository);
	}

	@Bean
  	PeriodMonitorToDTO monitorToDTO(final MonitorTypeToDTO monitorTypeToDTO) {
		return new PeriodMonitorToDTO(monitorTypeToDTO);
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
	UserService userService(final LoadUserPort loadUserPort, final UserMapper userMapper, final CreateUserPort createUserPort) {
		return new UserService(loadUserPort, userMapper, createUserPort);
	}

	@Bean
	PeriodMapper periodToDTOMapper(final MigraineMapper migraineMapper, final DailyTrackingMapper dailyTrackingMapper) {
		return new PeriodMapper(migraineMapper, dailyTrackingMapper);
	}

	@Bean
	PeriodService periodService(
			final PeriodPersistencePort periodPersistencePort,
			final MigrainePersistencePort migrainePersistencePort,
			final PeriodMapper periodMapper,
			final MigraineMapper migraineMapper,
			final DailyTrackingMapper dailyTrackingMapper,
			final DailyTrackingPersistencePort dailyTrackingPersistencePort
	) {
		return new PeriodService(periodPersistencePort, migrainePersistencePort, periodMapper, migraineMapper, dailyTrackingMapper, dailyTrackingPersistencePort);
	}

	@Bean
	PeriodPersistencePort periodPersistencePort(final PeriodRepository periodMonitorRepository) {
		return new PeriodPersistenceAdapter(periodMonitorRepository);
	}

	@Bean
	MigraineMapper migraineMapper() {
		return new MigraineMapper();
	}

	@Bean
	MigrainePersistencePort migrainePersistencePort(final MigraineRepository migraineRepository) {
		return new MigrainePersistenceAdapter(migraineRepository);
	}

	@Bean
	DailyTrackingMapper dailyTrackingMapper() {
		return new DailyTrackingMapper();
	}

	@Bean
	DailyTrackingPersistencePort dailyTrackingPersistencePort(final DailyTrackingRepository dailyTrackingRepository) {
		return new DailyTrackingPersistenceAdapter(dailyTrackingRepository);
	}
}
