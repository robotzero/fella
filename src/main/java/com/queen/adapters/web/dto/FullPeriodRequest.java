package com.queen.adapters.web.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;

import java.time.LocalDate;
import java.util.Optional;

public record FullPeriodRequest(
		@JsonSetter(nulls = Nulls.AS_EMPTY) Optional<LocalDate> date,
		@JsonSetter(nulls = Nulls.AS_EMPTY) Optional<MigraineRequest> migraine,
		@JsonSetter(nulls = Nulls.AS_EMPTY) Optional<DailyTrackingRequest> dailyTracking
) implements PeriodRequest {
	public LocalDate startDateOrNow() {
		return date.orElse(LocalDate.now());
	}
}
