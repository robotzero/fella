package com.queen.application.ports.in;

import javax.validation.constraints.NotNull;

public interface CreateMonitorTypeUseCase {
	void createManyMonitorTypes(final @NotNull CreateMonitorTypeCommand createMonitorTypeCommand);
}
