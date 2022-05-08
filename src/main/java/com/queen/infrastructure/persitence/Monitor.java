package com.queen.infrastructure.persitence;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table("monitor")
public class Monitor implements Persistable<String> {
	@Id
	@Column("id")
	private final String id;

	@Column("name")
	private final String name;

	@Column("userId")
	private String userId;

	@Column("created_at")
	@CreatedDate
	private Instant createdDate = Instant.now();

	@Transient
	private boolean newMonitor;

	public Monitor(final String id, final String name) {
		this.id = id;
		this.name = name;
	}

	public Monitor(final String  id, final String name, final String userId) {
		this.id = id;
		this.name = name;
		this.userId = userId;
	}

	@Override
	@Transient
	public boolean isNew() {
		return this.newMonitor|| id == null || id.isEmpty();
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

	public Monitor setAsNew() {
		this.newMonitor = true;
		return this;
	}
}
