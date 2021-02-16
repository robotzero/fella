package com.queen.application.ports.out;

import com.queen.infrastructure.persitence.Monitor;
import reactor.core.publisher.Flux;

public interface LoadAllMonitorsPort {
	Flux<Monitor> loadAllMonitors();
}
