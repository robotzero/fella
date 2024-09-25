package com.queen.adapters.web.dto;

import java.time.LocalDate;
import java.util.Optional;

public record MigraineRequest(Optional<LocalDate> migraineDate, Optional<Integer> severityLevel, Optional<String> description) {
	public LocalDate migraineDateOrNow() {
		return migraineDate.orElse(LocalDate.now());
	}
	public Integer severityLevelOrDefault() {
		return severityLevel.orElse(0);
	}
}
