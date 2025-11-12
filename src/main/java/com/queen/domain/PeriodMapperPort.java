package com.queen.domain;

import com.queen.adapters.web.dto.PeriodDTO;
import com.queen.adapters.web.dto.PeriodRequest;
import com.queen.infrastructure.persistence.DailyTracking;
import com.queen.infrastructure.persistence.Migraine;
import com.queen.infrastructure.persistence.Period;

import java.util.UUID;

public interface PeriodMapperPort {
	com.queen.domain.Period mapToDomain(UUID userId, PeriodRequest periodRequest);

	com.queen.domain.Period mapToDomainUpdate(UUID periodID, UUID userId, PeriodRequest periodRequest);

	PeriodDTO mapToDTO(final com.queen.infrastructure.persistence.Period period, final Migraine migraine, final DailyTracking dailyTracking);
	Period mapToPersistence(final com.queen.domain.Period period, final boolean isNew);
}
