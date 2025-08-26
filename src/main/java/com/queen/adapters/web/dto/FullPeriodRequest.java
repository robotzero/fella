package com.queen.adapters.web.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Optional;

public record FullPeriodRequest(Optional<LocalDate> startDate,
								Optional<LocalDate> endDate,
								Optional<MigraineRequest> migraine,
								Optional<DailyTrackingRequest> dailyTracking
) implements PeriodRequest {
	public LocalDate startDateOrNow() {
		return startDate.orElse(LocalDate.now());
	}
}
