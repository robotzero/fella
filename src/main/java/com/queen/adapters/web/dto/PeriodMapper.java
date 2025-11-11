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
		switch (periodRequest) {
			case FullPeriodRequest fullPeriodRequest -> {
				return new Period(
						UUID.randomUUID(),
						userId,
						fullPeriodRequest.startDateOrNow()
				);
			} case EndPeriodRequest endPeriodRequest -> {
				return new Period(
						endPeriodRequest.periodIdToUUID(),
						userId,
						endPeriodRequest.endDateOrNow()
				);
			}
		}
	}

	@Override
	public PeriodDTO mapToDTO(final com.queen.infrastructure.persistence.Period period, final Migraine migraine, final DailyTracking dailyTracking) {
		if (period != null) {
			return new PeriodDTO(
					period.getId(),
					period.getDate(),
					migraineMapper.mapToDTO(migraine),
					dailyTrackingMapper.mapToDTO(dailyTracking)
			);
		}
		return null;
	}

	@Override
	public com.queen.infrastructure.persistence.Period mapToPersistence(final Period period, final boolean isNew) {
		return new com.queen.infrastructure.persistence.Period(
				period.periodId(),
				period.userId(),
				period.date()).setId(period.periodId()).setNew(isNew);
	}
}
