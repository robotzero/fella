package com.queen.infrastructure.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface DailyTrackingRepository extends ReactiveCrudRepository<DailyTracking, UUID> {
}
