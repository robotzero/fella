package com.queen.application.service;

import com.queen.adapters.persistance.MonitorMapper;
import com.queen.application.ports.in.CreateMonitorCommand;
import com.queen.application.ports.in.CreateMonitorUseCase;
import com.queen.application.ports.in.MonitorQuery;
import com.queen.application.ports.out.CreateMonitorPort;
import com.queen.application.ports.out.LoadMonitorsPort;
import com.queen.application.service.dto.MonitorDTO;
import com.queen.application.service.exception.MonitorException;
import com.queen.domain.monitor.Monitor;
import com.queen.domain.monitor.MonitorResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class MonitorService implements MonitorQuery, CreateMonitorUseCase {
	private final LoadMonitorsPort loadMonitors;
	private final MonitorMapper monitorMapper;
	private final CreateMonitorPort createMonitorPort;
	private final MonitorTypeService monitorTypeService;
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	public MonitorService(
			final LoadMonitorsPort loadMonitors,
			final MonitorMapper monitorMapper,
			final CreateMonitorPort createMonitorPort,
			final MonitorTypeService monitorTypeService
	) {
		this.loadMonitors       = loadMonitors;
		this.monitorMapper      = monitorMapper;
		this.createMonitorPort  = createMonitorPort;
		this.monitorTypeService = monitorTypeService;
	}

	@Override
	public Flux<Monitor> loadMonitors(final String monitorTypeId, final String userId, final Pageable pageable) {
		final var monitors = loadMonitors.loadMonitors(monitorTypeId, userId, pageable).map(monitorMapper::mapToDomain);
		final var singleMonitorType = monitorTypeService.load(monitorTypeId, userId);
		return monitors.zipWith(singleMonitorType, (monitorDomain, monitorType) -> new Monitor(monitorDomain.id(), monitorDomain.name(), monitorType));
	}

	@Override
	public Flux<MonitorResult> loadCustomMonitors(final String monitorTypeId, final String userId, final Pageable pageable) {
		final var singleMonitorType = monitorTypeService.loadSingleMonitorType(monitorTypeId, userId);
		return singleMonitorType.flatMapMany(monitorTypeResult -> {
			return switch (monitorTypeResult) {
				case com.queen.domain.monitortype.MonitorTypeResult.Period period -> {
					yield loadMonitors.loadPeriodMonitors(monitorTypeId, userId, pageable).map(persistancePeriodMonitor -> monitorMapper.mapToPeriodMonitor(monitorTypeResult, persistancePeriodMonitor));
				}
				case com.queen.domain.monitortype.MonitorTypeResult.Stomach stomach -> throw new IllegalStateException("Not implemented yet");
				case com.queen.domain.monitortype.MonitorTypeResult.TabletsTaken tabletsTaken -> throw new IllegalStateException("Not implemented yet");
			};
		});
	}

	@Transactional
	@Override
	public Mono<MonitorResult> createMonitor(final CreateMonitorCommand createMonitorCommand) {
		return switch (createMonitorCommand.monitorDTO()) {
			case MonitorDTO.PeriodMonitorDTO periodMonitorDTO -> {
				final var singleMonitorType = monitorTypeService.loadSingleMonitorType(periodMonitorDTO.monitorTypeId(), periodMonitorDTO.userId());
				final var createdPeriodMonitor = createMonitorPort.createPeriodMonitor(
						monitorMapper.mapToPersistence(periodMonitorDTO).setAsNew()
				).doOnError(error -> {
							log.error("Failed to save period monitor");
							throw new MonitorException("Failed to save monitor", error);
						}
				).doOnSuccess(monitor -> log.info("Done saving period monitor"));

				final var createdPeriod = createMonitorPort.createPeriod(
						monitorMapper.mapToPersistence(periodMonitorDTO.periodDTO()).setAsNew()
				).doOnError(error -> {
							log.error("Failed to save period monitor");
							throw new MonitorException("Failed to save monitor", error);
						}
				).doOnSuccess(monitor -> log.info("Done saving period monitor"));

				yield singleMonitorType.zipWith(createdPeriodMonitor).map(tuple -> {
					final var monitorTypeResult = tuple.getT1();
					final var periodMonitor = tuple.getT2();
					return monitorMapper.mapToPeriodMonitor(monitorTypeResult, periodMonitor);
				}).zipWith(createdPeriod).map(tuple -> {
					final var monitorResult = tuple.getT1();
					final var period = tuple.getT2();
					return monitorMapper.mapToPeriodMonitorWithPeriod(monitorResult, List.of(period));
				});
			}
		};
//		return createMonitorPort.createMonitor(monitorMapper.mapToPersistence(createMonitorCommand.monitorDTO()).setAsNew())
//				.doOnError(error -> {
//					log.error("Failed to save monitor", error);
//					throw new MonitorException("failed to save monitor", error);
//				})
//				.doOnSuccess(monitor -> log.info("Done saving monitor"))
//				.map(monitorMapper::mapToDomain)
//				.cast(Monitor.class);
	}
}
