package com.queen.infrastructure.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("fields")
public class Fields implements Persistable<String> {
	@Id
	@Column("id")
	private final String id;

	@Column("monitorTypeId")
	private final String monitorTypeId;

	@Column("fieldTypeId")
	private final String fieldTypeId;

	@Transient
	private boolean newField;

	public Fields(final String id, final String monitorTypeId, final String fieldTypeId) {
		this.id = id;
		this.monitorTypeId = monitorTypeId;
		this.fieldTypeId = fieldTypeId;
	}

	@Override
	@Transient
	public boolean isNew() {
		return this.newField || id == null || id.isEmpty();
	}

	public String getId() {
		return id;
	}

	public Fields setAsNew() {
		this.newField = true;
		return this;
	}

	public String getMonitorTypeId() {
		return monitorTypeId;
	}

	public String getFieldTypeId() {
		return fieldTypeId;
	}
}
