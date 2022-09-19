package com.queen.infrastructure.persitence.monitor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface PeriodMonitorRepository extends ReactiveCrudRepository<PeriodMonitor, String> {
	@Query("SELECT pm.* FROM period_monitor pm INNER JOIN monitor_type mt ON pm.monitor_type_id = mt.id WHERE pm.user_id = :userId AND pm.monitor_type_id = :monitorTypeId GROUP BY pm.id ORDER BY pm.created_at LIMIT :#{#pageable.getPageSize()} OFFSET :#{#pageable.getOffset()}")
	Flux<PeriodMonitor> findPeriodMonitorsByIdAndUserId(final String monitorTypeId, final String userId, Pageable pageable);
}
