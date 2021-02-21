package com.queen.adapters.web;

import com.queen.domain.monitortype.MonitorType;

import java.util.List;

public class MonitorTypeToDTO {
	public MonitorTypeDTO toDTO(final MonitorType monitorType) {
		return new MonitorTypeDTO(
				monitorType.id(),
				monitorType.name(),
				monitorType.userId(),
				List.of()
		);
	}
}
