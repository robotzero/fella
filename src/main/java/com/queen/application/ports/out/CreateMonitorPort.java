package com.queen.application.ports.out;

import com.queen.infrastructure.persitence.Monitor;
import reactor.core.publisher.Mono;

public interface CreateMonitorPort {
	Mono<Monitor> createMonitor(final Monitor monitor);
}
