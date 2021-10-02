package com.queen.adapters.persistance;

import com.queen.application.ports.out.CreateFieldsPort;
import com.queen.infrastructure.persitence.Fields;
import com.queen.infrastructure.persitence.FieldsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class FieldsPersistenceAdapter implements CreateFieldsPort {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final FieldsRepository fieldsRepository;

	public FieldsPersistenceAdapter(FieldsRepository fieldsRepository) {
		this.fieldsRepository = fieldsRepository;
	}

	@Override
	public void createFields(List<Fields> fields) {
		final var disposable = fieldsRepository.saveAll(fields).subscribe(
				result -> logger.debug("Saved fields"),
				error -> logger.error("Error saving fields", error)
		);
		//Optional.of(disposable.isDisposed()).filter(is -> is).ifPresentOrElse(d -> {}, disposable::dispose);
	}
}
