package com.queen.application.ports.out;

import com.queen.infrastructure.persitence.Monitor;
import com.queen.infrastructure.persitence.monitor.Period;
import com.queen.infrastructure.persitence.monitor.PeriodMonitor;
import com.queen.infrastructure.persitence.monitor.StomachMonitor;
import com.queen.infrastructure.persitence.monitor.TabletsTakenMonitor;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;

public interface LoadMonitorsPort {
	Flux<Monitor> loadMonitors(final String monitorTypeId, final String userId, final Pageable pageable);
	Flux<PeriodMonitor> loadPeriodMonitors(final String monitorTypeId, final String userId, final Pageable pageable);
	Flux<Period> loadPeriods(final String periodMonitorId);
	Flux<StomachMonitor> loadStomachMonitor(final String monitorTypeId, final String userId, final Pageable pageable);
	Flux<TabletsTakenMonitor> loadTabletsTakenMonitor(final String monitorTypeId, final String userId, final Pageable pageable);
}
