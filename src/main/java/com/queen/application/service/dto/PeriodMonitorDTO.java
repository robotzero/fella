package com.queen.application.service.dto;

import java.util.Date;

public record PeriodMonitorDTO(String id, String monitorTypeId, String userId, Date periodDate, Integer painLevel, Integer flowLevel, String notes) {}
