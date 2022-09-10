package com.queen.infrastructure.persitence.monitor;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface PeriodRepository extends ReactiveCrudRepository<Period, String> {
	@Query("SELECT ps.* FROM periods ps INNER JOIN period_monitor pm ON ps.periodMonitorId = pm.id WHERE ps.periodMonitorId = :periodMonitorId GROUP BY ps.id ORDER BY ps.created_at")
	Flux<Period> findPeriodsByPeriodMonitorId(final String periodMonitorId);
}
