package com.queen.adapters.persistance;

import com.queen.adapters.web.dto.MonitorTypeDTO;
import com.queen.domain.monitortype.MonitorType;

public class MonitorTypeMapper {
	public MonitorType mapToDomain(com.queen.infrastructure.persitence.MonitorType monitorType) {
		return new MonitorType(monitorType.getId(), monitorType.getName(), null);
	}

	public MonitorType mapToDomain(MonitorTypeDTO monitorTypeDTO) {
		return new MonitorType(monitorTypeDTO.id(), monitorTypeDTO.name(), null);
	}

	public com.queen.infrastructure.persitence.MonitorType mapToPersistence(MonitorTypeDTO monitorTypeDTO) {
		return new com.queen.infrastructure.persitence.MonitorType(monitorTypeDTO.id(), monitorTypeDTO.name());
	}
}
