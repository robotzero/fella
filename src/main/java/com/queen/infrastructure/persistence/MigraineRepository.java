package com.queen.infrastructure.persistence;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface MigraineRepository extends CrudRepository<Migraine, UUID> {
	@Modifying
	@Query(value = """
		UPDATE migraines
		SET severity_level = :#{#migraine.severityLevel},
		    description = :#{#migraine.description},
		    migraine_date = :#{#migraine.migraineDate}
		WHERE migraine_id = :#{#migraine.id}
	""")
	void updateMigraine(final Migraine migraine);
}
