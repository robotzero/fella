package com.queen.application.ports.out;

import com.queen.infrastructure.persitence.MonitorType;
import reactor.core.publisher.Flux;

import javax.validation.constraints.NotNull;

public interface LoadAllMonitorTypesPort {
	Flux<MonitorType> loadAllMonitorTypes(final @NotNull String userId);
}
