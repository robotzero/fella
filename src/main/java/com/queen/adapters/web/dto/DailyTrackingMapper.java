package com.queen.adapters.web.dto;

import com.queen.domain.DailyTracking;
import com.queen.domain.DailyTrackingMapperPort;

import java.util.UUID;

public class DailyTrackingMapper implements DailyTrackingMapperPort {
	public DailyTracking mapToDomain(final UUID userId, final DailyTrackingRequest dt) {
		return new DailyTracking(userId, dt.trackingDateOrNow(), dt.painLevelOrDefault(), dt.flowLevelOrDefault());
	}

	@Override
	public DailyTrackingDTO mapToDTO(final com.queen.infrastructure.persistence.DailyTracking dailyTracking) {
		if (dailyTracking == null) {
			return null;
		}
		return new DailyTrackingDTO(dailyTracking.getPainLevel(), dailyTracking.getFlowLevel(), dailyTracking.getTrackingDate());
	}

	@Override
	public com.queen.infrastructure.persistence.DailyTracking mapToPersistence(final DailyTracking dailyTracking) {
		return new com.queen.infrastructure.persistence.DailyTracking(dailyTracking.userId(), dailyTracking.trackingDate(), true);
	}
}
