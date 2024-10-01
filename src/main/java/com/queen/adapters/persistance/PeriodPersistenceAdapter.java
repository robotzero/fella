package com.queen.adapters.persistance;

import com.queen.application.service.exception.ActivePeriodExistsException;
import com.queen.application.service.exception.PeriodUpdateException;
import com.queen.domain.PeriodPersistencePort;
import com.queen.infrastructure.persistence.Period;
import com.queen.infrastructure.persistence.PeriodRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public class PeriodPersistenceAdapter implements PeriodPersistencePort {
	final PeriodRepository periodRepository;

	public PeriodPersistenceAdapter(final PeriodRepository periodRepository) {
		this.periodRepository = periodRepository;
	}

	@Override
	@Transactional
	public Mono<Period> createPeriod(final Period period) {
		return periodRepository.save(period)
				.onErrorMap(e -> {
			return switch (e) {
				case DuplicateKeyException dke -> new ActivePeriodExistsException("An active period already exists", dke);
				default -> e;
			};
		});
	}

	@Override
	public Mono<Period> updatePeriod(Period period) {
		final var updated = this.periodRepository.endActivePeriod(period.getId(), period.getEndDate());
		return updated.flatMap(count -> {
			if (count == 0) {
				return Mono.error(new PeriodUpdateException("No active period found to update", null));
			}
			return Mono.just(period);
		});
	}

	@Override
	public Flux<Period> getPeriods(UUID userID) {
		return periodRepository.findAllByUserId(userID);
	}
}
