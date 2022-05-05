package com.queen.application.service;

import com.queen.adapters.persistance.MonitorMapper;
import com.queen.application.ports.in.MonitorQuery;
import com.queen.application.ports.out.LoadMonitorsPort;
import com.queen.domain.monitor.Monitor;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;

public class MonitorService implements MonitorQuery {
	private final LoadMonitorsPort loadMonitors;
	private final MonitorMapper monitorMapper;

	public MonitorService(final LoadMonitorsPort loadMonitors, final MonitorMapper monitorMapper) {
		this.loadMonitors = loadMonitors;
		this.monitorMapper   = monitorMapper;
	}

	@Override
	public Flux<Monitor> loadMonitors(final String monitorTypeId, final String userId, final Pageable pageable) {
		return loadMonitors.loadMonitors(monitorTypeId, userId, pageable).map(monitor -> {
			return monitorMapper.mapToDomain(monitor);
		});
	}
}
