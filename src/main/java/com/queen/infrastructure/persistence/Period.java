package com.queen.infrastructure.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.UUID;

@Table("periods")
public class Period implements Persistable<UUID> {
	@Id
	@Column("period_id")
	private UUID id;
	private final UUID userId;
	private final LocalDate startDate;
	private LocalDate endDate;
	private Integer cycleLength;
	private Boolean active;

	@Transient
	private final Boolean isNew;

	public Period(final UUID userId, final LocalDate startDate, Boolean isNew) {
		this.userId = userId;
		this.startDate = startDate;
		this.isNew = isNew;
	}

	@Override
	public boolean isNew() {
		return isNew;
	}

	public UUID getId() {
		return id;
	}

	public UUID getUserId() {
		return userId;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public Period setId(UUID id) {
		this.id = id;
		return this;
	}

	public Period setActive(Boolean active) {
		this.active = active;
		return this;
	}
}
