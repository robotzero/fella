package com.queen.infrastructure.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.UUID;

@Table("daily_tracking")
public class DailyTracking implements Persistable<UUID> {
	@Id
	@Column("tracking_id")
	private UUID id;
	private UUID userId;
	private final LocalDate trackingDate;
	private UUID periodId;
	private UUID moodId;
	private UUID migraineId;
	private Integer painLevel;
	private Integer flowLevel;

	@Transient
	private final Boolean isNew;

	public DailyTracking(UUID userId, LocalDate trackingDate, Boolean isNew) {
		this.userId = userId;
		this.trackingDate = trackingDate;
		this.isNew = isNew;
	}

	public static DailyTracking empty() {
		return new DailyTracking(null, null, true);
	}

	@Override
	public UUID getId() {
		return id;
	}

	@Override
	public boolean isNew() {
		return isNew;
	}

	public Integer getPainLevel() {
		return painLevel;
	}

	public DailyTracking setPainLevel(Integer painLevel) {
		this.painLevel = painLevel;
		return this;
	}

	public Integer getFlowLevel() {
		return flowLevel;
	}

	public DailyTracking setFlowLevel(Integer flowLevel) {
		this.flowLevel = flowLevel;
		return this;
	}

	public UUID getMoodId() {
		return moodId;
	}

	public DailyTracking setMoodId(UUID moodId) {
		this.moodId = moodId;
		return this;
	}

	public UUID getPeriodId() {
		return periodId;
	}

	public DailyTracking setPeriodId(UUID periodId) {
		this.periodId = periodId;
		return this;
	}

	public LocalDate getTrackingDate() {
		return trackingDate;
	}

	public UUID getMigraineId() {
		return migraineId;
	}

	public void setMigraineId(UUID migraineId) {
		this.migraineId = migraineId;
	}
}
