package com.queen.adapters.persistance;

import com.queen.application.ports.out.CreatePeriodMonitorPort;
import com.queen.application.ports.out.LoadPeriodMonitorsPort;
import com.queen.infrastructure.persitence.monitor.Period;
import com.queen.infrastructure.persitence.monitor.PeriodMonitor;
import com.queen.infrastructure.persitence.monitor.PeriodMonitorRepository;
import com.queen.infrastructure.persitence.monitor.PeriodRepository;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PeriodMonitorPersistenceAdapter implements LoadPeriodMonitorsPort, CreatePeriodMonitorPort {
	private final PeriodMonitorRepository periodMonitorRepository;
	private final PeriodRepository periodRepository;

	public PeriodMonitorPersistenceAdapter(final PeriodMonitorRepository periodMonitorRepository, final PeriodRepository periodRepository) {
		this.periodMonitorRepository = periodMonitorRepository;
		this.periodRepository = periodRepository;
	}

	@Override
	public Flux<PeriodMonitor> loadPeriodMonitors(final String monitorTypeId, final String userId, Pageable pageable) {
		return periodMonitorRepository.findPeriodMonitorsByIdAndUserId(monitorTypeId, userId, pageable);
	}

	@Override
	public Flux<Period> loadPeriods(final String periodMoniorId) {
		return periodRepository.findPeriodsByPeriodMonitorId(periodMoniorId);
	}

	@Override
	public Mono<PeriodMonitor> createPeriodMonitor(final PeriodMonitor monitor) {
		return periodMonitorRepository.save(monitor);
	}

	@Override
	public Mono<Period> createPeriod(final Period period) {
		return periodRepository.save(period);
	}
}
