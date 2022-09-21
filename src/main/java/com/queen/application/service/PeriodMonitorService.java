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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
	public Mono<PeriodMonitor> createPeriodMonitor(final CreatePeriodMonitorCommand createPeriodMonitorCommand) {
		final var periodMonitorDTO = createPeriodMonitorCommand.periodMonitorDTO();
//		loadPeriodMonitors.loadPeriodMonitorByDate(periodMonitorDTO.monitorTypeId(), periodMonitorDTO.userId(), new Date());
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
	public Flux<PeriodMonitor> loadPeriodMonitors(final String monitorTypeId, final String userId, final Pageable pageable) {
		return loadPeriodMonitors.loadPeriodMonitors(monitorTypeId, userId, pageable)
				.map(periodMonitorMapper::mapToPeriodMonitor);
	}

	@Override
	public Mono<PeriodMonitor> loadPeriodMonitorByDate(final String monitorTypeId, final String userId, final String dateToSearchBy) throws ParseException {
		final var simpleDateFromat = new SimpleDateFormat("yyyy-MM-dd").parse(dateToSearchBy);
		return loadPeriodMonitors.loadPeriodMonitorByDate(monitorTypeId, userId, simpleDateFromat)
				.map(periodMonitorMapper::mapToPeriodMonitor);
	}
}
