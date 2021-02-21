package com.queen.infrastructure.persitence;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;

public class FieldType implements Persistable<String> {
	@Id
	@Column("id")
	private final String id;

	@Column("name")
	private final String name;

	@Column("type")
	private final int type;

	@Transient
	private boolean newFieldType;

	public FieldType(final String id, final String name, final int type) {
		this.id = id;
		this.name = name;
		this.type = type;
	}

	@Override
	@Transient
	public boolean isNew() {
		return this.newFieldType || id == null || id.isEmpty();
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getType() {
		return type;
	}

	public FieldType setAsNew() {
		this.newFieldType = true;
		return this;
	}
}
