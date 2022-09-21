package com.queen.adapters.web.dto;

import java.util.Date;

public record PeriodMonitorDTO(
		String id,
		MonitorTypeDTO monitorType,
		Date periodDate,
		Integer painLevel,
		Integer flowLevel,
		String notes,
		String tags,
		Date createdAt
) {}
