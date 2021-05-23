package com.queen.infrastructure.persitence;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table("monitor")
public class Monitor {
	@Id
	@Column("id")
	private final int id;

	@Column("name")
	private final String name;

	@Column("userId")
	@CreatedBy
	private String userId;

	@Column("created_at")
	@CreatedDate
	private Instant createdDate;

	public Monitor(final int id, final String name) {
		this.id = id;
		this.name = name;
	}

	public Monitor(final int id, final String name, final String userId) {
		this.id = id;
		this.name = name;
		this.userId = userId;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getUserId() {
		return userId;
	}
}
