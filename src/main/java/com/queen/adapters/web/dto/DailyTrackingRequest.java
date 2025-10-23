package com.queen.adapters.web.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public record DailyTrackingRequest(@JsonSetter(nulls = Nulls.AS_EMPTY) Optional<UUID> periodId,
								   @JsonSetter(nulls = Nulls.AS_EMPTY) Optional<LocalDate> trackingDate,
								   @JsonSetter(nulls = Nulls.AS_EMPTY) Optional<Integer> painLevel,
								   @JsonSetter(nulls = Nulls.AS_EMPTY) Optional<Integer> flowLevel,
								   @JsonSetter(nulls = Nulls.AS_EMPTY) Optional<UUID> moodId,
								   @JsonSetter(nulls = Nulls.AS_EMPTY) Optional<UUID> migraineId
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
