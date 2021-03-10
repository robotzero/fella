package com.queen.application.ports.in;

import com.queen.domain.monitortype.MonitorType;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;

public interface AllMonitorTypesQuery {
	Flux<MonitorType> load(final String userId, final Pageable pageable);
}
