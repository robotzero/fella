package com.queen.adapters.persistance;

import com.queen.domain.PeriodPersistencePort;
import com.queen.infrastructure.persistence.Period;
import com.queen.infrastructure.persistence.PeriodRepository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

public class PeriodPersistenceAdapter implements PeriodPersistencePort {
	final PeriodRepository periodRepository;

	public PeriodPersistenceAdapter(final PeriodRepository periodRepository) {
		this.periodRepository = periodRepository;
	}

	@Override
	@Transactional
	public Mono<Period> createPeriod(Period period) {
		return periodRepository.save(period);
	}
}
