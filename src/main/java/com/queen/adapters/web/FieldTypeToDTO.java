package com.queen.adapters.web;

import com.queen.domain.fieldtype.FieldType;

public class FieldTypeToDTO {
	public FieldTypeDTO toDTO(FieldType fieldType) {
		return new FieldTypeDTO(fieldType.name(), fieldType.fieldTypeValue());
	}
}
