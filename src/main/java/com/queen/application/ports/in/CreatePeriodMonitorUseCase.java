package com.queen.application.ports.in;

import com.queen.domain.monitor.PeriodMonitor;
import reactor.core.publisher.Mono;

public interface CreatePeriodMonitorUseCase {
	Mono<PeriodMonitor> createPeriodMonitor(final CreatePeriodMonitorCommand createPeriodMonitorCommand);
}
