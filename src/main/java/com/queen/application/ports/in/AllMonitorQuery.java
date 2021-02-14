package com.queen.application.ports.in;

import com.queen.domain.monitor.Monitor;
import reactor.core.publisher.Flux;

public interface AllMonitorQuery {
	Flux<Monitor> loadAllMonitors();
}
