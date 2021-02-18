package com.queen.application.ports.in;

import org.springframework.context.ApplicationEvent;

public class CreateUserTemplateCommand extends ApplicationEvent {
	public CreateUserTemplateDTO getCreateUserTemplateDTO() {
		return createUserTemplateDTO;
	}

	private final CreateUserTemplateDTO createUserTemplateDTO;
	/**
	 * Create a new {@code ApplicationEvent}.
	 *
	 * @param createUserTemplateDTO the object on which the event initially occurred or with
	 *               which the event is associated (never {@code null})
	 */
	public CreateUserTemplateCommand(final CreateUserTemplateDTO createUserTemplateDTO) {
		super(createUserTemplateDTO);
		this.createUserTemplateDTO = createUserTemplateDTO;
	}
}
