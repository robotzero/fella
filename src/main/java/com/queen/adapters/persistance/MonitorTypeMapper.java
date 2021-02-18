package com.queen.adapters.persistance;

import com.queen.domain.monitortype.MonitorType;

public class MonitorTypeMapper {
	public MonitorType mapToDomain(com.queen.infrastructure.persitence.MonitorType monitorType) {
		return new MonitorType(monitorType.getId(), monitorType.getName(), monitorType.getUserId());
	}
}
