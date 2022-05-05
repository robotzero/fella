package com.queen.application.ports.out;

import com.queen.infrastructure.persitence.Monitor;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;

public interface LoadMonitorsPort {
	Flux<Monitor> loadMonitors(final String monitorTypeId, final String userId, final Pageable pageable);
}
