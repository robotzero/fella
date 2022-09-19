package com.queen.adapters.web.dto;

import com.queen.domain.monitor.PeriodMonitor;

import java.util.UUID;

public class PeriodMonitorToDTO {
	final MonitorTypeToDTO monitorTypeToDTO;

	public PeriodMonitorToDTO(final MonitorTypeToDTO monitorTypeToDTO) {
		this.monitorTypeToDTO = monitorTypeToDTO;
	}
	public MonitorDTO toDTO(final PeriodMonitor periodMonitor) {
		return new MonitorDTO.PeriodDTO(periodMonitor.id(), null);
	}

	public com.queen.application.service.dto.PeriodMonitorDTO toServiceDTO(
			final PeriodMonitorRequest periodMonitorRequest,
			final String monitorTypeId,
			final String userId
	) {
		final var periodMonitorId = UUID.randomUUID().toString();
		return new com.queen.application.service.dto.PeriodMonitorDTO(
				periodMonitorId,
				monitorTypeId,
				userId,
				periodMonitorRequest.periodDate(),
				periodMonitorRequest.painLevel(),
				periodMonitorRequest.flowLevel(),
				periodMonitorRequest.notes()
		);
	}
}
