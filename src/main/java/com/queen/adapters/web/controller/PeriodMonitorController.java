package com.queen.adapters.web.controller;

import com.queen.adapters.web.dto.MonitorDTO;
import com.queen.adapters.web.dto.PeriodMonitorRequest;
import com.queen.adapters.web.dto.PeriodMonitorToDTO;
import com.queen.adapters.web.dto.PageSupportDTO;
import com.queen.application.ports.in.CreatePeriodMonitorCommand;
import com.queen.application.ports.in.CreatePeriodMonitorUseCase;
import com.queen.application.service.PeriodMonitorService;
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
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@Validated
public class PeriodMonitorController {
	private final PeriodMonitorService periodMonitorService;
	private final PeriodMonitorToDTO periodMonitorToDTO;
	private final CreatePeriodMonitorUseCase createPeriodMonitorUseCase;

	public PeriodMonitorController(
			final PeriodMonitorService periodMonitorService,
			final PeriodMonitorToDTO periodMonitorToDTO,
			final CreatePeriodMonitorUseCase createPeriodMonitorUseCase
	) {
		this.periodMonitorService  = periodMonitorService;
		this.periodMonitorToDTO    = periodMonitorToDTO;
		this.createPeriodMonitorUseCase  = createPeriodMonitorUseCase;
	}

	@GetMapping(value = "/period-monitors/{periodMonitorId}/periods", produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<ResponseEntity<List<MonitorDTO>>> getPeriods(
			final @CurrentSecurityContext(expression = "authentication.userId") String userId,
			final @RequestParam(name = "page", defaultValue = PageSupportDTO.FIRST_PAGE_NUM) @Min(0) int page,
			final @RequestParam(name = "size", defaultValue = PageSupportDTO.DEFAULT_PAGE_SIZE) @Min(1) int size,
			final @PathVariable String periodMonitorId
	) {
		return periodMonitorService.loadPeriodMonitors(periodMonitorId, userId, PageRequest.of(page, size)).map(periodMonitorToDTO::toDTO)
				.collectList()
				.map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@GetMapping(value = "/period-monitors", produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<ResponseEntity<List<MonitorDTO>>> getPeriodMonitors(
			final @CurrentSecurityContext(expression = "authentication.userId") String userId,
			final @RequestParam(name = "page", defaultValue = PageSupportDTO.FIRST_PAGE_NUM) @Min(0) int page,
			final @RequestParam(name = "size", defaultValue = PageSupportDTO.DEFAULT_PAGE_SIZE) @Min(1) int size,
			final @PathVariable String periodMonitorId
	) {
		return periodMonitorService.loadPeriodMonitors(periodMonitorId, userId, PageRequest.of(page, size)).map(periodMonitorToDTO::toDTO)
				.collectList()
				.map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@PostMapping(value = "/period-monitors", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	Mono<ResponseEntity<MonitorDTO>> createPeriodMonitor(
			final @CurrentSecurityContext(expression = "authentication.userId") String userId,
			final @PathVariable String monitorTypeId,
			final @RequestBody PeriodMonitorRequest periodMonitorRequest
	) {
		return createPeriodMonitorUseCase.createPeriodMonitor(
				new CreatePeriodMonitorCommand(periodMonitorToDTO.toServiceDTO(periodMonitorRequest, monitorTypeId, userId))
		).map(periodMonitorPersistance -> ResponseEntity.ok(periodMonitorToDTO.toDTO(periodMonitorPersistance))).single();
	}
}
