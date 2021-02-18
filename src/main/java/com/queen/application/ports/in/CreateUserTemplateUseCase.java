package com.queen.application.ports.in;

import com.queen.adapters.web.MonitorTypeDTO;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface CreateUserTemplateUseCase {
	void publishCreateUserTemplateEvent(final @NotNull List<MonitorTypeDTO> monitorTypeDTOs);
}
