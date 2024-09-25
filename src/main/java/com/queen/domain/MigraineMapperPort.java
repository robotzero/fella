package com.queen.domain;

import com.queen.adapters.web.dto.MigraineDTO;

import java.util.UUID;

public interface MigraineMapperPort {
	MigraineDTO mapToDTO(final com.queen.infrastructure.persistence.Migraine migraine);
	com.queen.infrastructure.persistence.Migraine mapToPersistence(final com.queen.domain.Migraine migraine, final UUID periodId);
}
