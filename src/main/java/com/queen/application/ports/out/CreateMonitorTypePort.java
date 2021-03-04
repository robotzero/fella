package com.queen.application.ports.out;

import com.queen.infrastructure.persitence.MonitorType;

import javax.validation.constraints.NotNull;

public interface CreateMonitorTypePort {
	void createMonitorType(final @NotNull MonitorType monitorType);
}