package com.queen.adapters.web.dto;

import com.queen.domain.fieldtype.FieldType;
import com.queen.domain.fieldtype.FieldTypeValue;

import java.util.UUID;

public class FieldTypeToDTO {
	public FieldTypeDTO toDTO(final FieldType fieldType) {
		return new FieldTypeDTO(fieldType.name(), fieldType.fieldTypeValue());
	}

	public com.queen.application.service.dto.FieldsDTO toServiceDTO(final String monitorId, final Integer fieldTypeValue) {
		return new com.queen.application.service.dto.FieldsDTO(UUID.randomUUID().toString(), monitorId, String.valueOf(FieldTypeValue.getByType(fieldTypeValue).getType()));
	}
}
