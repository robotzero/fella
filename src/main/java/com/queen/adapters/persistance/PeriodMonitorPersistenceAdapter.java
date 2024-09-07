package com.queen.adapters.persistance;

import com.queen.application.ports.out.CreatePeriodMonitorPort;
import com.queen.application.ports.out.LoadPeriodMonitorsPort;
import com.queen.infrastructure.persistence.monitor.PeriodMonitor;
import com.queen.infrastructure.persistence.monitor.PeriodMonitorRepository;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

public class PeriodMonitorPersistenceAdapter implements LoadPeriodMonitorsPort, CreatePeriodMonitorPort {
	private final PeriodMonitorRepository periodMonitorRepository;

	public PeriodMonitorPersistenceAdapter(final PeriodMonitorRepository periodMonitorRepository) {
		this.periodMonitorRepository = periodMonitorRepository;
	}

	@Override
	public Flux<PeriodMonitor> loadPeriodMonitors(final String monitorTypeId, final String userId, Pageable pageable) {
		return periodMonitorRepository.findPeriodMonitorsByIdAndUserId(monitorTypeId, userId, pageable);
	}

	@Override
	public Mono<PeriodMonitor> loadPeriodMonitorByDate(final String monitorTypeId, final String userId, final Date dateToSearchBy) {
		return periodMonitorRepository.findPeriodMonitorByDate(monitorTypeId, userId, dateToSearchBy);
	}

	@Override
	public Mono<PeriodMonitor> createPeriodMonitor(final PeriodMonitor periodMonitor) {
		return periodMonitorRepository.save(periodMonitor);
	}
}
