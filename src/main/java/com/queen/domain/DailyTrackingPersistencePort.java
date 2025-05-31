package com.queen.domain;

import com.queen.infrastructure.persistence.DailyTracking;

public interface DailyTrackingPersistencePort {
	DailyTracking createDailyTracking(final DailyTracking dailyTracking);
}
