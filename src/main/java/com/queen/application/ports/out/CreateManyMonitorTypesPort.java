package com.queen.application.ports.out;

import com.queen.infrastructure.persitence.MonitorType;
import reactor.core.publisher.Flux;

import java.util.List;

public interface CreateManyMonitorTypesPort {
	Flux<MonitorType> createMonitorTypes(final List<MonitorType> monitorType);
}
