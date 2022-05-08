package com.queen.application.ports.out;

import com.queen.infrastructure.persitence.MonitorType;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface LoadMonitorTypesPort {
	Flux<MonitorType> loadAllMonitorTypes(final String userId, Pageable pageable);
	Flux<MonitorType> findByUserIdAndNames(final List<String> name, final String userId);
	Mono<MonitorType> loadSingleMonitorType(final String monitorTypeId, final String userId);
}
