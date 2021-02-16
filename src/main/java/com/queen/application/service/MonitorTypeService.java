package com.queen.application.service;

import com.queen.adapters.persistance.MonitorTypeMapper;
import com.queen.application.ports.in.AllMonitorTypesQuery;
import com.queen.application.ports.out.LoadAllMonitorTypesPort;
import com.queen.domain.monitortype.MonitorType;
import reactor.core.publisher.Flux;

public class MonitorTypeService implements AllMonitorTypesQuery {
	private final LoadAllMonitorTypesPort loadAllMonitorTypes;
	private final MonitorTypeMapper monitorTypeMapper;

	public MonitorTypeService(final LoadAllMonitorTypesPort loadAllMonitorTypes, final MonitorTypeMapper monitorTypeMapper) {
		this.loadAllMonitorTypes = loadAllMonitorTypes;
		this.monitorTypeMapper   = monitorTypeMapper;
	}

	@Override
	public Flux<MonitorType> load() {
		final var allMonitorTypes = this.loadAllMonitorTypes.loadAllMonitorTypes();
		return allMonitorTypes.map(monitorType -> {
			return monitorTypeMapper.mapToDomain(monitorType);
		});
	}
}
