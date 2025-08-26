package com.queen.configuration;

import com.queen.adapters.persistance.DailyTrackingPersistenceAdapter;
import com.queen.adapters.persistance.MigrainePersistenceAdapter;
import com.queen.adapters.persistance.PeriodPersistenceAdapter;
import com.queen.adapters.persistance.UserMapper;
import com.queen.adapters.persistance.UserPersistenceAdapter;
import com.queen.adapters.web.dto.DailyTrackingMapper;
import com.queen.adapters.web.dto.MigraineMapper;
import com.queen.adapters.web.dto.PeriodMapper;
import com.queen.adapters.web.dto.UserToDTO;
import com.queen.application.ports.out.CreateUserPort;
import com.queen.application.ports.out.LoadUserPort;
import com.queen.application.service.AttachUserService;
import com.queen.application.service.PeriodService;
import com.queen.application.service.UserService;
import com.queen.domain.DailyTrackingPersistencePort;
import com.queen.domain.MigrainePersistencePort;
import com.queen.domain.PeriodPersistencePort;
import com.queen.infrastructure.persistence.DailyTrackingRepository;
import com.queen.infrastructure.persistence.MigraineRepository;
import com.queen.infrastructure.persistence.PeriodRepository;
import com.queen.infrastructure.persistence.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.web.client.RestClient;

@Configuration
@EnableWebSecurity
public class FellaConfiguration {

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

	@Bean
	JwtAuthenticationConverter jwtAuthenticationConverter() {
		return new JwtAuthenticationConverter();
	}

//	@Bean
//	public RestClient restClient(RestClient.Builder builder) {
//		return builder
//				.baseUrl("http://192.168.0.33:8080")
//				.build();
//	}
}
