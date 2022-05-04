package com.queen.infrastructure.persitence;

import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Flux;

import java.util.List;

public interface MonitorTypeRepository extends ReactiveSortingRepository<MonitorType, String> {
	@Query("SELECT mt.* FROM monitor_type mt INNER JOIN fields f ON f.monitorTypeId = mt.id INNER JOIN field_type ft ON ft.id = f.fieldTypeId WHERE mt.userId = :userId GROUP BY mt.id ORDER BY mt.created_at LIMIT :#{#pageable.getPageSize()} OFFSET :#{#pageable.getOffset()}")
	Flux<MonitorType> findByUserId(String userId, Pageable pageable);

	@Query("SELECT mt.* FROM monitor_type mt INNER JOIN fields f ON f.monitorTypeId = mt.id INNER JOIN field_type ft ON ft.id = f.fieldTypeId WHERE mt.userId = :userId AND mt.name IN (:names)")
	Flux<MonitorType> findByUserIdAndNames(List<String> names, String userId);
}
