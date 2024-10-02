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
	private Migraine migraine;

	@Transient
	private DailyTracking dailyTracking;

	@Transient
	private Boolean isNew;

	public Period(final UUID userId, final LocalDate startDate) {
		this.userId = userId;
		this.startDate = startDate;
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

	public Period setId(final UUID id) {
		this.id = id;
		return this;
	}

	public Period setActive(final Boolean active) {
		this.active = active;
		return this;
	}

	public Period setEndDate(final LocalDate endDate) {
		this.endDate = endDate;
		return this;
	}

	public Period setNew(final Boolean aNew) {
		isNew = aNew;
		return this;
	}

	public Boolean getActive() {
		return active;
	}

	public Migraine getMigraine() {
		return migraine;
	}

	public void setMigraine(final Migraine migraine) {
		this.migraine = migraine;
	}

	public void setDailyTracking(final DailyTracking dailyTracking) {
		this.dailyTracking = dailyTracking;
	}

	public Period setCycleLength(final Integer cycleLength) {
		this.cycleLength = cycleLength;
		return this;
	}

	public DailyTracking getDailyTracking() {
		return dailyTracking;
	}
}
