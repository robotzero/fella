package com.queen.domain.monitor;

import com.queen.domain.monitortype.MonitorType;
import com.queen.domain.monitortype.MonitorTypeResult;
import com.queen.infrastructure.persitence.monitor.Period;

import java.util.Date;
import java.util.List;

public sealed interface PeriodMonitorResult {
	record PeriodMonitor(String id, Date startDate, String notes, MonitorTypeResult monitorTypeResult, List<Period> periods) implements PeriodMonitorResult {}
	record StomachMonitor(String id, String name, MonitorType monitorType) implements PeriodMonitorResult {}
	record TabletsTakenMonitor(String id, String name, MonitorType monitorType) implements PeriodMonitorResult {}
}
