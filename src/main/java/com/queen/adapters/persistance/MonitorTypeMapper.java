package com.queen.adapters.persistance;

import com.queen.adapters.web.dto.MonitorTypeDTO;
import com.queen.domain.fieldtype.FieldType;
import com.queen.domain.fieldtype.FieldTypeValue;
import com.queen.domain.monitortype.MonitorType;
import com.queen.domain.monitortype.MonitorTypeResult;

import java.util.List;
import java.util.Map;

public class MonitorTypeMapper {
	public MonitorType mapToDomain(final com.queen.infrastructure.persistence.MonitorType monitorType) {
		return new MonitorType(monitorType.getId(), monitorType.getName(), null);
	}

	public MonitorTypeResult mapToConcreteMonitorType(final com.queen.infrastructure.persistence.MonitorType monitorType) {
		return switch (monitorType.getName().toLowerCase()) {
			case "period" -> new MonitorTypeResult.Period(monitorType.getId(), monitorType.getName(), null);
			case "stomach" -> new MonitorTypeResult.Stomach(monitorType.getId(), monitorType.getName(), null);
			case "tablets taken" -> new MonitorTypeResult.TabletsTaken(monitorType.getId(), monitorType.getName(), null);
			default -> throw new IllegalStateException("Unexpected value: " + monitorType.getName().toLowerCase());
		};
	}

	public MonitorType mapToDomainWithFieldTypes(final com.queen.infrastructure.persistence.MonitorType monitorType, final Map<String, List<FieldTypeValue>> fieldTypeValues) {
		return new MonitorType(monitorType.getId(), monitorType.getName(), null);
	}

	public MonitorType mapToDomain(final MonitorTypeDTO monitorTypeDTO) {
		return new MonitorType(monitorTypeDTO.id(), monitorTypeDTO.name(), null);
	}

	public com.queen.infrastructure.persistence.MonitorType mapToPersistence(final com.queen.application.service.dto.MonitorTypeDTO monitorTypeDTO) {
		return new com.queen.infrastructure.persistence.MonitorType(monitorTypeDTO.id(), monitorTypeDTO.name(), monitorTypeDTO.userId());
	}

	public MonitorType mapToDomain(com.queen.infrastructure.persistence.MonitorType monitorTypePersistance, List<FieldType> fieldTypes) {
		return new MonitorType(monitorTypePersistance.getId(), monitorTypePersistance.getName(), fieldTypes);
	}
}
