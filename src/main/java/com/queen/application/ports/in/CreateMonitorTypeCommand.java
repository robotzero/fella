package com.queen.application.ports.in;

import com.queen.application.service.dto.MonitorTypeDTO;

import java.util.List;

public record CreateMonitorTypeCommand(List<MonitorTypeDTO> monitorTypeDTOs) {
}
