package com.queen.infrastructure.persitence.monitor;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
@Table("period_monitor")
public class PeriodMonitor implements Persistable<String> {
	@Id
	@Column("id")
	private final String id;

	@Column("userId")
	private String userId;

	@Column("created_at")
	@CreatedDate
	private Instant createdDate = Instant.now();

	@Column("startdate")
	private Instant startDate;

	@Column("painlevel")
	private final Integer painLevel;

	@Column("notes")
	private final String notes = "";

	@Transient
	private boolean newPeriodMonitor;

	public PeriodMonitor(final String id, final String userId, final Instant startDate, final Integer painLevel) {
		this.id = id;
		this.userId = userId;
		this.startDate = startDate;
		this.painLevel = painLevel;
	}

	@Override
	@Transient
	public boolean isNew() {
		return this.newPeriodMonitor|| id == null || id.isEmpty();
	}

	public String getId() {
		return id;
	}

	public String getUserId() {
		return userId;
	}

	public PeriodMonitor setAsNew() {
		this.newPeriodMonitor = true;
		return this;
	}

	public Instant getStartDate() {
		return startDate;
	}

	public Integer getPainLevel() {
		return painLevel;
	}

	public String getNotes() {
		return notes;
	}
}
