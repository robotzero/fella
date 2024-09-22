package com.queen.application.service;

import com.queen.adapters.persistance.PeriodPersistenceAdapter;
import com.queen.adapters.web.dto.PeriodRequest;
import org.springframework.transaction.annotation.Transactional;

public class PeriodService {
	private final PeriodPersistenceAdapter periodPersistenceAdapter;

	public PeriodService(final PeriodPersistenceAdapter periodPersistenceAdapter) {
		this.periodPersistenceAdapter = periodPersistenceAdapter;
	}

	@Transactional
	public void createPeriodMonitor(final PeriodRequest periodRequest) {
		periodPersistenceAdapter.createPeriodMonitor();
	}
}
