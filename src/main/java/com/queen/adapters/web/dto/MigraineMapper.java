package com.queen.adapters.web.dto;

import com.queen.domain.Migraine;
import com.queen.domain.MigraineMapperPort;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MigraineMapper implements MigraineMapperPort {
	public Migraine mapToDomain(final UUID userId, MigraineRequest request) {
		return new Migraine(userId, request.migraineDateOrNow(), request.severityLevelOrDefault(), request.description().orElse(null));
	}

	@Override
	public List<MigraineDTO> mapToDTO(final List<com.queen.infrastructure.persistence.Migraine> migraine) {
		if (migraine == null) {
			return null;
		}
		return migraine.stream().map(m -> {
			return new MigraineDTO(m.getMigraineDate(), m.getSeverityLevel(), m.getDescription());
		}).toList();
	}

	@Override
	public Optional<com.queen.infrastructure.persistence.Migraine> mapToPersistence(final Migraine migraine) {
		if (migraine != null) {
			final var m = new com.queen.infrastructure.persistence.Migraine(migraine.userId(), migraine.migraineDate(), true);
			m.setSeverityLevel(migraine.severityLevel());
			m.setDescription(migraine.description());
			return Optional.of(m);
		}
		return Optional.empty();
	}
}
