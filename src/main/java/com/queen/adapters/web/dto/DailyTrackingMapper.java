package com.queen.adapters.web.dto;

import com.queen.domain.DailyTracking;
import com.queen.domain.DailyTrackingMapperPort;
import com.queen.infrastructure.persistence.Migraine;
import com.queen.infrastructure.persistence.Tracking;

import java.util.UUID;

public class DailyTrackingMapper implements DailyTrackingMapperPort {
	public DailyTracking mapToDomain(final UUID userId, final DailyTrackingRequest dt) {
		return new DailyTracking(dt.trackingId(), userId, dt.trackingDateOrNow(), dt.painLevelOrDefault(), dt.flowLevelOrDefault());
	}

	@Override
	public DailyTrackingDTO mapToDTO(final com.queen.infrastructure.persistence.DailyTracking dailyTracking) {
		if (dailyTracking == null) {
			return null;
		}
		return new DailyTrackingDTO(dailyTracking.getId(), dailyTracking.getPainLevel(), dailyTracking.getFlowLevel(), dailyTracking.getTrackingDate());
	}

	@Override
	public com.queen.infrastructure.persistence.DailyTracking mapToPersistence(final DailyTracking dailyTracking) {
		return new com.queen.infrastructure.persistence.DailyTracking(dailyTracking.userId(), dailyTracking.trackingDate(), true);
	}

	@Override
	public TrackingDTO mapToTrackingDTO(
			final Tracking tracking,
			final com.queen.infrastructure.persistence.Period period,
			final Migraine migraine) {
		var periodDTO = period != null && period.getId() != null ? new PeriodDTO(
				period.getId(),
				period.getDate(),
				null,
				null
		) : null;
		var migraineDTO = migraine != null && migraine.getId() != null ? new MigraineDTO(
				migraine.getMigraineDate(),
				migraine.getId(),
				migraine.getSeverityLevel(),
				migraine.getDescription()
		) : null;
		return new TrackingDTO(
			tracking.getId(),
			periodDTO,
			migraineDTO,
			tracking.getPainLevel(),
			tracking.getFlowLevel(),
			tracking.getTrackingDate()
		);
	}
}
