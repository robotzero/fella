package com.queen.adapters.web.dto;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public record DailyTrackingRequest(Optional<UUID> periodId,
								   Optional<LocalDate> trackingDate,
								   Optional<Integer> painLevel,
								   Optional<Integer> flowLevel,
								   Optional<UUID> moodId,
								   Optional<UUID> migraineId
) {
	public LocalDate trackingDateOrNow() {
		return trackingDate.orElse(LocalDate.now());
	}
	public Integer painLevelOrDefault() {
		return painLevel.orElse(0);
	}
	public Integer flowLevelOrDefault() {
		return flowLevel.orElse(0);
	}
}
