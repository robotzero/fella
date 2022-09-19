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
		return new PeriodMonitorResult.PeriodMonitor(
				periodMonitor.getId(),
				Date.from(periodMonitor.getStartDate()),
				periodMonitor.getNotes(),
				monitorTypeResult,
				List.of()
		);
	}

	public PeriodMonitorResult mapToPeriodMonitorWithPeriod(final PeriodMonitorResult.PeriodMonitor periodMonitorResult, final List<Period> periods) {
		return new PeriodMonitorResult.PeriodMonitor(periodMonitorResult.id(), periodMonitorResult.startDate(), periodMonitorResult.notes(), periodMonitorResult.monitorTypeResult(), periods);
	}

	public com.queen.infrastructure.persitence.monitor.PeriodMonitor mapToPersistence(final MonitorDTO.PeriodMonitorDTO monitor) {
		return new com.queen.infrastructure.persitence.monitor.PeriodMonitor(monitor.id(), monitor.monitorTypeId(), monitor.userId(), monitor.startDate().toInstant(), "");
	}

	public com.queen.infrastructure.persitence.monitor.Period mapToPersistence(final MonitorDTO.PeriodDTO monitor) {
		return new com.queen.infrastructure.persitence.monitor.Period(monitor.id(), monitor.periodMonitorId(), monitor.painLevel());
	}
}
