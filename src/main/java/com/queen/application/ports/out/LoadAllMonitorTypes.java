package com.queen.application.ports.out;

import com.queen.infrastructure.persitence.MonitorType;
import reactor.core.publisher.Flux;

public interface LoadAllMonitorTypes {
	Flux<MonitorType> loadAllMonitorTypes();
}
