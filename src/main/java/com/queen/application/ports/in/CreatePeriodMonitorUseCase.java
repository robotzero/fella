package com.queen.application.ports.in;

import com.queen.domain.monitor.PeriodMonitorResult;
import reactor.core.publisher.Mono;

public interface CreatePeriodMonitorUseCase {
	Mono<PeriodMonitorResult> createPeriodMonitor(final CreatePeriodMonitorCommand createPeriodMonitorCommand);
}
