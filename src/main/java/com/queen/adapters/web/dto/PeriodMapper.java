package com.queen.adapters.web.dto;

import com.queen.domain.Period;
import com.queen.domain.PeriodMapperPort;

import java.util.Optional;

public class PeriodMapper implements PeriodMapperPort {
	public Period mapToDomain(final String userId, final PeriodRequest periodRequest) {
		return new Period(
				periodRequest.startDateOrNow(),
				periodRequest.endDate()
		);
	}

	@Override
	public PeriodDTO mapToDTO(com.queen.infrastructure.persistence.Period period) {
		return new PeriodDTO(
				period.getStartDate(),
				Optional.ofNullable(period.getEndDate())
		);
	}

	@Override
	public com.queen.infrastructure.persistence.Period mapToPersistence(Period period) {
		return new com.queen.infrastructure.persistence.Period("1212", period.startDate());
	}
}
