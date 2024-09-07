package com.queen.application.ports.out;

import com.queen.infrastructure.persistence.FieldType;
import reactor.core.publisher.Flux;

public interface LoadFieldTypesPort {
	Flux<FieldType> loadFieldTypesByMonitorType(final String monitorTypeId);
}
