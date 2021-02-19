package com.queen.adapters.web;

import com.queen.application.ports.in.AllMonitorTypesQuery;
import com.queen.domain.user.User;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class MonitorTypeController {
	private final AllMonitorTypesQuery allMonitorTypesQuery;
	private final MonitorTypeToDTO monitorTypeToDTO;

	public MonitorTypeController(final AllMonitorTypesQuery allMonitorTypesQuery, final MonitorTypeToDTO monitorTypeToDTO) {
		this.allMonitorTypesQuery = allMonitorTypesQuery;
		this.monitorTypeToDTO     = monitorTypeToDTO;
	}

	@GetMapping("/monitor-types")
	//@TODO response entity?
	//@TODO UserDetails implementation, so we can inject user here?
	Flux<MonitorTypeDTO> loadMonitorTypes(final JwtAuthenticationToken jwtAuthenticationToken) {
		String userId = ((User) jwtAuthenticationToken.getDetails()).id();
		return allMonitorTypesQuery.load(userId).map(monitorType -> {
			return monitorTypeToDTO.toDTO(monitorType);
		});
	}

	@PostMapping("/monitor-types")
	void createNewMonitorType() {

	}
}
