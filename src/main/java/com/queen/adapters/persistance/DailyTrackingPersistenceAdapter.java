package com.queen.adapters.persistance;

import com.queen.domain.DailyTrackingPersistencePort;
import com.queen.infrastructure.persistence.DailyTracking;
import com.queen.infrastructure.persistence.DailyTrackingRepository;
import com.queen.infrastructure.persistence.Tracking;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class DailyTrackingPersistenceAdapter implements DailyTrackingPersistencePort {
	private final DailyTrackingRepository dailyTrackingRepository;

	public DailyTrackingPersistenceAdapter(final DailyTrackingRepository dailyTrackingRepository) {
		this.dailyTrackingRepository = dailyTrackingRepository;
	}

	@Override
	public DailyTracking createDailyTracking(final DailyTracking dailyTracking) {
		return dailyTrackingRepository.save(dailyTracking);
	}

	@Override
	public void updateDailyTracking(DailyTracking dailyTracking) {
		dailyTrackingRepository.updateDailyTracking(dailyTracking);
	}

	@Override
	public List<Tracking> getTracking(UUID userId) {
		return dailyTrackingRepository.findAllByUserId(userId);
	}

	@Override
	public void deleteTracking(Set<UUID> trackingIds, UUID userId) {
		dailyTrackingRepository.deleteTracking(trackingIds, userId);
	}
}
