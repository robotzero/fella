package com.queen.domain;

import com.queen.infrastructure.persistence.Migraine;

public interface MigrainePersistencePort {
	Migraine createMigraine(final Migraine migraine);
	void updateMigraine(final Migraine migraine);
}
