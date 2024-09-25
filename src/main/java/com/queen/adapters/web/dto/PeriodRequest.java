package com.queen.adapters.web.dto;

import java.time.LocalDate;
import java.util.Optional;

public record PeriodRequest(Optional<LocalDate> startDate, Optional<LocalDate> endDate,
							Optional<MigraineRequest> migraine,
							Optional<DailyTrackingRequest> dailyTracking) {
	public LocalDate startDateOrNow() {
		return startDate.orElse(LocalDate.now());
	}
}
