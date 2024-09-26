package com.queen.application.service;

import com.queen.adapters.web.dto.PeriodDTO;
import com.queen.domain.DailyTracking;
import com.queen.domain.DailyTrackingPersistencePort;
import com.queen.domain.Migraine;
import com.queen.domain.MigraineMapperPort;
import com.queen.domain.DailyTrackingMapperPort;
import com.queen.domain.MigrainePersistencePort;
import com.queen.domain.Period;
import com.queen.domain.PeriodMapperPort;
import com.queen.domain.PeriodPersistencePort;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.Optional;

public class PeriodService {
	private final PeriodPersistencePort periodPersistencePort;
	private final MigrainePersistencePort migrainePersistencePort;
	private final PeriodMapperPort periodMapper;
	private final MigraineMapperPort migraineMapper;
	private final DailyTrackingMapperPort dailyTrackingMapper;
	private final DailyTrackingPersistencePort dailyTrackingPersistencePort;

	public PeriodService(
			final PeriodPersistencePort periodPersistencePort,
			final MigrainePersistencePort migrainePersistencePort,
			final PeriodMapperPort periodMapper,
			final MigraineMapperPort migraineMapper,
			final DailyTrackingMapperPort dailyTrackingMapper,
			final DailyTrackingPersistencePort dailyTrackingPersistencePort
	) {
		this.periodPersistencePort = periodPersistencePort;
		this.migrainePersistencePort = migrainePersistencePort;
		this.periodMapper = periodMapper;
		this.migraineMapper = migraineMapper;
		this.dailyTrackingMapper = dailyTrackingMapper;
		this.dailyTrackingPersistencePort = dailyTrackingPersistencePort;
	}

	@Transactional
	public Mono<PeriodDTO> createPeriod(final Period period, final Migraine migraine, final DailyTracking dailyTracking) {
		return periodPersistencePort.createPeriod(periodMapper.mapToPersistence(period))
				.zipWith(migrainePersistencePort.createMigraine(migraineMapper.mapToPersistence(migraine)))
						.flatMap(tuple -> {
							if (dailyTracking != null) {
								return dailyTrackingPersistencePort.createDailyTracking(dailyTrackingMapper.mapToPersistence(dailyTracking)).map(dailyTracking1 -> {
									return periodMapper.mapToDTO(tuple.getT1(), Optional.of(tuple.getT2()));
								});
							}
							return Mono.just(periodMapper.mapToDTO(tuple.getT1(), Optional.of(tuple.getT2())));
						}).onErrorResume(e -> {
							return Mono.error(e);
						});
	}
}
