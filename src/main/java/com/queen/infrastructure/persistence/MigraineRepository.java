package com.queen.infrastructure.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface MigraineRepository extends CrudRepository<Migraine, UUID> {
}
