package com.queen.application.ports.in;

import com.queen.application.service.dto.MonitorDTO;

public record CreatePeriodMonitorCommand(MonitorDTO monitorDTO) {
}
