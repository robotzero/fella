package com.queen.application.ports.in;

import java.time.LocalDate;
import java.util.Optional;

public record CreatePeriodCommand(String userId, LocalDate startDate, Optional<LocalDate> endDate) {
}
