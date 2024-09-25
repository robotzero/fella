package com.queen.domain;

import com.queen.infrastructure.persistence.Migraine;
import reactor.core.publisher.Mono;

public interface MigrainePersistencePort {
	Mono<Migraine> createMigraine(final Migraine migraine);
}
