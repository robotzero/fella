package com.queen.application.ports.in;

import com.queen.domain.monitortype.MonitorType;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MonitorTypesQuery {
	Flux<MonitorType> load(final String userId, final Pageable pageable);
	Mono<MonitorType> load(final String monitorTypeId, final String userId);
}
