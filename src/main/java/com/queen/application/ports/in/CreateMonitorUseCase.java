package com.queen.application.ports.in;

import com.queen.domain.monitor.Monitor;
import reactor.core.publisher.Mono;

public interface CreateMonitorUseCase {
	Mono<Monitor> createMonitor(final CreateMonitorCommand createMonitorCommand);
}
