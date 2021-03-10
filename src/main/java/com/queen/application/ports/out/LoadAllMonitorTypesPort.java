package com.queen.application.ports.out;

import com.queen.infrastructure.persitence.MonitorType;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;

public interface LoadAllMonitorTypesPort {
	Flux<MonitorType> loadAllMonitorTypes(final String userId, Pageable pageable);
}
