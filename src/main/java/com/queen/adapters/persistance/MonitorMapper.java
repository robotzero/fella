package com.queen.adapters.persistance;

import com.queen.application.service.dto.MonitorDTO;
import com.queen.domain.monitor.Monitor;
import com.queen.domain.monitor.MonitorResult;
import com.queen.domain.monitortype.MonitorTypeResult;
import com.queen.infrastructure.persitence.monitor.Period;
import com.queen.infrastructure.persitence.monitor.PeriodMonitor;

import java.util.Date;
import java.util.List;

public class MonitorMapper {
	public Monitor mapToDomain(final com.queen.infrastructure.persitence.Monitor monitor) {
		return new Monitor(monitor.getId(), monitor.getName(), null);
	}

	public MonitorResult mapToPeriodMonitor(final MonitorTypeResult monitorTypeResult, final PeriodMonitor periodMonitor) {
		return switch (monitorTypeResult) {
			case MonitorTypeResult.Period period -> new MonitorResult.PeriodMonitor(periodMonitor.getId(), Date.from(periodMonitor.getStartDate()), Date.from(periodMonitor.getStartDate()), periodMonitor.getNotes(), period, List.of());
			case MonitorTypeResult.Stomach stomach -> throw new RuntimeException("TODO");
			case MonitorTypeResult.TabletsTaken tabletsTaken  -> throw new RuntimeException("TODO");
		};
	}

	public MonitorResult mapToPeriodMonitorWithPeriod(final MonitorResult monitorResult, final List<Period> periods) {
		return switch (monitorResult) {
			case MonitorResult.PeriodMonitor periodMonitorResult -> new MonitorResult.PeriodMonitor(periodMonitorResult.id(), periodMonitorResult.startDate(), periodMonitorResult.endDate(), periodMonitorResult.notes(), periodMonitorResult.monitorTypeResult(), periods);
			case MonitorResult.StomachMonitor stomachMonitor -> throw new RuntimeException("TODO");
			case MonitorResult.TabletsTakenMonitor tabletsTakenMonitor -> throw new RuntimeException("TODO");
		};
	}

	public com.queen.infrastructure.persitence.monitor.PeriodMonitor mapToPersistence(final MonitorDTO.PeriodMonitorDTO monitor) {
		return new com.queen.infrastructure.persitence.monitor.PeriodMonitor(monitor.id(), monitor.monitorTypeId(), monitor.userId(), monitor.startDate().toInstant());
	}

	public com.queen.infrastructure.persitence.monitor.Period mapToPersistence(final MonitorDTO.PeriodDTO monitor) {
		return new com.queen.infrastructure.persitence.monitor.Period(monitor.id(), monitor.periodMonitorId(), monitor.painLevel());
	}
}
