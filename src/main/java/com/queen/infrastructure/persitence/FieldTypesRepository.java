package com.queen.infrastructure.persitence;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface FieldTypesRepository extends ReactiveCrudRepository<FieldType, String> {
	@Query("SELECT ft.* FROM field_type ft INNER JOIN fields f ON f.monitorTypeId = :monitorTypeId INNER JOIN monitor_type mt ON f.monitorTypeId = mt.id WHERE f.fieldTypeId = ft.id GROUP BY ft.id, mt.id")
	Flux<FieldType> findByMonitorTypeId(String monitorTypeId);
}
