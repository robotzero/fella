package com.queen.adapters.web.dto;

import com.queen.domain.Migraine;
import com.queen.domain.MigraineMapperPort;

import java.util.UUID;

public class MigraineMapper implements MigraineMapperPort {
	public Migraine mapToDomain(final UUID userId, MigraineRequest request) {
		return new Migraine(userId, request.migraineDateOrNow(), request.severityLevelOrDefault(), request.description().orElse(null));
	}

	@Override
	public MigraineDTO mapToDTO(com.queen.infrastructure.persistence.Migraine migraine) {
		return new MigraineDTO();
	}

	@Override
	public com.queen.infrastructure.persistence.Migraine mapToPersistence(final Migraine migraine) {
		if (migraine != null) {
			return new com.queen.infrastructure.persistence.Migraine(migraine.userId(), migraine.migraineDate(), true);
		}
		return null;
	}
}
