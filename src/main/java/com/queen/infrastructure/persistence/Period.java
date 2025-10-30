package com.queen.infrastructure.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Table(name = "periods")
public class Period implements Persistable<UUID> {
	@Id
	@Column("period_id")
	private UUID id;
	private UUID userId;
	private LocalDate date;

	@Transient
	private List<Migraine> migraine;

	@Transient
	private List<DailyTracking> dailyTracking;

	@Transient
	private Boolean isNew;

	public Period(final UUID id, final UUID userId, final LocalDate date) {
		this.id = id;
		this.userId = userId;
		this.date = date;
	}

	protected Period() {}

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

	public LocalDate getDate() {
		return date;
	}

	public Period setId(final UUID id) {
		this.id = id;
		return this;
	}

	public Period setNew(final Boolean aNew) {
		isNew = aNew;
		return this;
	}

	public List<Migraine> getMigraine() {
		return migraine;
	}

	public void setMigraine(final List<Migraine> migraine) {
		this.migraine = migraine;
	}

	public void setDailyTracking(final List<DailyTracking> dailyTracking) {
		this.dailyTracking = dailyTracking;
	}

	public Period setCycleLength(final Integer cycleLength) {
//		this.cycleLength = cycleLength;
		return this;
	}

	public List<DailyTracking> getDailyTracking() {
		return dailyTracking;
	}
}
