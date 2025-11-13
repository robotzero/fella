package com.queen.adapters.web.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public record MigraineRequest(
		@JsonSetter(nulls = Nulls.AS_EMPTY) Optional<UUID> migraineId,
		@JsonSetter(nulls = Nulls.AS_EMPTY) Optional<LocalDate> migraineDate,
		@JsonSetter(nulls = Nulls.AS_EMPTY) Optional<Integer> severityLevel,
		@JsonSetter(nulls = Nulls.AS_EMPTY) Optional<String> description) {
	public LocalDate migraineDateOrNow() {
		return migraineDate.orElse(LocalDate.now());
	}
	public Integer severityLevelOrDefault() {
		return severityLevel.orElse(0);
	}
}
