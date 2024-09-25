package com.queen.infrastructure.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface PeriodRepository extends ReactiveCrudRepository<Period, String> {
	Mono<Period> findByUserId(UUID userId);
}
