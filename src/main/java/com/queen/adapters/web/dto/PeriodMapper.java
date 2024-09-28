package com.queen.adapters.web.dto;

import com.queen.domain.Period;
import com.queen.domain.PeriodMapperPort;
import com.queen.infrastructure.persistence.DailyTracking;
import com.queen.infrastructure.persistence.Migraine;

import java.util.UUID;

public class PeriodMapper implements PeriodMapperPort {
	public final MigraineMapper migraineMapper;
	public final DailyTrackingMapper dailyTrackingMapper;

	public PeriodMapper(final MigraineMapper migraineMapper, final DailyTrackingMapper dailyTrackingMapper) {
		this.migraineMapper = migraineMapper;
		this.dailyTrackingMapper = dailyTrackingMapper;
	}

	public Period mapToDomain(final UUID userId, final PeriodRequest periodRequest) {
		return new Period(
				userId,
				periodRequest.startDateOrNow(),
				periodRequest.endDate()
		);
	}

	@Override
	public PeriodDTO mapToDTO(final com.queen.infrastructure.persistence.Period period, final Migraine migraine, final DailyTracking dailyTracking) {
		return new PeriodDTO(
				period.getStartDate(),
				period.getEndDate(),
				migraineMapper.mapToDTO(migraine),
				dailyTrackingMapper.mapToDTO(dailyTracking)
		);
	}

	@Override
	public com.queen.infrastructure.persistence.Period mapToPersistence(final Period period) {
		return new com.queen.infrastructure.persistence.Period(period.userId(), period.startDate(), true);
	}
}
