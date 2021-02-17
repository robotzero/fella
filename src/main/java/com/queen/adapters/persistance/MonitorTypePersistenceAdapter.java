package com.queen.adapters.persistance;

import com.queen.application.ports.out.CreateMonitorTypePort;
import com.queen.application.ports.out.LoadAllMonitorTypesPort;
import com.queen.infrastructure.persitence.MonitorType;
import com.queen.infrastructure.persitence.MonitorTypeRepository;
import reactor.core.publisher.Flux;

public class MonitorTypePersistenceAdapter implements LoadAllMonitorTypesPort, CreateMonitorTypePort {
	private final MonitorTypeRepository monitorTypeRepository;

	public MonitorTypePersistenceAdapter(final MonitorTypeRepository monitorTypeRepository) {
		this.monitorTypeRepository = monitorTypeRepository;
	}

	@Override
	public Flux<MonitorType> loadAllMonitorTypes() {
		return this.monitorTypeRepository.findAll();
	}

	@Override
	public void createMonitorType(MonitorType monitorType) {
		this.monitorTypeRepository.save(monitorType).subscribe();
	}
}
