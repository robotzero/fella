package com.queen.domain;

import com.queen.adapters.web.dto.DailyTrackingDTO;

import java.util.List;

public interface DailyTrackingMapperPort {
	List<DailyTrackingDTO> mapToDTO(final List<com.queen.infrastructure.persistence.DailyTracking> dailyTracking);
	com.queen.infrastructure.persistence.DailyTracking mapToPersistence(
			final com.queen.domain.DailyTracking dailyTracking
	);
}
