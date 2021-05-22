package com.queen.application.ports.in;

import com.queen.adapters.web.dto.MonitorTypeDTO;

import java.util.List;

public class CreateUserTemplateDTO {
	private final List<MonitorTypeDTO> monitorTypeDTOs;

	public CreateUserTemplateDTO(final List<MonitorTypeDTO> monitorTypeDTOs) {
		this.monitorTypeDTOs = monitorTypeDTOs;
	}

	public List<MonitorTypeDTO> getMonitorTypeDTOs() {
		return this.monitorTypeDTOs;
	}
}
