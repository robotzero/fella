package com.queen.adapters.web.controller;

import com.queen.adapters.web.dto.MonitorDTO;
import com.queen.adapters.web.dto.MonitorToDTO;
import com.queen.adapters.web.dto.PageSupportDTO;
import com.queen.application.service.MonitorService;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@Validated
public class MonitorController {
	private final MonitorService monitorService;
	private final MonitorToDTO monitorToDTO;

	public MonitorController(final MonitorService monitorService, final MonitorToDTO monitorToDTO) {
		this.monitorService = monitorService;
		this.monitorToDTO   = monitorToDTO;
	}

	@GetMapping(value = "/monitor-types/{monitorTypeId}/monitors", produces = MediaType.APPLICATION_JSON_VALUE)
	public Flux<MonitorDTO> getMonitors(
			@CurrentSecurityContext(expression = "authentication.userId") String userId,
			@RequestParam(name = "page", defaultValue = PageSupportDTO.FIRST_PAGE_NUM) @Min(0) int page,
			@RequestParam(name = "size", defaultValue = PageSupportDTO.DEFAULT_PAGE_SIZE) @Min(1) int size,
			final @PathVariable String monitorTypeId
	) {
		return monitorService.loadMonitors(monitorTypeId, userId, PageRequest.of(page, size)).map(monitor -> {
			return monitorToDTO.toDTO(monitor);
		});
	}
}
