package com.queen.infrastructure.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface MigraineRepository extends ReactiveCrudRepository<Migraine, String> {
}
