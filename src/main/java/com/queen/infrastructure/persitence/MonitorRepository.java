package com.queen.infrastructure.persitence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface MonitorRepository extends ReactiveCrudRepository<Monitor, Integer> {
	Flux<Monitor> findMonitorsByUserId(int userId);
}
