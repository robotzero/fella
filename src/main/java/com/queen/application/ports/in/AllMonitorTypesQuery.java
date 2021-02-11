package com.queen.application.ports.in;

import com.queen.domain.monitortype.MonitorType;
import reactor.core.publisher.Flux;

public interface AllMonitorTypesQuery {
	Flux<MonitorType> load();
}
