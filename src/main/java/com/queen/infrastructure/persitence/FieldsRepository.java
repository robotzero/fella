package com.queen.infrastructure.persitence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface FieldsRepository extends ReactiveCrudRepository<Fields, String> {
}
