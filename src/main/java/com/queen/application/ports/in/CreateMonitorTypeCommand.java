package com.queen.application.ports.in;

import com.queen.adapters.web.MonitorTypeDTO;

import javax.validation.constraints.NotNull;
import java.util.List;

public record CreateMonitorTypeCommand(@NotNull List<MonitorTypeDTO> monitorTypeDTOs) {
}
