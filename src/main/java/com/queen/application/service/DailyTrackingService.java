package com.queen.application.service;

import com.queen.adapters.web.dto.DailyTrackingMapper;
import com.queen.adapters.web.dto.TrackingDTO;
import com.queen.domain.DailyTrackingPersistencePort;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class DailyTrackingService {
	final DailyTrackingPersistencePort dailyTrackingPersistencePort;
	final DailyTrackingMapper dailyTrackingMapper;

	public DailyTrackingService(
			final DailyTrackingPersistencePort dailyTrackingPersistencePort,
			final DailyTrackingMapper dailyTrackingMapper
	) {
		this.dailyTrackingPersistencePort = dailyTrackingPersistencePort;
		this.dailyTrackingMapper = dailyTrackingMapper;
	}
	public List<TrackingDTO> getDailyTracking(final UUID userId) {
		return dailyTrackingPersistencePort.getTracking(userId).stream().map(t -> {
			return dailyTrackingMapper.mapToTrackingDTO(t, t.getPeriod(), t.getMigraine());
		}).collect(Collectors.toList());
	}
}
