package com.queen.infrastructure.persistence;

import com.queen.infrastructure.auth.repository.PeriodsWithDailyMigraineExtractor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

// @TODO transactional
public interface PeriodRepository extends CrudRepository<@NotNull Period, @NotNull UUID> {
	@Query(value = """
		SELECT
			p.period_id   		 AS periodId,
			p.user_id     		 AS userId,
			p.date  		 	 AS date,
			p.cycle_length 		 AS cycleLength,
			dt.tracking_id 		 AS trackingId,
			dt.tracking_date     AS trackingDate,
			dt.pain_level	     AS painLevel,
			dt.flow_level		 AS flowLevel,
			m.migraine_id        AS migraineId,
			m.severity_level     AS migraineSeverity,
			m.migraine_date		 AS migraineDate
		FROM periods p
		JOIN daily_tracking dt ON p.period_id = dt.period_id
		LEFT JOIN migraines m ON dt.migraine_id = m.migraine_id
		WHERE p.user_id = :userId
	""", resultSetExtractorClass = PeriodsWithDailyMigraineExtractor.class)
	List<Period> findAllByUserId(final UUID userId);

	@Query(value = """
		SELECT p.*, m.* AS migraine, dt.* AS dailyTracking
		FROM periods p
		INNER JOIN daily_tracking dt ON p.period_id = dt.period_id
		LEFT JOIN migraines m ON dt.migraine_id = m.migraine_id
		WHERE p.period_id = :periodId
	""")
	Period findByIdAndByUserId(final UUID periodId);

	@Modifying
	@Query(value = """
		DELETE FROM periods
		WHERE period_id IN (:periodIds) AND user_id = :userId
	""")
	void deletePeriods(final Set<UUID> periodIds, final UUID userId);
}
