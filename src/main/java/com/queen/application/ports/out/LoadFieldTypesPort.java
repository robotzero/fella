package com.queen.application.ports.out;

import com.queen.infrastructure.persitence.FieldType;
import reactor.core.publisher.Flux;

public interface LoadFieldTypesPort {
	Flux<FieldType> loadFieldTypesByMonitorType(final String monitorTypeId);
}
