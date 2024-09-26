package com.queen.adapters.persistance;

import com.queen.domain.DailyTrackingPersistencePort;
import com.queen.infrastructure.persistence.DailyTracking;
import com.queen.infrastructure.persistence.DailyTrackingRepository;
import reactor.core.publisher.Mono;

public class DailyTrackingPersistenceAdapter implements DailyTrackingPersistencePort {
	private final DailyTrackingRepository dailyTrackingRepository;

	public DailyTrackingPersistenceAdapter(final DailyTrackingRepository dailyTrackingRepository) {
		this.dailyTrackingRepository = dailyTrackingRepository;
	}

	@Override
	public Mono<DailyTracking> createDailyTracking(final DailyTracking dailyTracking) {
		return dailyTrackingRepository.save(dailyTracking);
	}
}
