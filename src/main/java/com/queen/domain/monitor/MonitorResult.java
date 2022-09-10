package com.queen.domain.monitor;

import com.queen.domain.monitortype.MonitorType;
import com.queen.domain.monitortype.MonitorTypeResult;
import com.queen.infrastructure.persitence.monitor.Period;

import java.util.Date;
import java.util.List;

public sealed interface MonitorResult {
	record PeriodMonitor(String id, Date startDate, Date endDate, String notes, MonitorTypeResult monitorTypeResult, List<Period> periods) implements MonitorResult {}
	record StomachMonitor(String id, String name, MonitorType monitorType) implements MonitorResult {}
	record TabletsTakenMonitor(String id, String name, MonitorType monitorType) implements MonitorResult {}
}
