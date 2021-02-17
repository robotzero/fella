package com.queen.application.ports.out;

import com.queen.infrastructure.persitence.MonitorType;

public interface CreateMonitorTypePort {
	void createMonitorType(final MonitorType monitorType);
}
