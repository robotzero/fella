package com.queen.adapters.event;

import com.queen.application.ports.in.CreateUserTemplateCommand;
import org.springframework.context.ApplicationListener;

public class GenericEventHandler implements ApplicationListener<CreateUserTemplateCommand> {
	@Override
	public void onApplicationEvent(final CreateUserTemplateCommand createUserTemplateCommand) {
		System.out.println(createUserTemplateCommand.getCreateUserTemplateDTO());
		System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
	}
}
