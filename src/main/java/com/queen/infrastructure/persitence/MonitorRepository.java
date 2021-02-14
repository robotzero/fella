package com.queen.infrastructure.persitence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface MonitorRepository extends ReactiveCrudRepository<Monitor, Integer> {

}
