package com.queen.domain;

import com.queen.infrastructure.persistence.DailyTracking;
import reactor.core.publisher.Mono;

public interface DailyTrackingPersistencePort {
	Mono<DailyTracking> createDailyTracking(final DailyTracking dailyTracking);
}
