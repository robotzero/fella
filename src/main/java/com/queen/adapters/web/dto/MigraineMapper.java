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
		if (migraine == null) {
			return null;
		}
		return new MigraineDTO(migraine.getMigraineDate(), migraine.getSeverityLevel(), migraine.getDescription());
	}

	@Override
	public com.queen.infrastructure.persistence.Migraine mapToPersistence(final Migraine migraine) {
		if (migraine != null) {
			final var m = new com.queen.infrastructure.persistence.Migraine(migraine.userId(), migraine.migraineDate(), true);
			m.setSeverityLevel(migraine.severityLevel());
			m.setDescription(migraine.description());
			return m;
		}
		return null;
	}
}
