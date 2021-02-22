package com.queen.application.ports.out;

import com.queen.infrastructure.persitence.FieldType;
import reactor.core.publisher.Flux;

import javax.validation.constraints.NotNull;

public interface LoadFieldTypesPort {
	Flux<FieldType> loadFieldTypesByMonitorType(final @NotNull String monitorTypeId);
}
