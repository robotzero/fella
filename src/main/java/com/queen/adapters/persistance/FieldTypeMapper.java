package com.queen.adapters.persistance;

import com.queen.domain.fieldtype.FieldType;
import com.queen.domain.fieldtype.FieldTypeValue;

import javax.validation.constraints.NotNull;

public class FieldTypeMapper {
	public FieldType toDomain(final @NotNull com.queen.infrastructure.persitence.FieldType fieldType) {
		return new FieldType(fieldType.getId(), fieldType.getName(), FieldTypeValue.getByType(fieldType.getType()));
	}
}
