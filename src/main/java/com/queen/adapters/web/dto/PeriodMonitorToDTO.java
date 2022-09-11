package com.queen.adapters.web.dto;

import com.queen.domain.monitor.PeriodMonitorResult;

import java.util.Date;
import java.util.UUID;

public class PeriodMonitorToDTO {
	final MonitorTypeToDTO monitorTypeToDTO;

	public PeriodMonitorToDTO(final MonitorTypeToDTO monitorTypeToDTO) {
		this.monitorTypeToDTO = monitorTypeToDTO;
	}
	public MonitorDTO toDTO(PeriodMonitorResult monitor) {
		return switch (monitor) {
			case PeriodMonitorResult.PeriodMonitor periodMonitor -> new MonitorDTO.PeriodDTO(periodMonitor.id(), null);
			default -> throw new IllegalStateException("Unexpected value: " + monitor);
		};
	}

	public com.queen.application.service.dto.MonitorDTO toServiceDTO(
			final PeriodMonitorRequest monitorRequest,
			final String monitorTypeId,
			final String userId
	) {
		final var periodMonitorId = UUID.randomUUID().toString();
		return switch (monitorRequest) {
			case PeriodMonitorRequest periodMonitorRequest -> new com.queen.application.service.dto.MonitorDTO.PeriodMonitorDTO(
					periodMonitorId,
					monitorTypeId,
					userId,
					new Date(),
					periodMonitorRequest.notes(),
					new com.queen.application.service.dto.MonitorDTO.PeriodDTO(
							UUID.randomUUID().toString(),
							periodMonitorId,
							periodMonitorRequest.painLevel(),
							new Date(),
							""
					)

			);
		};
	}
}
