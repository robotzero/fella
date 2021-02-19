package com.queen.application.ports.out;

import com.queen.infrastructure.persitence.MonitorType;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface CreateManyMonitorTypesPort {
	void createMonitorTypes(final @NotNull List<MonitorType> monitorType);
}
