package com.queen.adapters.web.dto;

import com.queen.domain.monitortype.MonitorType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MonitorTypeToDTO {
	private final FieldTypeToDTO fieldTypeToDTO;

	public MonitorTypeToDTO(final FieldTypeToDTO fieldTypeToDTO) {
		this.fieldTypeToDTO = fieldTypeToDTO;
	}

	public MonitorTypeDTO toDTO(final MonitorType monitorType) {
		return new MonitorTypeDTO(
				monitorType.id(),
				monitorType.name(),
				Optional.ofNullable(monitorType.fieldTypes()).orElseGet(List::of).stream().map(fieldTypeToDTO::toDTO).toList()
		);
	}

	public com.queen.application.service.dto.MonitorTypeDTO toServiceDTO(
			final MonitorTypeRequest monitorTypeRequest,
			final String userId
	) {
		final var monitorId = UUID.randomUUID().toString();
		final var fieldsDTO = monitorTypeRequest.fieldTypes().stream().map(fieldType -> {
			return fieldTypeToDTO.toServiceDTO(monitorId, fieldType);
		}).toList();
		return new com.queen.application.service.dto.MonitorTypeDTO(
				monitorId,
				monitorTypeRequest.name(),
				fieldsDTO, userId
		);
	}
}
