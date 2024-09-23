package com.queen.domain;

import com.queen.adapters.web.dto.PeriodDTO;
import com.queen.infrastructure.persistence.Period;

public interface PeriodMapperPort {
	PeriodDTO mapToDTO(com.queen.infrastructure.persistence.Period period);
	Period mapToPersistence(final com.queen.domain.Period period);
}
