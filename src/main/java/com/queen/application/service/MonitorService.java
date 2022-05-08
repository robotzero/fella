package com.queen.application.service;

import com.queen.adapters.persistance.MonitorMapper;
import com.queen.application.ports.in.CreateMonitorCommand;
import com.queen.application.ports.in.CreateMonitorUseCase;
import com.queen.application.ports.in.MonitorQuery;
import com.queen.application.ports.out.CreateMonitorPort;
import com.queen.application.ports.out.LoadMonitorsPort;
import com.queen.application.service.exception.MonitorException;
import com.queen.domain.monitor.Monitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MonitorService implements MonitorQuery, CreateMonitorUseCase {
	private final LoadMonitorsPort loadMonitors;
	private final MonitorMapper monitorMapper;
	private final CreateMonitorPort createMonitorPort;
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	public MonitorService(
			final LoadMonitorsPort loadMonitors,
			final MonitorMapper monitorMapper,
			final CreateMonitorPort createMonitorPort
	) {
		this.loadMonitors      = loadMonitors;
		this.monitorMapper     = monitorMapper;
		this.createMonitorPort = createMonitorPort;
	}

	@Override
	public Flux<Monitor> loadMonitors(final String monitorTypeId, final String userId, final Pageable pageable) {
		return loadMonitors.loadMonitors(monitorTypeId, userId, pageable).map(monitorMapper::mapToDomain);
	}

	@Transactional
	@Override
	public Mono<Monitor> createMonitor(final CreateMonitorCommand createMonitorCommand) {
		return createMonitorPort.createMonitor(monitorMapper.mapToPersistence(createMonitorCommand.monitorDTO()).setAsNew())
				.doOnError(error -> {
					log.error("Failed to save monitor", error);
					throw new MonitorException("failed to save monitor", error);
				})
				.doOnSuccess(monitor -> log.info("Done saving monitor"))
				.map(monitorMapper::mapToDomain)
				.cast(Monitor.class);
	}
}
