package com.queen.adapters.web.dto;

import com.queen.domain.DailyTracking;
import com.queen.domain.DailyTrackingMapperPort;

import java.util.List;
import java.util.UUID;

public class DailyTrackingMapper implements DailyTrackingMapperPort {
	public DailyTracking mapToDomain(final UUID userId, final DailyTrackingRequest dt) {
		return new DailyTracking(userId, dt.trackingDateOrNow(), dt.painLevelOrDefault(), dt.flowLevelOrDefault());
	}

	@Override
	public List<DailyTrackingDTO> mapToDTO(final List<com.queen.infrastructure.persistence.DailyTracking> dailyTracking) {
		if (dailyTracking == null) {
			return null;
		}
		return dailyTracking.stream().map(d -> {
			return new DailyTrackingDTO(d.getPainLevel(), d.getFlowLevel(), d.getTrackingDate());
		}).toList();
	}

	@Override
	public com.queen.infrastructure.persistence.DailyTracking mapToPersistence(final DailyTracking dailyTracking) {
		return new com.queen.infrastructure.persistence.DailyTracking(dailyTracking.userId(), dailyTracking.trackingDate(), true);
	}
}
