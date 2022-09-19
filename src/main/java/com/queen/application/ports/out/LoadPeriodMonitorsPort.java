package com.queen.application.ports.out;

import com.queen.infrastructure.persitence.monitor.PeriodMonitor;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;

public interface LoadPeriodMonitorsPort {
	Flux<PeriodMonitor> loadPeriodMonitors(final String monitorTypeId, final String userId, final Pageable pageable);
}
