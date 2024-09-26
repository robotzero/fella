package com.queen.domain;

import com.queen.adapters.web.dto.MigraineDTO;

public interface MigraineMapperPort {
	MigraineDTO mapToDTO(final com.queen.infrastructure.persistence.Migraine migraine);
	com.queen.infrastructure.persistence.Migraine mapToPersistence(final com.queen.domain.Migraine migraine);
}
