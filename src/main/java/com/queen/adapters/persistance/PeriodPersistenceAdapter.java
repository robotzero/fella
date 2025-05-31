package com.queen.adapters.persistance;

import com.queen.application.service.exception.ActivePeriodExistsException;
import com.queen.application.service.exception.PeriodUpdateException;
import com.queen.domain.PeriodPersistencePort;
import com.queen.infrastructure.persistence.Period;
import com.queen.infrastructure.persistence.PeriodRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public class PeriodPersistenceAdapter implements PeriodPersistencePort {
	final PeriodRepository periodRepository;

	public PeriodPersistenceAdapter(final PeriodRepository periodRepository) {
		this.periodRepository = periodRepository;
	}

	@Override
	@Transactional
	public Period createPeriod(final Period period) {
		return periodRepository.save(period);
	}

	@Override
	public Period updatePeriod(final Period period) {
		//@TODO throw proper exception here
		assert period.getId() != null;
		final var updated = this.periodRepository.endActivePeriod(period.getId(), period.getEndDate());
		return this.periodRepository.findByIdAndByUserId(period.getId());
	}

	@Override
	public List<Period> getPeriods(UUID userID) {
		return periodRepository.findAllByUserId(userID);
	}
}
