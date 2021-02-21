package com.queen.infrastructure.persitence;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface MonitorTypeRepository extends ReactiveCrudRepository<com.queen.infrastructure.persitence.MonitorType, Integer> {
	@Query("SELECT mt.* FROM monitor_type mt INNER JOIN fields f ON f.monitorTypeId = mt.id INNER JOIN field_type ft ON ft.id = f.fieldTypeId WHERE mt.userId = :userId GROUP BY mt.id")
	Flux<MonitorType> findByUserId(String userId);
}
