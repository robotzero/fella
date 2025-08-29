package com.queen.domain;

import com.queen.adapters.web.dto.MigraineDTO;

import java.util.List;
import java.util.Optional;

public interface MigraineMapperPort {
	List<MigraineDTO> mapToDTO(final List<com.queen.infrastructure.persistence.Migraine> migraine);
	Optional<com.queen.infrastructure.persistence.Migraine> mapToPersistence(final com.queen.domain.Migraine migraine);
}
