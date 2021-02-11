package com.queen.application.service;

import com.queen.adapters.persistance.MonitorTypeMapper;
import com.queen.application.ports.in.AllMonitorTypesQuery;
import com.queen.application.ports.out.LoadAllMonitorTypes;
import com.queen.domain.monitortype.MonitorType;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class MonitorTypeService implements AllMonitorTypesQuery {
	private final LoadAllMonitorTypes loadAllMonitorTypes;
	private final MonitorTypeMapper monitorTypeMapper;

	public MonitorTypeService(LoadAllMonitorTypes loadAllMonitorTypes, MonitorTypeMapper monitorTypeMapper) {
		this.loadAllMonitorTypes = loadAllMonitorTypes;
		this.monitorTypeMapper = monitorTypeMapper;
	}

	@Override
	public Flux<MonitorType> load() {
		final var allMonitorTypes = this.loadAllMonitorTypes.loadMonitorTypes();
		return allMonitorTypes.map(monitorType -> {
			return monitorTypeMapper.mapToDomain(monitorType);
		});
	}
}
