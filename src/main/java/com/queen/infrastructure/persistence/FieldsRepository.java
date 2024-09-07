package com.queen.infrastructure.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldsRepository extends ReactiveCrudRepository<Fields, String> {
}
