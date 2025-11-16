package com.queen.adapters.web.dto;

import com.queen.domain.Migraine;
import com.queen.domain.MigraineMapperPort;

import java.util.Optional;
import java.util.UUID;

public class MigraineMapper implements MigraineMapperPort {
	public Migraine mapToDomain(final UUID userId, MigraineRequest request) {
		return new Migraine(request.migraineId(), userId, request.migraineDateOrNow(), request.severityLevelOrDefault(), request.description().orElse(null));
	}

	@Override
	public MigraineDTO mapToDTO(final com.queen.infrastructure.persistence.Migraine migraine) {
		if (migraine == null) {
			return null;
		}
		return new MigraineDTO(migraine.getMigraineDate(), migraine.getId(), migraine.getSeverityLevel(), migraine.getDescription());
	}

	@Override
	public Optional<com.queen.infrastructure.persistence.Migraine> mapToPersistence(final Migraine migraine) {
		if (migraine != null && migraine.severityLevel() != null && migraine.severityLevel() > 0) {
			final var m = new com.queen.infrastructure.persistence.Migraine(migraine.userId(), migraine.migraineDate(), true);
			m.setSeverityLevel(migraine.severityLevel());
			m.setDescription(migraine.description());
			m.setId(migraine.migraineId().orElse(UUID.randomUUID()));
			return Optional.of(m);
		}
		return Optional.empty();
	}
}
