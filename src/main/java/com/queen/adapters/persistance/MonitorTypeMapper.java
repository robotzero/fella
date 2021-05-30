package com.queen.adapters.persistance;

import com.queen.adapters.web.dto.MonitorTypeDTO;
import com.queen.domain.monitortype.MonitorType;

public class MonitorTypeMapper {
	public MonitorType mapToDomain(final com.queen.infrastructure.persitence.MonitorType monitorType) {
		return new MonitorType(monitorType.getId(), monitorType.getName(), null);
	}

	public MonitorType mapToDomain(final MonitorTypeDTO monitorTypeDTO) {
		return new MonitorType(monitorTypeDTO.id(), monitorTypeDTO.name(), null);
	}

	public com.queen.infrastructure.persitence.MonitorType mapToPersistence(final MonitorTypeDTO monitorTypeDTO) {
		return new com.queen.infrastructure.persitence.MonitorType(monitorTypeDTO.id(), monitorTypeDTO.name());
	}

	public com.queen.infrastructure.persitence.MonitorType mapToPersistence(final com.queen.application.service.dto.MonitorTypeDTO monitorTypeDTO) {
		return new com.queen.infrastructure.persitence.MonitorType(monitorTypeDTO.id(), monitorTypeDTO.name());
	}
}
