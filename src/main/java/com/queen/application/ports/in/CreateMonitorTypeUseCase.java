package com.queen.application.ports.in;

import com.queen.infrastructure.persitence.Fields;
import reactor.core.publisher.Flux;

public interface CreateMonitorTypeUseCase {
	Flux<Fields> createManyMonitorTypes(final CreateMonitorTypeCommand createMonitorTypeCommand);
}
