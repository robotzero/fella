package com.queen.domain;

import com.queen.adapters.web.dto.PeriodDTO;
import com.queen.infrastructure.persistence.Migraine;
import com.queen.infrastructure.persistence.Period;

import java.util.Optional;

public interface PeriodMapperPort {
	PeriodDTO mapToDTO(final com.queen.infrastructure.persistence.Period period, final Optional<Migraine> migraine);
	Period mapToPersistence(final com.queen.domain.Period period);
}
