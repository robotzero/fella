package com.queen.domain;

import com.queen.infrastructure.persistence.Period;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface PeriodPersistencePort {
	Mono<Period> createPeriod(Period period);
	Mono<Period> updatePeriod(Period period);
	Flux<Period> getPeriods(UUID userID);
}
