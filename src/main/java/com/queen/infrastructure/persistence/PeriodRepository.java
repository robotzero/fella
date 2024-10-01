package com.queen.infrastructure.persistence;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.UUID;

public interface PeriodRepository extends ReactiveCrudRepository<Period, UUID> {
	Mono<Period> findByUserId(UUID userId);
	@Modifying
	@Query("UPDATE periods SET active = false, end_date = :endDate WHERE period_id = :periodId AND active = true")
	Mono<Long> endActivePeriod(UUID periodId, LocalDate endDate);
	Flux<Period> findAllByUserId(UUID userId);
}
