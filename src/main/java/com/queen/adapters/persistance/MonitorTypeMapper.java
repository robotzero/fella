package com.queen.adapters.persistance;

import com.queen.domain.monitortype.MonitorType;
import org.springframework.stereotype.Component;

@Component
public class MonitorTypeMapper {
	public MonitorType mapToDomain(com.queen.infrastructure.persitence.MonitorType monitorType) {
		final var domainMonitorType = new MonitorType();
		domainMonitorType.id = monitorType.id;
		domainMonitorType.name = monitorType.name;
		return domainMonitorType;
	}
}
