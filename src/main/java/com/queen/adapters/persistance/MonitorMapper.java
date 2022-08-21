package com.queen.adapters.persistance;

import com.queen.domain.monitor.Monitor;
import com.queen.domain.monitor.MonitorResult;
import com.queen.domain.monitortype.MonitorTypeResult;
import com.queen.infrastructure.persitence.monitor.PeriodMonitor;

import java.util.Date;

public class MonitorMapper {
	public Monitor mapToDomain(final com.queen.infrastructure.persitence.Monitor monitor) {
		return new Monitor(monitor.getId(), monitor.getName(), null);
	}

	public MonitorResult mapToPeriodMonitor(final MonitorTypeResult monitorTypeResult, final PeriodMonitor periodMonitor) {
		return switch (monitorTypeResult) {
			case MonitorTypeResult.Period period -> new MonitorResult.PeriodMonitor(periodMonitor.getId(), Date.from(periodMonitor.getStartDate()), Date.from(periodMonitor.getStartDate()), periodMonitor.getPainLevel(), periodMonitor.getNotes(), period);
			case MonitorTypeResult.Stomach stomach -> throw new RuntimeException("TODO");
			case MonitorTypeResult.TabletsTaken tabletsTaken  -> throw new RuntimeException("TODO");
		};
	}

	public com.queen.infrastructure.persitence.Monitor mapToPersistence(final com.queen.application.service.dto.MonitorDTO monitor) {
		return new com.queen.infrastructure.persitence.Monitor(monitor.id(), monitor.name(), monitor.userId());
	}
}
