package com.queen.application.ports.in;

import com.queen.adapters.web.dto.MonitorTypeDTO;

import java.util.List;

public interface CreateUserTemplateEvent {
	void publishCreateUserTemplateEvent(final List<MonitorTypeDTO> monitorTypeDTOs);
}
