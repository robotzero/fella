package com.queen.infrastructure.persistence;

import org.springframework.data.annotation.Id;

import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.UUID;

@Table("daily_tracking")
public class Tracking implements Persistable<UUID> {
	@Id
	@Column("tracking_id")
	private UUID id;
	private UUID userId;
	private LocalDate trackingDate;
	private Integer painLevel;
	private Integer flowLevel;
	private Migraine migraine;
	private Period period;

	@Transient
	private Boolean isNew;

	public Tracking(UUID userId, LocalDate trackingDate, Boolean isNew) {
		this.userId = userId;
		this.trackingDate = trackingDate;
		this.isNew = isNew;
	}

	protected Tracking() {}

	public static Tracking empty() {
		return new Tracking(null, null, true);
	}

	@Override
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	@Override
	public boolean isNew() {
		return isNew;
	}

	public Integer getPainLevel() {
		return painLevel;
	}

	public Tracking setPainLevel(Integer painLevel) {
		this.painLevel = painLevel;
		return this;
	}

	public Integer getFlowLevel() {
		return flowLevel;
	}

	public Tracking setFlowLevel(Integer flowLevel) {
		this.flowLevel = flowLevel;
		return this;
	}

	public LocalDate getTrackingDate() {
		return trackingDate;
	}

	public Migraine getMigraine() {
		return migraine;
	}

	public void setMigraine(Migraine migraine) {
		this.migraine = migraine;
	}

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}
}

