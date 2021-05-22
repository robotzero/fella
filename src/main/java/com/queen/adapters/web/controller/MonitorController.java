package com.queen.adapters.web.controller;

import com.queen.adapters.web.dto.MonitorDTO;
import com.queen.adapters.web.dto.MonitorToDTO;
import com.queen.application.service.MonitorService;
import com.queen.configuration.FellaJwtAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
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
	public Flux<MonitorDTO> getAllMonitors(final FellaJwtAuthenticationToken jwtAuthenticationToken) {
		ReactiveSecurityContextHolder.getContext().hasElement().filter(a -> a == false).subscribe(c -> System.out.println("NOT!!!!!!!!!!!!!"));
		return monitorService.loadAllMonitors().map(monitor -> {
			return monitorToDTO.toDTO(monitor);
		});
	}
}
