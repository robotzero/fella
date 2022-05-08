package com.queen.adapters.web.controller;

import com.queen.adapters.web.dto.MonitorDTO;
import com.queen.adapters.web.dto.MonitorRequest;
import com.queen.adapters.web.dto.MonitorToDTO;
import com.queen.adapters.web.dto.PageSupportDTO;
import com.queen.application.ports.in.CreateMonitorCommand;
import com.queen.application.ports.in.CreateMonitorUseCase;
import com.queen.application.service.MonitorService;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Validated
public class MonitorController {
	private final MonitorService monitorService;
	private final MonitorToDTO monitorToDTO;
	private final CreateMonitorUseCase createMonitorUseCase;

	public MonitorController(
			final MonitorService monitorService,
			final MonitorToDTO monitorToDTO,
			final CreateMonitorUseCase createMonitorUserCase
	) {
		this.monitorService        = monitorService;
		this.monitorToDTO          = monitorToDTO;
		this.createMonitorUseCase = createMonitorUserCase;
	}

	@GetMapping(value = "/monitor-types/{monitorTypeId}/monitors", produces = MediaType.APPLICATION_JSON_VALUE)
	public Flux<MonitorDTO> getMonitors(
			final @CurrentSecurityContext(expression = "authentication.userId") String userId,
			final @RequestParam(name = "page", defaultValue = PageSupportDTO.FIRST_PAGE_NUM) @Min(0) int page,
			final @RequestParam(name = "size", defaultValue = PageSupportDTO.DEFAULT_PAGE_SIZE) @Min(1) int size,
			final @PathVariable String monitorTypeId
	) {
		return monitorService.loadMonitors(monitorTypeId, userId, PageRequest.of(page, size)).map(monitorToDTO::toDTO);
	}

	@PostMapping(value = "/monitor-types/{monitorTypeId}/monitors", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	Mono<ResponseEntity<MonitorDTO>> createMonitor(
			final @CurrentSecurityContext(expression = "authentication.userId") String userId,
			final @PathVariable String monitorTypeId,
			final @RequestBody MonitorRequest monitorRequest
	) {
		return createMonitorUseCase.createMonitor(
				new CreateMonitorCommand(monitorToDTO.toServiceDTO(monitorRequest, monitorTypeId, userId))
		).map(monitorPersistence -> ResponseEntity.ok(monitorToDTO.toDTO(monitorPersistence))).single();
	}
}
