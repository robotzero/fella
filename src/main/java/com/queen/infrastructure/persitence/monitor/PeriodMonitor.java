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

	@Column("monitorTypeId")
	private String monitorTypeId;

	@Column("created_at")
	@CreatedDate
	private Instant createdDate = Instant.now();

	@Column("startdate")
	private Instant startDate;

	@Column("notes")
	private final String notes = "";

	@Transient
	private boolean newPeriodMonitor;

	public PeriodMonitor(final String id, String monitorTypeId, final String userId, final Instant startDate) {
		this.id = id;
		this.monitorTypeId = monitorTypeId;
		this.userId = userId;
		this.startDate = startDate;
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

	public String getNotes() {
		return notes;
	}
}
