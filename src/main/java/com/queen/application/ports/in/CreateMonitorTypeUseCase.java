package com.queen.application.ports.in;

import com.queen.domain.monitortype.MonitorType;
import reactor.core.publisher.Flux;

public interface CreateMonitorTypeUseCase {
	Flux<MonitorType> createManyMonitorTypes(final CreateMonitorTypeCommand createMonitorTypeCommand);
}
