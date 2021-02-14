package com.queen.application.service;

import com.queen.adapters.persistance.MonitorMapper;
import com.queen.application.ports.in.AllMonitorQuery;
import com.queen.application.ports.out.LoadAllMonitors;
import com.queen.domain.monitor.Monitor;
import reactor.core.publisher.Flux;

public class MonitorService implements AllMonitorQuery {
	private final LoadAllMonitors loadAllMonitors;
	private final MonitorMapper monitorMapper;

	public MonitorService(final LoadAllMonitors loadAllMonitors, final MonitorMapper monitorMapper) {
		this.loadAllMonitors = loadAllMonitors;
		this.monitorMapper   = monitorMapper;
	}

	@Override
	public Flux<Monitor> loadAllMonitors() {
		return loadAllMonitors.loadAllMonitors().map(monitor -> {
			return monitorMapper.mapToDomain(monitor);
		});
	}
}
