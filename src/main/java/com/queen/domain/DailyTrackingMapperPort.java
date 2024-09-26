package com.queen.domain;

import com.queen.adapters.web.dto.DailyTrackingDTO;

public interface DailyTrackingMapperPort {
	DailyTrackingDTO mapToDTO(final com.queen.infrastructure.persistence.DailyTracking dailyTracking);
	com.queen.infrastructure.persistence.DailyTracking mapToPersistence(
			final com.queen.domain.DailyTracking dailyTracking
	);
}
