package com.queen.application.ports.out;

import com.queen.infrastructure.persitence.MonitorType;

import java.util.List;

public interface CreateManyMonitorTypesPort {
	void createMonitorTypes(final List<MonitorType> monitorType);
}
