package com.queen.application.ports.in;

import com.queen.adapters.web.MonitorTypeDTO;

import java.util.List;

public record CreateMonitorTypeCommand(List<MonitorTypeDTO> monitorTypeDTOs) {
}
