package com.queen.adapters.web.dto;

import com.queen.domain.monitor.Monitor;

public class MonitorToDTO {
	final MonitorTypeToDTO monitorTypeToDTO;

	public MonitorToDTO(final MonitorTypeToDTO monitorTypeToDTO) {
		this.monitorTypeToDTO = monitorTypeToDTO;
	}
	public MonitorDTO toDTO(Monitor monitor) {
		return new MonitorDTO(monitor.id(), monitor.name(), monitorTypeToDTO.toDTO(monitor.monitorType()));
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
