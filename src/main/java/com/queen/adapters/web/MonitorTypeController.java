package com.queen.adapters.web;

import com.queen.application.ports.in.AllMonitorTypesQuery;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@EnableReactiveMethodSecurity
public class MonitorTypeController {
	private final AllMonitorTypesQuery allMonitorTypesQuery;
	private final MonitorTypeToDTO monitorTypeToDTO;

	public MonitorTypeController(final AllMonitorTypesQuery allMonitorTypesQuery, final MonitorTypeToDTO monitorTypeToDTO) {
		this.allMonitorTypesQuery = allMonitorTypesQuery;
		this.monitorTypeToDTO     = monitorTypeToDTO;
	}

	@GetMapping("/monitor-types")
	//@TODO response entity?
	//@TODO Change FellaUser to be DTO object not domain
	Flux<MonitorTypeDTO> loadMonitorTypes(@CurrentSecurityContext(expression = "authentication.userId") String userId) {
		return allMonitorTypesQuery.load(userId).map(monitorType -> {
			return monitorTypeToDTO.toDTO(monitorType);
		});
	}

	@PostMapping("/monitor-types")
	void createNewMonitorType() {

	}
}
