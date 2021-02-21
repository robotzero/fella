package com.queen.adapters.persistance;

import com.queen.domain.monitortype.MonitorType;

public class MonitorTypeMapper {
	public MonitorType mapToDomain(com.queen.infrastructure.persitence.MonitorType monitorType) {
		return new MonitorType(monitorType.getId(), monitorType.getName(), monitorType.getUserId(), null);
	}

	public MonitorType mapToDomain(com.queen.adapters.web.MonitorTypeDTO monitorTypeDTO) {
		return new MonitorType(monitorTypeDTO.id(), monitorTypeDTO.name(), monitorTypeDTO.userId(), null);
	}

	public com.queen.infrastructure.persitence.MonitorType mapToPersistence(com.queen.adapters.web.MonitorTypeDTO monitorTypeDTO) {
		return new com.queen.infrastructure.persitence.MonitorType(monitorTypeDTO.id(), monitorTypeDTO.name(), monitorTypeDTO.userId());
	}
}
