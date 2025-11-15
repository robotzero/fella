package com.queen.domain;

import com.queen.adapters.web.dto.DailyTrackingDTO;
import com.queen.adapters.web.dto.TrackingDTO;
import com.queen.infrastructure.persistence.Migraine;
import com.queen.infrastructure.persistence.Tracking;

public interface DailyTrackingMapperPort {
	DailyTrackingDTO mapToDTO(final com.queen.infrastructure.persistence.DailyTracking dailyTracking);
	com.queen.infrastructure.persistence.DailyTracking mapToPersistence(
			final com.queen.domain.DailyTracking dailyTracking
	);
	TrackingDTO mapToTrackingDTO(
			final Tracking tracking,
			final com.queen.infrastructure.persistence.Period period,
			final Migraine migraine
	);
}
