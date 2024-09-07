package com.queen.infrastructure.persistence;

import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface MonitorRepository extends ReactiveCrudRepository<Monitor, String> {
	@Query("SELECT m.* FROM monitor m INNER JOIN monitor_type mt ON m.id = mt.id WHERE m.userId = :userId GROUP BY m.id ORDER BY m.created_at LIMIT :#{#pageable.getPageSize()} OFFSET :#{#pageable.getOffset()}")
	Flux<Monitor> findMonitorsByIdAndUserId(final String monitorTypeId, final String userId, Pageable pageable);
}
