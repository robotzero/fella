package com.queen.adapters.persistance;

import com.queen.application.ports.out.CreateMonitorPort;
import com.queen.application.ports.out.LoadMonitorsPort;
import com.queen.infrastructure.persitence.Monitor;
import com.queen.infrastructure.persitence.MonitorRepository;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MonitorPersistenceAdapter implements LoadMonitorsPort, CreateMonitorPort {
	private final MonitorRepository monitorRepository;

	public MonitorPersistenceAdapter(final MonitorRepository monitorRepository) {
		this.monitorRepository = monitorRepository;
	}

	@Override
	public Flux<Monitor> loadMonitors(final String monitorTypeId, final String userId, final Pageable pageable) {
		return monitorRepository.findMonitorsByIdAndUserId(monitorTypeId, userId, pageable);
	}

	@Override
	public Mono<Monitor> createMonitor(final Monitor monitor) {
		return monitorRepository.save(monitor);
	}
}
