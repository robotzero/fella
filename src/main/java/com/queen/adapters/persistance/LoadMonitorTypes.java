package com.queen.adapters.persistance;

import com.queen.application.ports.out.LoadAllMonitorTypes;
import com.queen.infrastructure.persitence.MonitorType;
import com.queen.infrastructure.persitence.MonitorTypeRepository;
import reactor.core.publisher.Flux;

public class LoadMonitorTypes implements LoadAllMonitorTypes {
	private final MonitorTypeRepository monitorTypeRepository;

	public LoadMonitorTypes(MonitorTypeRepository monitorTypeRepository) {
		this.monitorTypeRepository = monitorTypeRepository;
	}

	@Override
	public Flux<MonitorType> loadMonitorTypes() {
		return this.monitorTypeRepository.findAll();
	}
}
