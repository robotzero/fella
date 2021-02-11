package com.queen.adapters.web;

import com.queen.application.ports.in.AllMonitorTypesQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class MonitorTypeController {
	private final AllMonitorTypesQuery allMonitorTypesQuery;
	private final MonitorTypeToDTO monitorTypeToDTO;

	public MonitorTypeController(AllMonitorTypesQuery allMonitorTypesQuery, MonitorTypeToDTO monitorTypeToDTO) {
		this.allMonitorTypesQuery = allMonitorTypesQuery;
		this.monitorTypeToDTO = monitorTypeToDTO;
	}

	@GetMapping("/monitor-types")
	Flux<MonitorTypeDTO> loadMonitorTypes() {
		return allMonitorTypesQuery.load().map(monitorType -> {
			return monitorTypeToDTO.toDTO(monitorType);
		});
	}
}
