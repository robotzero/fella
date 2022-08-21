package com.queen.adapters.web.dto;

public sealed interface MonitorDTO {
	record PeriodDTO(String id, MonitorTypeDTO monitorType) implements MonitorDTO {}
}
