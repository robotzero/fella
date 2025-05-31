package com.queen.infrastructure.persistence;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

// @TODO transactional
public interface PeriodRepository extends CrudRepository<Period, UUID> {
	@Modifying
	@Query(nativeQuery = true, value = """
		UPDATE periods
		SET active = false, end_date = :endDate
		WHERE period_id = :periodId
		AND active = true
	""")
	int endActivePeriod(UUID periodId, LocalDate endDate);
	//@TODO name the fields specifically as
	@Query(nativeQuery = true, value = """
		SELECT p.*, m.*, dt.*
		FROM periods p
		INNER JOIN daily_tracking dt ON p.period_id = dt.period_id
		LEFT JOIN migraines m ON dt.migraine_id = m.migraine_id
		WHERE p.user_id = :userId
	""")
	List<Period> findAllByUserId(UUID userId);
	@Query(nativeQuery = true, value = """
		SELECT p.*, m.*, dt.*
		FROM periods p
		INNER JOIN daily_tracking dt ON p.period_id = dt.period_id
		LEFT JOIN migraines m ON dt.migraine_id = m.migraine_id
		WHERE p.period_id = :periodId
	""")
	Period findByIdAndByUserId(UUID periodId);
}
