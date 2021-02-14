package com.queen.adapters.persistance;

import com.queen.domain.monitor.Monitor;

public class MonitorMapper {
	public Monitor mapToDomain(com.queen.infrastructure.persitence.Monitor monitor) {
		return new Monitor(monitor.getId(), monitor.getName());
	}
}
