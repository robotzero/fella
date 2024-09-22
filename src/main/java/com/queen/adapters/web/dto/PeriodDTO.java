package com.queen.adapters.web.dto;

import java.time.LocalDate;
import java.util.Optional;

public record PeriodDTO(
	LocalDate startDate,
	Optional<LocalDate> endDate
) {}
