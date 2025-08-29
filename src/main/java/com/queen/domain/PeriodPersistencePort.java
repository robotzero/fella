package com.queen.domain;

import com.queen.infrastructure.persistence.Period;

import java.util.List;
import java.util.UUID;

public interface PeriodPersistencePort {
	Period createPeriod(Period period);
	Period updatePeriod(Period period);
	List<Period> getPeriods(UUID userID);
	Period getActivePeriod(UUID userId);
}
