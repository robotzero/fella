package com.queen.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.springframework.data.annotation.Id;
import jakarta.persistence.Transient;
import org.springframework.data.domain.Persistable;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "migraines")
public class Migraine implements Persistable<UUID> {
	@jakarta.persistence.Id
	@Id
	@Column(name = "migraine_id")
	private UUID id;
	private UUID userId;
	private LocalDate migraineDate;
	private Integer severityLevel;
	private String description;

	@Transient
	private Boolean isNew;

	public Migraine(final UUID userId, final LocalDate migraineDate, Boolean isNew) {
		this.userId = userId;
		this.migraineDate = migraineDate;
		this.isNew = isNew;
	}

	protected Migraine() {}

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

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getUserId() {
		return userId;
	}
}
