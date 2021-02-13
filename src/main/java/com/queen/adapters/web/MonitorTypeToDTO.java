package com.queen.adapters.web;

import com.queen.domain.monitortype.MonitorType;

public class MonitorTypeToDTO {
	public MonitorTypeDTO toDTO(MonitorType monitorType) {
		return new MonitorTypeDTO(monitorType.id(), monitorType.name());
	}
}
