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

	public static FullPeriodRequest fromUI(LocalDate trackingDate, Integer painLevel, Integer flowLevel, Integer migraineLevel) {
		var migraineRequest = Optional.of(migraineLevel).filter(m -> m > 0).map(m -> new MigraineRequest(
				Optional.of(trackingDate),
				Optional.of(migraineLevel),
				Optional.empty()
		));
		Optional<DailyTrackingRequest> dailyTrackingRequest = Optional.of(new DailyTrackingRequest(
				Optional.empty(),
				Optional.of(trackingDate),
				Optional.of(painLevel),
				Optional.of(flowLevel),
				Optional.empty(),
				Optional.empty()
		));
		return new FullPeriodRequest(
				Optional.of(trackingDate),
				migraineRequest,
				dailyTrackingRequest
		);
	}
}
