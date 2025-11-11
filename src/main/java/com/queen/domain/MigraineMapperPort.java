package com.queen.domain;

import com.queen.adapters.web.dto.MigraineDTO;

import java.util.Optional;

public interface MigraineMapperPort {
	MigraineDTO mapToDTO(final com.queen.infrastructure.persistence.Migraine migraine);
	Optional<com.queen.infrastructure.persistence.Migraine> mapToPersistence(final com.queen.domain.Migraine migraine);
}
