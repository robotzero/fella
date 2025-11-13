package com.queen.infrastructure.persistence;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface DailyTrackingRepository extends CrudRepository<DailyTracking, UUID> {
	@Modifying
	@Query(value = """
		UPDATE daily_tracking
		SET
			pain_level = :#{#dailyTracking.painLevel},
			flow_level = :#{#dailyTracking.flowLevel},
			migraine_id = :#{#dailyTracking.migraineId}
		WHERE
			tracking_id = :#{#dailyTracking.id}
	""")
	void updateDailyTracking(DailyTracking dailyTracking);
}
