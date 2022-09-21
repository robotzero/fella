package com.queen.adapters.persistance;

import com.queen.application.service.dto.PeriodMonitorDTO;
import com.queen.domain.monitor.PeriodMonitor;

import java.util.Date;

public class PeriodMonitorMapper {

	public PeriodMonitor mapToPeriodMonitor(final com.queen.infrastructure.persitence.monitor.PeriodMonitor periodMonitor) {
		return new PeriodMonitor(
				periodMonitor.getId(),
				Date.from(periodMonitor.getPeriodDate()),
				periodMonitor.getPainLevel(),
				periodMonitor.getFlowLevel(),
				periodMonitor.getNotes(),
				"",
				Date.from(periodMonitor.getCreatedDate())

		);
	}

	public com.queen.infrastructure.persitence.monitor.PeriodMonitor mapToPersistence(final PeriodMonitorDTO periodMonitorDTO) {
		return new com.queen.infrastructure.persitence.monitor.PeriodMonitor(
				periodMonitorDTO.id(),
				periodMonitorDTO.monitorTypeId(),
				periodMonitorDTO.userId(),
				periodMonitorDTO.periodDate().toInstant(),
				periodMonitorDTO.painLevel(),
				periodMonitorDTO.flowLevel(),
				""
		);
	}
}
