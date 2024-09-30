package com.queen.adapters.web.dto;

import com.queen.application.service.exception.InvalidPeriodIdException;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public record EndPeriodRequest(
		@NotNull(message = "Missing data in the request") String periodId,
		Optional<LocalDate> endDate
) implements PeriodRequest {
	public LocalDate endDateOrNow() {
		return endDate.orElse(LocalDate.now());
	}

	public UUID periodIdToUUID() {
		try {
			return UUID.fromString(periodId);
		} catch (IllegalArgumentException e) {
			throw new InvalidPeriodIdException("Invalid period id");
		}
	}
}
