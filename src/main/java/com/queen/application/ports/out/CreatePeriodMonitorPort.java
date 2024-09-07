package com.queen.application.ports.out;

import com.queen.infrastructure.persistence.monitor.PeriodMonitor;
import reactor.core.publisher.Mono;

public interface CreatePeriodMonitorPort {
	Mono<PeriodMonitor> createPeriodMonitor(final PeriodMonitor monitor);
}
