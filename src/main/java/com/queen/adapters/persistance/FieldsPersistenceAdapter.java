package com.queen.adapters.persistance;

import com.queen.application.ports.out.CreateFieldsPort;
import com.queen.infrastructure.persitence.Fields;
import com.queen.infrastructure.persitence.FieldsRepository;

import java.util.List;
import java.util.Optional;

public class FieldsPersistenceAdapter implements CreateFieldsPort {
	private final FieldsRepository fieldsRepository;

	public FieldsPersistenceAdapter(FieldsRepository fieldsRepository) {
		this.fieldsRepository = fieldsRepository;
	}

	@Override
	public void createFields(List<Fields> fields) {
		final var disposable = fieldsRepository.saveAll(fields).subscribe();
		Optional.of(disposable.isDisposed()).ifPresentOrElse(d -> {}, disposable::dispose);
	}
}
