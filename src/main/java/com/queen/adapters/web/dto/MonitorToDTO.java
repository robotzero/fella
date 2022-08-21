package com.queen.adapters.web.dto;

import com.queen.domain.monitor.MonitorResult;

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
		return new com.queen.application.service.dto.MonitorDTO(
				monitorTypeId,
				monitorRequest.name(),
				userId
		);
	}
}
