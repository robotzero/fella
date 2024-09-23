package com.queen.domain;

import java.time.LocalDate;
import java.util.Optional;

public record Period(LocalDate startDate,
					 Optional<LocalDate> endDate) {
}
