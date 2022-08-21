package com.queen.adapters.persistance;

import com.queen.application.ports.out.CreateMonitorPort;
import com.queen.application.ports.out.LoadMonitorsPort;
import com.queen.infrastructure.persitence.Monitor;
import com.queen.infrastructure.persitence.MonitorRepository;
import com.queen.infrastructure.persitence.monitor.PeriodMonitor;
import com.queen.infrastructure.persitence.monitor.PeriodRepository;
import com.queen.infrastructure.persitence.monitor.StomachMonitor;
import com.queen.infrastructure.persitence.monitor.TabletsTakenMonitor;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MonitorPersistenceAdapter implements LoadMonitorsPort, CreateMonitorPort {
	private final MonitorRepository monitorRepository;
	private final PeriodRepository periodRepository;

	public MonitorPersistenceAdapter(final MonitorRepository monitorRepository, final PeriodRepository periodRepository) {
		this.monitorRepository = monitorRepository;
		this.periodRepository = periodRepository;
	}

	@Override
	public Flux<Monitor> loadMonitors(final String monitorTypeId, final String userId, final Pageable pageable) {
		return monitorRepository.findMonitorsByIdAndUserId(monitorTypeId, userId, pageable);
	}

	@Override
	public Flux<PeriodMonitor> loadPeriodMonitors(String monitorTypeId, String userId, Pageable pageable) {
		return periodRepository.findPeriodMonitorsByIdAndUserId(monitorTypeId, userId, pageable);
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
	public Mono<Monitor> createMonitor(final Monitor monitor) {
		return monitorRepository.save(monitor);
	}
}
