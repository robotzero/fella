package com.queen.application.ports.in;

import com.queen.application.service.dto.MonitorDTO;

public record CreateMonitorCommand(MonitorDTO monitorDTO) {
}
