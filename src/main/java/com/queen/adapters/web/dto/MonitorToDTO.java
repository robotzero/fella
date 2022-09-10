package com.queen.adapters.web.dto;

import com.queen.domain.monitor.MonitorResult;

import java.util.Date;
import java.util.UUID;

public class MonitorToDTO {
	final MonitorTypeToDTO monitorTypeToDTO;

	public MonitorToDTO(final MonitorTypeToDTO monitorTypeToDTO) {
		this.monitorTypeToDTO = monitorTypeToDTO;
	}
	public MonitorDTO toDTO(MonitorResult monitor) {
		return switch (monitor) {
			case MonitorResult.PeriodMonitor periodMonitor -> new MonitorDTO.PeriodDTO(periodMonitor.id(), null);
			default -> throw new IllegalStateException("Unexpected value: " + monitor);
		};
	}

	public com.queen.application.service.dto.MonitorDTO toServiceDTO(
			final MonitorRequest monitorRequest,
			final String monitorTypeId,
			final String userId
	) {
		final var periodMonitorId = UUID.randomUUID().toString();
		return switch (monitorRequest) {
			case MonitorRequest.PeriodMonitorRequest periodMonitorRequest -> new com.queen.application.service.dto.MonitorDTO.PeriodMonitorDTO(
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
			case MonitorRequest.MigraneRequest migraneRequest -> throw new IllegalStateException("Not implemented yet");
			case MonitorRequest.StomachRequest stomachRequest -> throw new IllegalStateException("Not implemented yet");
		};
	}
}
