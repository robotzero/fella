package com.queen.infrastructure.persitence.monitor;

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

	@Column("user_id")
	private String userId;

	@Column("monitor_type_id")
	private String monitorTypeId;

	@Column("created_at")
	private Instant createdDate = Instant.now();

	@Column("period_date")
	private Instant periodDate;

	@Column("pain_level")
	private Integer painLevel;

	@Column("flow_level")
	private Integer flowLevel;

	@Column("notes")
	private String notes = "";

	@Transient
	private boolean newPeriodMonitor;

	public PeriodMonitor(final String id, String monitorTypeId, final String userId, final Instant periodDate, final Integer painLevel, final Integer flowLevel, final String notes) {
		this.id = id;
		this.monitorTypeId = monitorTypeId;
		this.userId = userId;
		this.periodDate = periodDate;
		this.painLevel = painLevel;
		this.flowLevel = flowLevel;
		this.notes = notes;
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

	public Instant getPeriodDate() {
		return periodDate;
	}

	public String getNotes() {
		return notes;
	}

	public Integer getPainLevel() {
		return painLevel;
	}

	public Integer getFlowLevel() {
		return flowLevel;
	}
}
