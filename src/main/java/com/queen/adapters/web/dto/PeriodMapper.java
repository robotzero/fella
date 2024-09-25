package com.queen.adapters.web.dto;

import com.queen.domain.Period;
import com.queen.domain.PeriodMapperPort;

import java.util.Optional;
import java.util.UUID;

public class PeriodMapper implements PeriodMapperPort {
	public Period mapToDomain(final UUID userId, final PeriodRequest periodRequest) {
		return new Period(
				userId,
				periodRequest.startDateOrNow(),
				periodRequest.endDate()
		);
	}

	@Override
	public PeriodDTO mapToDTO(final com.queen.infrastructure.persistence.Period period) {
		return new PeriodDTO(
				period.getStartDate(),
				Optional.ofNullable(period.getEndDate())
		);
	}

	@Override
	public com.queen.infrastructure.persistence.Period mapToPersistence(final Period period) {
		return new com.queen.infrastructure.persistence.Period(period.userId(), period.startDate());
	}
}
