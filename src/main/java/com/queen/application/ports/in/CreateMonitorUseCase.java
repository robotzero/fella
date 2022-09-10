package com.queen.application.ports.in;

import com.queen.domain.monitor.MonitorResult;
import reactor.core.publisher.Mono;

public interface CreateMonitorUseCase {
	Mono<MonitorResult> createMonitor(final CreateMonitorCommand createMonitorCommand);
}
