package com.queen.application.service;

import com.queen.adapters.persistance.PeriodMonitorMapper;
import com.queen.application.ports.in.CreatePeriodMonitorCommand;
import com.queen.application.ports.in.CreatePeriodMonitorUseCase;
import com.queen.application.ports.in.PeriodMonitorQuery;
import com.queen.application.ports.out.CreatePeriodMonitorPort;
import com.queen.application.ports.out.LoadPeriodMonitorsPort;
import com.queen.application.service.dto.MonitorDTO;
import com.queen.application.service.exception.MonitorException;
import com.queen.domain.monitor.PeriodMonitorResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class PeriodMonitorService implements PeriodMonitorQuery, CreatePeriodMonitorUseCase {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private final LoadPeriodMonitorsPort loadPeriodMonitors;
	private final PeriodMonitorMapper periodMonitorMapper;
	private final CreatePeriodMonitorPort createPeriodMonitorPort;
	private final MonitorTypeService monitorTypeService;

	public PeriodMonitorService(
			final LoadPeriodMonitorsPort loadPeriodMonitors,
			final PeriodMonitorMapper periodMonitorMapper,
			final CreatePeriodMonitorPort createPeriodMonitorPort,
			final MonitorTypeService monitorTypeService
	) {
		this.loadPeriodMonitors       = loadPeriodMonitors;
		this.periodMonitorMapper      = periodMonitorMapper;
		this.createPeriodMonitorPort  = createPeriodMonitorPort;
		this.monitorTypeService = monitorTypeService;
	}

	@Override
	@Transactional
	public Mono<PeriodMonitorResult> createPeriodMonitor(CreatePeriodMonitorCommand createPeriodMonitorCommand) {
		return switch (createPeriodMonitorCommand.monitorDTO()) {
			case MonitorDTO.PeriodMonitorDTO periodMonitorDTO -> {
				final var singleMonitorType = monitorTypeService.loadSingleMonitorType(periodMonitorDTO.monitorTypeId(), periodMonitorDTO.userId());
				final var createdPeriodMonitor = createPeriodMonitorPort.createPeriodMonitor(
						periodMonitorMapper.mapToPersistence(periodMonitorDTO).setAsNew()
				).doOnError(error -> {
							log.error("Failed to save period monitor");
							throw new MonitorException("Failed to save monitor", error);
						}
				).doOnSuccess(monitor -> log.info("Done saving period monitor"));

				final var createdPeriod = createPeriodMonitorPort.createPeriod(
						periodMonitorMapper.mapToPersistence(periodMonitorDTO.periodDTO()).setAsNew()
				).doOnError(error -> {
							log.error("Failed to save period monitor");
							throw new MonitorException("Failed to save monitor", error);
						}
				).doOnSuccess(monitor -> log.info("Done saving period"));

				yield singleMonitorType.zipWith(createdPeriodMonitor).map(tuple -> {
					final var monitorTypeResult = tuple.getT1();
					final var periodMonitor = tuple.getT2();
					return periodMonitorMapper.mapToPeriodMonitor(monitorTypeResult, periodMonitor);
				}).zipWith(createdPeriod).map(tuple -> {
					final var monitorResult = tuple.getT1();
					final var period = tuple.getT2();
					return periodMonitorMapper.mapToPeriodMonitorWithPeriod(monitorResult, List.of(period));
				});
			}
		};
	}

	@Override
	public Flux<PeriodMonitorResult> loadPeriodMonitors(String monitorTypeId, String userId, Pageable pageable) {
		final var singleMonitorType = monitorTypeService.loadSingleMonitorType(monitorTypeId, userId);
		return singleMonitorType.flatMapMany(monitorTypeResult -> {
			return switch (monitorTypeResult) {
				case com.queen.domain.monitortype.MonitorTypeResult.Period period -> {
					yield loadPeriodMonitors.loadPeriodMonitors(monitorTypeId, userId, pageable).map(persistancePeriodMonitor -> periodMonitorMapper.mapToPeriodMonitor(monitorTypeResult, persistancePeriodMonitor));
				}
				case com.queen.domain.monitortype.MonitorTypeResult.Stomach stomach -> throw new IllegalStateException("Not implemented yet");
				case com.queen.domain.monitortype.MonitorTypeResult.TabletsTaken tabletsTaken -> throw new IllegalStateException("Not implemented yet");
			};
		});
	}
}
