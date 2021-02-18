package com.queen.application.service;

import com.queen.adapters.web.MonitorTypeDTO;
import com.queen.application.ports.in.CreateUserTemplateCommand;
import com.queen.application.ports.in.CreateUserTemplateDTO;
import com.queen.application.ports.in.CreateUserTemplateUseCase;
import org.springframework.context.ApplicationEventPublisher;

import javax.validation.constraints.NotNull;
import java.util.List;

public class StartUpTemplateService implements CreateUserTemplateUseCase {
	private final ApplicationEventPublisher applicationEventPublisher;

	public StartUpTemplateService(final ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}

	@Override
	public void publishCreateUserTemplateEvent(final @NotNull List<MonitorTypeDTO> monitorTypeDTOs) {
		this.applicationEventPublisher.publishEvent(new CreateUserTemplateCommand(new CreateUserTemplateDTO(monitorTypeDTOs)));
	}
}
