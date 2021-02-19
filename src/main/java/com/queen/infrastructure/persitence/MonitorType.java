package com.queen.infrastructure.persitence;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("monitor_type")
public class MonitorType implements Persistable<String> {
	@Id
	@Column("id")
	private final String id;

	@Column("name")
	private final String name;

	@Column("userId")
	private final String userId;

	@Transient
	private boolean newMonitorType;

	public MonitorType(final String id, final String name, final String userId) {
		this.id = id;
		this.name = name;
		this.userId = userId;
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
