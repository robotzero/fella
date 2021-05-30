package com.queen.application.ports.out;

import com.queen.infrastructure.persitence.MonitorType;
import reactor.core.publisher.Mono;

public interface CreateMonitorTypePort {
	Mono<MonitorType> createMonitorType(final MonitorType monitorType);
}
