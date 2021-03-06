package com.queen.infrastructure.persitence;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table("monitor_type")
public class MonitorType implements Persistable<String> {
	@Id
	@Column("id")
	private final String id;

	@Column("name")
	private final String name;

	@Column("userId")
	@CreatedBy
	private String userId;

	@Column("created_at")
	@CreatedDate
	private Instant createdDate;

	@Transient
	private boolean newMonitorType;

	public MonitorType(final String id, final String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	@Transient
	public boolean isNew() {
		return this.newMonitorType || id == null || id.isEmpty();
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getUserId() {
		return userId;
	}

	public MonitorType setAsNew() {
		this.newMonitorType = true;
		return this;
	}
}
