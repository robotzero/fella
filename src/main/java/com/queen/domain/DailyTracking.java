package com.queen.domain;

import java.time.LocalDate;

public record DailyTracking(LocalDate trackingDate, Integer painLevel, Integer flowLevel) {
}
