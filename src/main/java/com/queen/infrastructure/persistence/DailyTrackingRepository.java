package com.queen.infrastructure.persistence;

import com.queen.infrastructure.auth.repository.TrackingWithPeriodAndMigraine;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface DailyTrackingRepository extends CrudRepository<DailyTracking, UUID> {
	@Modifying
	@Query(value = """
		UPDATE daily_tracking
		SET
			pain_level  = :#{#dailyTracking.painLevel},
			flow_level  = :#{#dailyTracking.flowLevel},
			migraine_id = :#{#dailyTracking.migraineId},
			period_id   = :#{#dailyTracking.periodId}
		WHERE
			tracking_id = :#{#dailyTracking.id}
		  AND user_id = :#{#dailyTracking.userId}
		  AND (:#{#dailyTracking.migraineId} IS NOT NULL OR :#{#dailyTracking.migraineId} IS NOT NULL)
	""")
	void updateDailyTracking(DailyTracking dailyTracking);

	@Query(value = """
		SELECT
			p.period_id   		 AS periodId,
			p.user_id     		 AS userId,
			p.date  		 	 AS date,
			p.cycle_length 		 AS cycleLength,
			dt.tracking_id 		 AS trackingId,
			dt.user_id        	 AS userId,
			dt.tracking_date     AS trackingDate,
			dt.pain_level	     AS painLevel,
			dt.flow_level		 AS flowLevel,
			m.migraine_id        AS migraineId,
			m.severity_level     AS migraineSeverity,
			m.migraine_date		 AS migraineDate
		FROM daily_tracking dt
		LEFT JOIN periods p ON p.period_id = dt.period_id AND p.user_id = dt.user_id
		LEFT JOIN migraines m ON dt.migraine_id = m.migraine_id AND m.user_id = dt.user_id
		WHERE dt.user_id = :userId
	""", resultSetExtractorClass = TrackingWithPeriodAndMigraine.class)
	List<Tracking> findAllByUserId(final UUID userId);

	@Modifying
	@Query(value = """
		DELETE FROM daily_tracking
		WHERE tracking_id IN (:trackingIds) AND user_id = :userId
	""")
	void deleteTracking(final Set<UUID> trackingIds, final UUID userId);
}
