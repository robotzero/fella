package com.queen.infrastructure.persitence;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("monitor")
public class Monitor {
	@Id
	@Column("id")
	private final int id;

	@Column("name")
	private final String name;

	@Column("userId")
	private final int userId;

	public Monitor(final int id, final String name, final int userId) {
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

	public int getUserId() {
		return userId;
	}
}
