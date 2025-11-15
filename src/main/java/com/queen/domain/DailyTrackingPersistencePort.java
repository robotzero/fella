package com.queen.domain;

import com.queen.infrastructure.persistence.DailyTracking;
import com.queen.infrastructure.persistence.Tracking;

import java.util.List;
import java.util.UUID;

public interface DailyTrackingPersistencePort {
	DailyTracking createDailyTracking(final DailyTracking dailyTracking);
	void updateDailyTracking(final DailyTracking dailyTracking);
	List<Tracking> getTracking(final UUID userId);
}
