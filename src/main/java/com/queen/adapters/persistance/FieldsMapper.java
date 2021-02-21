package com.queen.adapters.persistance;

import com.queen.adapters.web.FieldsDTO;
import com.queen.domain.fields.Fields;

import javax.validation.constraints.NotNull;

public class FieldsMapper {
	public FieldsDTO toDTO(@NotNull final Fields fields) {
		return new FieldsDTO(fields.id(), fields.monitorTypeId(), fields.fieldTypeId());
	}

	public com.queen.infrastructure.persitence.Fields mapToPersistence(@NotNull final FieldsDTO fieldsDTO) {
		return new com.queen.infrastructure.persitence.Fields(fieldsDTO.id(), fieldsDTO.monitorTypeId(), fieldsDTO.fieldTypeId());
	}
}
