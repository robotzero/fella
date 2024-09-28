package com.queen.domain;

import java.time.LocalDate;
import java.util.UUID;

public record DailyTracking(UUID userId, LocalDate trackingDate, Integer painLevel, Integer flowLevel) {
}
