package com.queen.adapters.persistance;

import com.queen.application.ports.out.CreateManyMonitorTypesPort;
import com.queen.application.ports.out.LoadMonitorTypesPort;
import com.queen.infrastructure.persitence.MonitorType;
import com.queen.infrastructure.persitence.MonitorTypeRepository;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;

import java.util.List;

public class MonitorTypePersistenceAdapter implements LoadMonitorTypesPort, CreateManyMonitorTypesPort {
	private final MonitorTypeRepository monitorTypeRepository;

	public MonitorTypePersistenceAdapter(final MonitorTypeRepository monitorTypeRepository) {
		this.monitorTypeRepository = monitorTypeRepository;
	}

	@Override
	public Flux<MonitorType> loadAllMonitorTypes(final String userId, final Pageable pageable) {
		return this.monitorTypeRepository.findByUserId(userId, pageable);
	}

	@Override
	public Flux<MonitorType> findByUserIdAndNames(List<String> names, String userId) {
		return this.monitorTypeRepository.findByUserIdAndNames(names, userId);
	}

	@Override
	public Flux<MonitorType> createMonitorTypes(final List<MonitorType> monitorTypes) {
		return this.monitorTypeRepository.saveAll(monitorTypes);
	}
}
