package com.queen.adapters.persistance;

import com.queen.adapters.web.dto.FieldsDTO;
import com.queen.domain.fields.Fields;

public class FieldsMapper {
	public FieldsDTO toDTO(final Fields fields) {
		return new FieldsDTO(fields.id(), fields.monitorTypeId(), fields.fieldTypeId());
	}

	public com.queen.infrastructure.persistence.Fields mapToPersistence(final FieldsDTO fieldsDTO) {
		return new com.queen.infrastructure.persistence.Fields(fieldsDTO.id(), fieldsDTO.monitorTypeId(), fieldsDTO.fieldTypeId());
	}

	public com.queen.infrastructure.persistence.Fields mapToPersistence(final com.queen.application.service.dto.FieldsDTO fieldsDTO) {
		return new com.queen.infrastructure.persistence.Fields(fieldsDTO.id(), fieldsDTO.monitorTypeId(), fieldsDTO.fieldTypeId());
	}

	public Fields mapToDomain(final com.queen.application.service.dto.FieldsDTO fieldsDTO) {
		return new Fields(
				fieldsDTO.id(),
				fieldsDTO.fieldTypeId(),
				fieldsDTO.monitorTypeId()
		);

	}
}
