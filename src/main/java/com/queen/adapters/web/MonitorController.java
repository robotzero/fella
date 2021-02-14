package com.queen.adapters.web;

import com.queen.application.service.MonitorService;
import org.springframework.security.core.Authentication;
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
	public Flux<MonitorDTO> getAllMonitors(Authentication authentication) {
		return monitorService.loadAllMonitors().map(monitor -> {
			return monitorToDTO.toDTO(monitor);
		});
	}
}
