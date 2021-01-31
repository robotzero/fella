package com.queen.infrastructure;

import com.queen.controller.Period;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public interface PeriodsRepository extends ReactiveCrudRepository<Period, Long> {
	@Query("SELECT idperiod as id, iduser as userId from periods WHERE idperiod = 1")
	Mono<Period> findByCustomId();
}
