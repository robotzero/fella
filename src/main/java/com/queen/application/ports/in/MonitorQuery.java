package com.queen.application.ports.in;

import com.queen.domain.monitor.Monitor;
import com.queen.domain.monitor.MonitorResult;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;

public interface MonitorQuery {
	Flux<Monitor> loadMonitors(final String monitorTypeId, final String userId, Pageable pageable);
	Flux<MonitorResult> loadCustomMonitors(final String monitorTypeId, final String userId, Pageable pageable);
}
