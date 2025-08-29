package com.queen.domain;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public record Period(UUID periodId, UUID userId, LocalDate startDate, Optional<LocalDate> endDate, Boolean active) {
}
