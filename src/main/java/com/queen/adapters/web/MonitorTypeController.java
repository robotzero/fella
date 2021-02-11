package com.queen.adapters.web;

import com.queen.application.ports.in.AllMonitorTypesQuery;
import com.queen.domain.monitortype.MonitorType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class MonitorTypeController {
	private final AllMonitorTypesQuery allMonitorTypesQuery;

	public MonitorTypeController(AllMonitorTypesQuery allMonitorTypesQuery) {
		this.allMonitorTypesQuery = allMonitorTypesQuery;
	}

	@GetMapping("/monitor-types")
	Flux<MonitorType> loadMonitorTypes() {
		return allMonitorTypesQuery.load();
	}
}
