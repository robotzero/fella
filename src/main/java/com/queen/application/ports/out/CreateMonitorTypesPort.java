package com.queen.application.ports.out;

import com.queen.infrastructure.persistence.MonitorType;
import reactor.core.publisher.Flux;

import java.util.List;

public interface CreateMonitorTypesPort {
	Flux<MonitorType> createMonitorTypes(final List<MonitorType> monitorType);
}
