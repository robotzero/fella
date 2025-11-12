package com.queen.domain;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public record DailyTracking(Optional<UUID> trackingId, UUID userId, LocalDate trackingDate, Integer painLevel, Integer flowLevel) {
}
