package com.queen.adapters.persistance;

import org.springframework.transaction.annotation.Transactional;

public class PeriodPersistenceAdapter {

	@Transactional
	public void createPeriodMonitor() {
		System.out.println("PeriodPersistenceAdapter.createPeriodMonitor");
	}
}
