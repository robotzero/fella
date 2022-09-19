package com.queen.application.service;

import com.queen.adapters.persistance.PeriodMonitorMapper;
import com.queen.application.ports.in.CreatePeriodMonitorCommand;
import com.queen.application.ports.in.CreatePeriodMonitorUseCase;
import com.queen.application.ports.in.PeriodMonitorQuery;
import com.queen.application.ports.out.CreatePeriodMonitorPort;
import com.queen.application.ports.out.LoadPeriodMonitorsPort;
import com.queen.application.service.exception.MonitorException;
import com.queen.domain.monitor.PeriodMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PeriodMonitorService implements PeriodMonitorQuery, CreatePeriodMonitorUseCase {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private final LoadPeriodMonitorsPort loadPeriodMonitors;
	private final PeriodMonitorMapper periodMonitorMapper;
	private final CreatePeriodMonitorPort createPeriodMonitorPort;

	public PeriodMonitorService(
			final LoadPeriodMonitorsPort loadPeriodMonitors,
			final PeriodMonitorMapper periodMonitorMapper,
			final CreatePeriodMonitorPort createPeriodMonitorPort
	) {
		this.loadPeriodMonitors       = loadPeriodMonitors;
		this.periodMonitorMapper      = periodMonitorMapper;
		this.createPeriodMonitorPort  = createPeriodMonitorPort;
	}

	@Override
	@Transactional
	public Mono<PeriodMonitor> createPeriodMonitor(CreatePeriodMonitorCommand createPeriodMonitorCommand) {
		final var periodMonitorDTO = createPeriodMonitorCommand.periodMonitorDTO();
		final var createdPeriodMonitor = createPeriodMonitorPort.createPeriodMonitor(
				periodMonitorMapper.mapToPersistence(periodMonitorDTO).setAsNew()
				).doOnError(error -> {
							log.error("Failed to save period monitor");
							throw new MonitorException("Failed to save monitor", error);
						}
				).doOnSuccess(monitor -> log.info("Done saving period monitor"));

		return createdPeriodMonitor.map(periodMonitorMapper::mapToPeriodMonitor);
	}

	@Override
	public Flux<PeriodMonitor> loadPeriodMonitors(String monitorTypeId, String userId, Pageable pageable) {
		return loadPeriodMonitors.loadPeriodMonitors(monitorTypeId, userId, pageable)
				.map(periodMonitorMapper::mapToPeriodMonitor);
	}
}
