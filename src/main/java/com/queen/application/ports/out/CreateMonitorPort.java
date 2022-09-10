package com.queen.application.ports.out;

import com.queen.infrastructure.persitence.monitor.Period;
import com.queen.infrastructure.persitence.monitor.PeriodMonitor;
import reactor.core.publisher.Mono;

public interface CreateMonitorPort {
	Mono<PeriodMonitor> createPeriodMonitor(final PeriodMonitor monitor);
	Mono<Period> createPeriod(final Period period);
}
