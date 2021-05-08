package com.queen.adapters.persistance;

import com.queen.application.ports.out.LoadFieldTypesPort;
import com.queen.infrastructure.persitence.FieldType;
import com.queen.infrastructure.persitence.FieldTypesRepository;
import reactor.core.publisher.Flux;

public class FieldTypePersistenceAdapter implements LoadFieldTypesPort {
	private final FieldTypesRepository fieldTypesRepository;

	public FieldTypePersistenceAdapter(final FieldTypesRepository fieldTypesRepository) {
		this.fieldTypesRepository = fieldTypesRepository;
	}

	@Override
	public Flux<FieldType> loadFieldTypesByMonitorType(final String monitorTypeId) {
		return fieldTypesRepository.findByMonitorTypeId(monitorTypeId);
	}
}
