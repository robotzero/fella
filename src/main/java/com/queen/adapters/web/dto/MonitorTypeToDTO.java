package com.queen.adapters.web.dto;

import com.queen.domain.monitortype.MonitorType;

public class MonitorTypeToDTO {
	private final FieldTypeToDTO fieldTypeToDTO;

	public MonitorTypeToDTO(final FieldTypeToDTO fieldTypeToDTO) {
		this.fieldTypeToDTO = fieldTypeToDTO;
	}

	public MonitorTypeDTO toDTO(final MonitorType monitorType) {
		return new MonitorTypeDTO(
				monitorType.id(),
				monitorType.name(),
				monitorType.fieldTypes().stream().map(fieldTypeToDTO::toDTO).toList()
		);
	}
}
