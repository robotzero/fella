package com.queen.adapters.persistance;

import com.queen.application.ports.out.CreateFieldsPort;
import com.queen.infrastructure.persitence.Fields;
import com.queen.infrastructure.persitence.FieldsRepository;
import reactor.core.publisher.Flux;

import java.util.List;

public class FieldsPersistenceAdapter implements CreateFieldsPort {
	private final FieldsRepository fieldsRepository;

	public FieldsPersistenceAdapter(final FieldsRepository fieldsRepository) {
		this.fieldsRepository = fieldsRepository;
	}

	@Override
	public Flux<Fields> createFields(final List<Fields> fields) {
		return fieldsRepository.saveAll(fields);
	}
}
