package com.queen.domain;

import com.queen.adapters.web.dto.PeriodDTO;
import com.queen.infrastructure.persistence.DailyTracking;
import com.queen.infrastructure.persistence.Migraine;
import com.queen.infrastructure.persistence.Period;

import java.util.List;

public interface PeriodMapperPort {
	PeriodDTO mapToDTO(final com.queen.infrastructure.persistence.Period period, final List<Migraine> migraine, final List<DailyTracking> dailyTracking);
	Period mapToPersistence(final com.queen.domain.Period period);
}
