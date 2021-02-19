package com.queen.infrastructure.persitence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface MonitorTypeRepository extends ReactiveCrudRepository<com.queen.infrastructure.persitence.MonitorType, Integer> {
	Flux<MonitorType> findByUserId(String userId);
}
