package com.queen.application.ports.in;

import com.queen.domain.monitortype.MonitorType;
import reactor.core.publisher.Flux;

import javax.validation.constraints.NotNull;

public interface AllMonitorTypesQuery {
	Flux<MonitorType> load(final @NotNull String userId);
}
