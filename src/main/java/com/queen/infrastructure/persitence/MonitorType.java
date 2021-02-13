package com.queen.infrastructure.persitence;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("monitor_type")
public class MonitorType {
	@Id
	@Column("id")
	private final int id;

	@Column("name")
	private final String name;

	public MonitorType(final int id, final String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
