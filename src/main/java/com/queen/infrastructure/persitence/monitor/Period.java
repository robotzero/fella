package com.queen.infrastructure.persitence.monitor;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table("periods")
public class Period implements Persistable<String> {
	@Id
	@Column("id")
	private final String id;

	@Column("periodMonitorId")
	private final String periodMoniorId;

	@Column("painLevel")
	private final Integer painLevel;

	@Column("notes")
	private String notes = "";

	@Column("created_at")
	private final Instant createdDate = Instant.now();

	@Transient
	private boolean newPeriod;

	public Period(final String id, final String periodMoniorId, final Integer painLevel) {
		this.id = id;
		this.periodMoniorId = periodMoniorId;
		this.painLevel = painLevel;
	}

	@Override
	@Transient
	public boolean isNew() {
		return this.newPeriod || id == null || id.isEmpty();
	}

	public String getId() {
		return id;
	}

	public Period setAsNew() {
		this.newPeriod = true;
		return this;
	}

	public Integer getPainLevel() {
		return painLevel;
	}

	public Instant getCreatedDate() {
		return createdDate;
	}

	public String getPeriodMoniorId() {
		return periodMoniorId;
	}
}
