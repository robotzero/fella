package com.queen.infrastructure.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.UUID;

@Table("migraines")
public class Migraine implements Persistable<UUID> {
	@Id
	@Column("migraine_id")
	private UUID id;
	private final UUID userId;
	private final LocalDate migraineDate;
	private Integer severityLevel;
	private String description;

	@Transient
	private final Boolean isNew;

	public Migraine(final UUID userId, final LocalDate migraineDate, Boolean isNew) {
		this.userId = userId;
		this.migraineDate = migraineDate;
		this.isNew = isNew;
	}

	public static Migraine empty() {
		return new Migraine(null, null, false);
	}

	@Override
	public UUID getId() {
		return id;
	}

	@Override
	public boolean isNew() {
		return isNew;
	}

	public Integer getSeverityLevel() {
		return severityLevel;
	}

	public Migraine setSeverityLevel(Integer severityLevel) {
		this.severityLevel = severityLevel;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public Migraine setDescription(String description) {
		this.description = description;
		return this;
	}

	public LocalDate getMigraineDate() {
		return migraineDate;
	}
}
