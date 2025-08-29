package com.queen.application.service;

import com.queen.adapters.web.dto.PeriodDTO;
import com.queen.application.service.exception.ActivePeriodExistsException;
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
		var activePeriod = periodPersistencePort.getActivePeriod(period.userId());
		com.queen.infrastructure.persistence.Period p;
		if (activePeriod != null) {
			if (dailyTracking.flowLevel() == 0 && dailyTracking.painLevel() == 0) {
				throw new ActivePeriodExistsException("Active period is currently running.", null);
			}
			dt.setPeriodId(activePeriod.getId());
			p = activePeriod;
		} else {
			p = periodPersistencePort.createPeriod(periodMapper.mapToPersistence(period));
			dt.setPeriodId(p.getId());
		}
		var m = migraineMapper.mapToPersistence(migraine).orElse(com.queen.infrastructure.persistence.Migraine.empty());
		migrainePersistencePort.createMigraine(m);
		dt.setFlowLevel(dailyTracking.flowLevel());
		dt.setPainLevel(dailyTracking.painLevel());
		dt.setMigraineId(m.getId());
		var dt1 = dailyTrackingPersistencePort.createDailyTracking(dt);

		return periodMapper.mapToDTO(p, List.of(m), List.of(dt1));
	}

	@Transactional
	public PeriodDTO endPeriod(final Period period) {
		var p =  periodPersistencePort.updatePeriod(periodMapper.mapToPersistence(period));
		List<com.queen.infrastructure.persistence.Migraine> migraine = p.getMigraine() != null ? p.getMigraine() : List.of();
		List<com.queen.infrastructure.persistence.DailyTracking> dts = p.getDailyTracking() != null ? p.getDailyTracking() : List.of();

		return periodMapper.mapToDTO(p, migraine, dts);
	}

	public List<PeriodDTO> getPeriods(final UUID userId) {
		return periodPersistencePort.getPeriods(userId).stream().map(p -> {
			return periodMapper.mapToDTO(p, p.getMigraine(), p.getDailyTracking());
		}).collect(Collectors.toList());
	}
}
