package com.queen.domain.monitor;

import com.queen.domain.monitortype.MonitorType;

public record PeriodMonitor(String id, String name, MonitorType monitorType) {
}
