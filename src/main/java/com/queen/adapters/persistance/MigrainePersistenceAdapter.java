package com.queen.adapters.persistance;

import com.queen.infrastructure.persistence.Migraine;
import com.queen.domain.MigrainePersistencePort;
import com.queen.infrastructure.persistence.MigraineRepository;
import org.springframework.transaction.annotation.Transactional;

public class MigrainePersistenceAdapter implements MigrainePersistencePort {
	private final MigraineRepository migraineRepository;

	public MigrainePersistenceAdapter(final MigraineRepository migraineRepository) {
		this.migraineRepository = migraineRepository;
	}

	@Override
	@Transactional
	public Migraine createMigraine(final Migraine migraine) {
		return migraineRepository.save(migraine);
	}
}
