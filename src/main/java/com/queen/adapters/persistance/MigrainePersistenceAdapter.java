package com.queen.adapters.persistance;

import com.queen.infrastructure.persistence.Migraine;
import com.queen.domain.MigrainePersistencePort;
import com.queen.infrastructure.persistence.MigraineRepository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

public class MigrainePersistenceAdapter implements MigrainePersistencePort {
	private final MigraineRepository migraineRepository;

	public MigrainePersistenceAdapter(final MigraineRepository migraineRepository) {
		this.migraineRepository = migraineRepository;
	}

	@Override
	@Transactional
	public Mono<Migraine> createMigraine(final Migraine migraine) {
		if (migraine == null) {
			return Mono.empty();
		}
		return migraineRepository.save(migraine);
	}
}
