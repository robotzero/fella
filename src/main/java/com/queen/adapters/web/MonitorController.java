package com.queen.adapters.web;

import com.queen.application.service.MonitorService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class MonitorController {
	private final MonitorService monitorService;
	private final MonitorToDTO monitorToDTO;

	public MonitorController(final MonitorService monitorService, final MonitorToDTO monitorToDTO) {
		this.monitorService = monitorService;
		this.monitorToDTO   = monitorToDTO;
	}

	@GetMapping("/monitors")
	public Flux<MonitorDTO> getAllMonitors(Authentication authentication, JwtAuthenticationToken jwtAuthenticationToken) {
		return monitorService.loadAllMonitors().map(monitor -> {
			return monitorToDTO.toDTO(monitor);
		});
	}
}
