package com.queen.application.ports.in;

import com.queen.domain.monitor.PeriodMonitor;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;

public interface PeriodMonitorQuery {
	Flux<PeriodMonitor> loadPeriodMonitors(final String monitorTypeId, final String userId, Pageable pageable);
}
