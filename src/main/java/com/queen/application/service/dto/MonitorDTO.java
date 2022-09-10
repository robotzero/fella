package com.queen.application.service.dto;

import java.util.Date;

public sealed interface MonitorDTO {
	record PeriodMonitorDTO(String id, String monitorTypeId, String userId, Date startDate, String notes, PeriodDTO periodDTO) implements MonitorDTO {}
	record PeriodDTO(String id, String periodMonitorId, Integer painLevel, Date dateCreated, String notes) {}
}
