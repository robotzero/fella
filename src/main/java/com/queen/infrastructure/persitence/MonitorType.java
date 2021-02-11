package com.queen.infrastructure.persitence;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("monitor_type")
public class MonitorType {
	@Id
	@Column("id")
	public int id;

	@Column("name")
	public String name;
}
