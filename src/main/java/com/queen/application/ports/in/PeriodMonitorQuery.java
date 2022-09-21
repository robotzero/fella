package com.queen.application.ports.in;

import com.queen.domain.monitor.PeriodMonitor;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.util.Date;

public interface PeriodMonitorQuery {
	Flux<PeriodMonitor> loadPeriodMonitors(final String monitorTypeId, final String userId, Pageable pageable);
	Mono<PeriodMonitor> loadPeriodMonitorByDate(final String monitorTypeId, final String userId, final String dateToSearchBy) throws ParseException;
}
