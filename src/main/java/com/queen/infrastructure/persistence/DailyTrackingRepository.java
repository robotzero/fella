package com.queen.infrastructure.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface DailyTrackingRepository extends CrudRepository<DailyTracking, UUID> {
}
