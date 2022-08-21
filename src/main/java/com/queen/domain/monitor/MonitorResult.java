package com.queen.domain.monitor;

import com.queen.domain.monitortype.MonitorType;
import com.queen.domain.monitortype.MonitorTypeResult;

import java.util.Date;

public sealed interface MonitorResult {
	record PeriodMonitor(String id, Date startDate, Date endDate, Integer painLevel, String notes, MonitorTypeResult monitorTypeResult) implements MonitorResult {}
	record StomachMonitor(String id, String name, MonitorType monitorType) implements MonitorResult {}
	record TabletsTakenMonitor(String id, String name, MonitorType monitorType) implements MonitorResult {}
}
