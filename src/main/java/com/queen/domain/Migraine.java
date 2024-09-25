package com.queen.domain;

import java.time.LocalDate;
import java.util.UUID;

public record Migraine(UUID userId, LocalDate migraineDate, Integer severityLevel, String description) {
}
