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
import java.util.List;

import java.util.UUID;
import java.util.stream.Collectors;

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
	public PeriodDTO createPeriod(final Period period, final Migraine migraine, final DailyTracking dailyTracking) {
		var dt = dailyTrackingMapper.mapToPersistence(dailyTracking);
		var p = periodPersistencePort.createPeriod(periodMapper.mapToPersistence(period, true));
		dt.setPeriodId(p.getId());
		var m = migraineMapper.mapToPersistence(migraine);
		m.ifPresent(mm -> {
			migrainePersistencePort.createMigraine(mm);
			dt.setMigraineId(mm.getId());
		});
		dt.setFlowLevel(dailyTracking.flowLevel());
		dt.setPainLevel(dailyTracking.painLevel());
		var dt1 = dailyTrackingPersistencePort.createDailyTracking(dt);

		return periodMapper.mapToDTO(p, List.of(m.orElse(com.queen.infrastructure.persistence.Migraine.empty())), List.of(dt1));
	}

	public List<PeriodDTO> getPeriods(final UUID userId) {
		return periodPersistencePort.getPeriods(userId).stream().map(p -> {
			return periodMapper.mapToDTO(p, p.getMigraine(), p.getDailyTracking());
		}).collect(Collectors.toList());
	}
}
