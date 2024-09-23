package com.queen.domain;

import com.queen.infrastructure.persistence.Period;
import reactor.core.publisher.Mono;

public interface PeriodPersistencePort {
	Mono<Period> createPeriod(Period period);
}
