package com.queen.adapters.web;

import com.queen.domain.monitortype.MonitorType;

import java.util.List;

public class MonitorTypeToDTO {
	private final FieldTypeToDTO fieldTypeToDTO;

	public MonitorTypeToDTO(final FieldTypeToDTO fieldTypeToDTO) {
		this.fieldTypeToDTO = fieldTypeToDTO;
	}

	public MonitorTypeDTO toDTO(final MonitorType monitorType) {
		return new MonitorTypeDTO(
				monitorType.id(),
				monitorType.name(),
				List.of(),
				monitorType.fieldTypes().stream().map(fieldTypeToDTO::toDTO).toList()
		);
	}
}
