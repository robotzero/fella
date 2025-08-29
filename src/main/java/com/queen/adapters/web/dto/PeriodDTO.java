package com.queen.adapters.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record PeriodDTO(
		UUID periodId,
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
		@JsonDeserialize(using = LocalDateDeserializer.class)
		@JsonSerialize(using = LocalDateSerializer.class)
		LocalDate startDate,
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
		@JsonDeserialize(using = LocalDateDeserializer.class)
		@JsonSerialize(using = LocalDateSerializer.class)
		LocalDate endDate,
		List<MigraineDTO> migraine,
		List<DailyTrackingDTO> dailyTracking
) {
}
