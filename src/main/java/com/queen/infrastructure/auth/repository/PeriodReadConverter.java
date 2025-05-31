package com.queen.infrastructure.auth.repository;

import com.queen.infrastructure.persistence.DailyTracking;
import com.queen.infrastructure.persistence.Migraine;
import com.queen.infrastructure.persistence.Period;
import jakarta.persistence.AttributeConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.time.LocalDate;
import java.util.UUID;

@ReadingConverter
public class PeriodReadConverter implements AttributeConverter<Period, String> {
//	public Period convert(Row source) {
//		Period period = new Period(
//				source.get("user_id", UUID.class),
//				source.get("start_date", LocalDate.class)
//		);
//		period.setActive(source.get("active", Boolean.class));
//		period.setEndDate(source.get("end_date", LocalDate.class));
//		period.setCycleLength(source.get("cycle_length", Integer.class));
//		period.setId(source.get("period_id", UUID.class));
//
//		var migraine = new Migraine(
//				source.get("user_id", UUID.class),
//				source.get("migraine_date", LocalDate.class),
//				false
//		);
//		migraine.setId(source.get("migraine_id", UUID.class));
//		migraine.setSeverityLevel(source.get("severity_level", Integer.class));
//		migraine.setDescription(source.get("description", String.class));
//
//		period.setMigraine(migraine);
//
//		var dt = new DailyTracking(
//				source.get("user_id", UUID.class),
//				source.get("tracking_date", LocalDate.class),
//				false
//		);
//
//		dt.setPeriodId(source.get("period_id", UUID.class));
//		dt.setId(source.get("tracking_id", UUID.class));
//		dt.setMigraineId(source.get("migraine_id", UUID.class));
//		dt.setPainLevel(source.get("pain_level", Integer.class));
//		dt.setFlowLevel(source.get("flow_level", Integer.class));
//
//		period.setDailyTracking(dt);
//		return period;
//	}

	@Override
	public String convertToDatabaseColumn(Period period) {
		System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		return "";
	}

	@Override
	public Period convertToEntityAttribute(String s) {
		System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
		return null;
	}
}
