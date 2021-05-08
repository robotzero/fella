package com.queen.application.ports.in;

import com.queen.adapters.web.MonitorTypeDTO;

import java.util.List;

public interface CreateUserTemplateEvent {
	void publishCreateUserTemplateEvent(final List<MonitorTypeDTO> monitorTypeDTOs);
}
