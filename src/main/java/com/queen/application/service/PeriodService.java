package com.queen.application.service;

import com.queen.adapters.web.dto.PeriodDTO;
import com.queen.domain.Migraine;
import com.queen.domain.MigraineMapperPort;
import com.queen.domain.MigrainePersistencePort;
import com.queen.domain.Period;
import com.queen.domain.PeriodMapperPort;
import com.queen.domain.PeriodPersistencePort;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

public class PeriodService {
	private final PeriodPersistencePort periodPersistencePort;
	private final MigrainePersistencePort migrainePersistencePort;
	private final PeriodMapperPort periodMapper;
	private final MigraineMapperPort migraineMapper;

	public PeriodService(
			final PeriodPersistencePort periodPersistencePort,
			final MigrainePersistencePort migrainePersistencePort,
			final PeriodMapperPort periodMapper,
			final MigraineMapperPort migraineMapper
	) {
		this.periodPersistencePort = periodPersistencePort;
		this.migrainePersistencePort = migrainePersistencePort;
		this.periodMapper = periodMapper;
		this.migraineMapper = migraineMapper;
	}

	@Transactional
	public Mono<PeriodDTO> createPeriod(final Period period, final Migraine migraine) {
		return periodPersistencePort.createPeriod(periodMapper.mapToPersistence(period))
				.flatMap(createdPeriod -> {
					if (migraine != null) {
						return migrainePersistencePort.createMigraine(migraineMapper.mapToPersistence(migraine, createdPeriod.getId()))
								.thenReturn(createdPeriod);
					}
					return Mono.just(createdPeriod);
				})
				.map(periodMapper::mapToDTO);
	}
}
