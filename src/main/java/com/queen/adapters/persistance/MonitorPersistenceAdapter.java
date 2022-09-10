package com.queen.adapters.persistance;

import com.queen.application.ports.out.CreateMonitorPort;
import com.queen.application.ports.out.LoadMonitorsPort;
import com.queen.infrastructure.persitence.Monitor;
import com.queen.infrastructure.persitence.monitor.Period;
import com.queen.infrastructure.persitence.monitor.PeriodMonitor;
import com.queen.infrastructure.persitence.monitor.PeriodMonitorRepository;
import com.queen.infrastructure.persitence.monitor.PeriodRepository;
import com.queen.infrastructure.persitence.monitor.StomachMonitor;
import com.queen.infrastructure.persitence.monitor.TabletsTakenMonitor;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MonitorPersistenceAdapter implements LoadMonitorsPort, CreateMonitorPort {
	private final PeriodMonitorRepository periodMonitorRepository;
	private final PeriodRepository periodRepository;

	public MonitorPersistenceAdapter(final PeriodMonitorRepository periodMonitorRepository, final PeriodRepository periodRepository) {
		this.periodMonitorRepository = periodMonitorRepository;
		this.periodRepository = periodRepository;
	}

	@Override
	public Flux<Monitor> loadMonitors(final String monitorTypeId, final String userId, final Pageable pageable) {
		return Flux.never();
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
	public Flux<StomachMonitor> loadStomachMonitor(String monitorTypeId, String userId, Pageable pageable) {
		return null;
	}

	@Override
	public Flux<TabletsTakenMonitor> loadTabletsTakenMonitor(String monitorTypeId, String userId, Pageable pageable) {
		return null;
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
