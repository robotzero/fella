package com.queen.adapters.persistance;

import com.queen.domain.monitor.Monitor;

public class MonitorMapper {
	public Monitor mapToDomain(final com.queen.infrastructure.persitence.Monitor monitor) {
		return new Monitor(monitor.getId(), monitor.getName(), null);
	}

	public com.queen.infrastructure.persitence.Monitor mapToPersistence(final com.queen.application.service.dto.MonitorDTO monitor) {
		return new com.queen.infrastructure.persitence.Monitor(monitor.id(), monitor.name(), monitor.userId());
	}
}
