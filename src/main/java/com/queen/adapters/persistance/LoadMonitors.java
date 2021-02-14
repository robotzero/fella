package com.queen.adapters.persistance;

import com.queen.application.ports.out.LoadAllMonitors;
import com.queen.infrastructure.persitence.Monitor;
import com.queen.infrastructure.persitence.MonitorRepository;
import reactor.core.publisher.Flux;

public class LoadMonitors implements LoadAllMonitors {
	private final MonitorRepository monitorRepository;

	public LoadMonitors(MonitorRepository monitorRepository) {
		this.monitorRepository = monitorRepository;
	}

	@Override
	public Flux<Monitor> loadAllMonitors() {
		return monitorRepository.findAll();
	}
}
