package com.queen.application.ports.out;

import com.queen.infrastructure.persitence.Fields;
import reactor.core.publisher.Flux;

import java.util.List;

public interface CreateFieldsPort {
	Flux<Fields> createFields(List<Fields> fieldsType);
}
