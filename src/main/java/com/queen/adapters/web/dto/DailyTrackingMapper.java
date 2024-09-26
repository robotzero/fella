package com.queen.adapters.web.dto;

import com.queen.domain.DailyTracking;
import com.queen.domain.DailyTrackingMapperPort;

public class DailyTrackingMapper implements DailyTrackingMapperPort {
	public DailyTracking mapToDomain(final DailyTrackingRequest dt) {
		return new DailyTracking(dt.trackingDateOrNow(), dt.painLevelOrDefault(), dt.flowLevelOrDefault());
	}

	@Override
	public DailyTrackingDTO mapToDTO(final com.queen.infrastructure.persistence.DailyTracking dailyTracking) {
		return new DailyTrackingDTO();
	}

	@Override
	public com.queen.infrastructure.persistence.DailyTracking mapToPersistence(final DailyTracking dailyTracking) {
		return new com.queen.infrastructure.persistence.DailyTracking(dailyTracking.trackingDate(), true);
	}
}
