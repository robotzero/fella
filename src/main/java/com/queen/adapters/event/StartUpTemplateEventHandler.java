package com.queen.adapters.event;

import com.queen.application.ports.in.CreateMonitorTypeCommand;
import com.queen.application.ports.in.CreateUserTemplateCommand;
import com.queen.application.service.MonitorTypeService;
import org.springframework.context.ApplicationListener;

public class StartUpTemplateEventHandler implements ApplicationListener<CreateUserTemplateCommand> {
	private final MonitorTypeService monitorTypeService;

	public StartUpTemplateEventHandler(MonitorTypeService monitorTypeService) {
		this.monitorTypeService = monitorTypeService;
	}

	@Override
	public void onApplicationEvent(final CreateUserTemplateCommand createUserTemplateCommand) {
		monitorTypeService.createManyMonitorTypes(new CreateMonitorTypeCommand(createUserTemplateCommand.getCreateUserTemplateDTO().getMonitorTypeDTOs()));
	}
}
