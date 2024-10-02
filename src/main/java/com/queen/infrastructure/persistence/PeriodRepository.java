package com.queen.infrastructure.persistence;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.UUID;

public interface PeriodRepository extends ReactiveCrudRepository<Period, UUID> {
	@Modifying
	@Query("""
		UPDATE periods
		SET active = false, end_date = :endDate
		WHERE period_id = :periodId
		AND active = true
	""")
	Mono<Long> endActivePeriod(UUID periodId, LocalDate endDate);
	//@TODO name the fields specifically as
	@Query("""
		SELECT p.*, m.*, dt.*
		FROM periods p
		INNER JOIN daily_tracking dt ON p.period_id = dt.period_id
		LEFT JOIN migraines m ON dt.migraine_id = m.migraine_id
		WHERE p.user_id = :userId
	""")
	Flux<Period> findAllByUserId(UUID userId);
}
