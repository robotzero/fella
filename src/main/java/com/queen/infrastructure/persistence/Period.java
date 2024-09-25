package com.queen.infrastructure.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.UUID;

@Table("periods")
public class Period implements Persistable<String> {
	@Id
	@Column("period_id")
	private String id;
	private final UUID userId;
	private final LocalDate startDate;
	private LocalDate endDate;
	private Integer cycleLength;

	public Period(final UUID userId, final LocalDate startDate) {
		this.userId = userId;
		this.startDate = startDate;
	}

	@Override
	public boolean isNew() {
		return id == null || id.isEmpty();
	}

	public String getId() {
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
}
