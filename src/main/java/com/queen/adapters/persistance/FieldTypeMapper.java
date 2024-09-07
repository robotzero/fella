package com.queen.adapters.persistance;

import com.queen.domain.fieldtype.FieldType;
import com.queen.domain.fieldtype.FieldTypeValue;

public class FieldTypeMapper {
	public FieldType toDomain(final com.queen.infrastructure.persistence.FieldType fieldType) {
		return new FieldType(fieldType.getId(), fieldType.getName(), FieldTypeValue.getByType(fieldType.getType()));
	}

	public FieldType toDomain(final com.queen.domain.fields.Fields fields) {
		return new FieldType(
				"",
				FieldTypeValue.getByType(Integer.parseInt(fields.fieldTypeId())).name(),
				FieldTypeValue.getByType(Integer.parseInt(fields.fieldTypeId()))
		);
	}
}
