package com.queen.domain;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public record Migraine(Optional<UUID> migraineId, UUID userId, LocalDate migraineDate, Integer severityLevel, String description) {
}
