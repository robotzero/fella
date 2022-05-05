package com.queen.adapters.persistance;

import com.queen.application.ports.out.LoadMonitorsPort;
import com.queen.infrastructure.persitence.Monitor;
import com.queen.infrastructure.persitence.MonitorRepository;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;

public class MonitorPersistenceAdapter implements LoadMonitorsPort {
	private final MonitorRepository monitorRepository;

	public MonitorPersistenceAdapter(final MonitorRepository monitorRepository) {
		this.monitorRepository = monitorRepository;
	}

	@Override
	public Flux<Monitor> loadMonitors(final String monitorTypeId, final String userId, final Pageable pageable) {
		return monitorRepository.findMonitorsByIdAndUserId(monitorTypeId, userId, pageable);
	}
}
