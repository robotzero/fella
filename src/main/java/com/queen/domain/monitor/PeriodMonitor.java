package com.queen.domain.monitor;

import java.util.Date;

public record PeriodMonitor(String id, Date periodDate, Integer painLevel, Integer flowLevel, String notes) {}
