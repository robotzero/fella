package com.queen.adapters.persistance;

import com.queen.application.ports.out.LoadAllMonitorsPort;
import com.queen.infrastructure.persitence.Monitor;
import com.queen.infrastructure.persitence.MonitorRepository;
import reactor.core.publisher.Flux;

public class MonitorPersistenceAdapter implements LoadAllMonitorsPort {
	private final MonitorRepository monitorRepository;

	public MonitorPersistenceAdapter(MonitorRepository monitorRepository) {
		this.monitorRepository = monitorRepository;
	}

	@Override
	public Flux<Monitor> loadAllMonitors() {
		return monitorRepository.findAll();
	}
}
