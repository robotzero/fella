package com.queen.adapters.persistance;

import com.queen.application.service.dto.MonitorDTO;
import com.queen.domain.monitor.PeriodMonitor;
import com.queen.domain.monitor.PeriodMonitorResult;
import com.queen.domain.monitortype.MonitorTypeResult;
import com.queen.infrastructure.persitence.monitor.Period;

import java.util.Date;
import java.util.List;

public class PeriodMonitorMapper {
	public PeriodMonitor mapToDomain(final com.queen.infrastructure.persitence.Monitor monitor) {
		return new PeriodMonitor(monitor.getId(), monitor.getName(), null);
	}

	public PeriodMonitorResult mapToPeriodMonitor(final MonitorTypeResult monitorTypeResult, final com.queen.infrastructure.persitence.monitor.PeriodMonitor periodMonitor) {
		return switch (monitorTypeResult) {
			case MonitorTypeResult.Period period -> new PeriodMonitorResult.PeriodMonitor(periodMonitor.getId(), Date.from(periodMonitor.getStartDate()), Date.from(periodMonitor.getStartDate()), periodMonitor.getNotes(), period, List.of());
			case MonitorTypeResult.Stomach stomach -> throw new RuntimeException("TODO");
			case MonitorTypeResult.TabletsTaken tabletsTaken  -> throw new RuntimeException("TODO");
		};
	}

	public PeriodMonitorResult mapToPeriodMonitorWithPeriod(final PeriodMonitorResult monitorResult, final List<Period> periods) {
		return switch (monitorResult) {
			case PeriodMonitorResult.PeriodMonitor periodMonitorResult -> new PeriodMonitorResult.PeriodMonitor(periodMonitorResult.id(), periodMonitorResult.startDate(), periodMonitorResult.endDate(), periodMonitorResult.notes(), periodMonitorResult.monitorTypeResult(), periods);
			case PeriodMonitorResult.StomachMonitor stomachMonitor -> throw new RuntimeException("TODO");
			case PeriodMonitorResult.TabletsTakenMonitor tabletsTakenMonitor -> throw new RuntimeException("TODO");
		};
	}

	public com.queen.infrastructure.persitence.monitor.PeriodMonitor mapToPersistence(final MonitorDTO.PeriodMonitorDTO monitor) {
		return new com.queen.infrastructure.persitence.monitor.PeriodMonitor(monitor.id(), monitor.monitorTypeId(), monitor.userId(), monitor.startDate().toInstant(), "");
	}

	public com.queen.infrastructure.persitence.monitor.Period mapToPersistence(final MonitorDTO.PeriodDTO monitor) {
		return new com.queen.infrastructure.persitence.monitor.Period(monitor.id(), monitor.periodMonitorId(), monitor.painLevel());
	}
}
