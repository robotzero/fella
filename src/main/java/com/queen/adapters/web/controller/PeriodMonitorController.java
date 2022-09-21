package com.queen.adapters.web.controller;

import com.queen.adapters.web.dto.PeriodMonitorDTO;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

	@GetMapping(value = "/monitor-type/{monitorTypeId}/period-monitors", produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<ResponseEntity<List<PeriodMonitorDTO>>> getPeriods(
			final @CurrentSecurityContext(expression = "authentication.userId") String userId,
			final @RequestParam(name = "page", defaultValue = PageSupportDTO.FIRST_PAGE_NUM) @Min(0) int page,
			final @RequestParam(name = "size", defaultValue = PageSupportDTO.DEFAULT_PAGE_SIZE) @Min(1) int size,
			final @PathVariable String monitorTypeId
	) {
		return periodMonitorService.loadPeriodMonitors(monitorTypeId, userId, PageRequest.of(page, size)).map(periodMonitorToDTO::toDTO)
				.collectList()
				.map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@GetMapping(value = "/monitor-type/{monitorTypeId}/period-monitors/{dateToSearchBy}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<ResponseEntity<PeriodMonitorDTO>> getPeriodByDate(
			final @CurrentSecurityContext(expression = "authentication.userId") String userId,
			final @PathVariable String monitorTypeId,
			final @PathVariable String dateToSearchBy
	) throws ParseException {
		return periodMonitorService.loadPeriodMonitorByDate(monitorTypeId, userId, dateToSearchBy)
				.map(periodMonitorToDTO::toDTO)
				.map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@PostMapping(value = "/monitor-type/{monitorTypeId}/period-monitors", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	Mono<ResponseEntity<PeriodMonitorDTO>> createPeriodMonitor(
			final @CurrentSecurityContext(expression = "authentication.userId") String userId,
			final @PathVariable String monitorTypeId,
			final @RequestBody PeriodMonitorRequest periodMonitorRequest
	) {
		return createPeriodMonitorUseCase.createPeriodMonitor(
				new CreatePeriodMonitorCommand(periodMonitorToDTO.toServiceDTO(periodMonitorRequest, monitorTypeId, userId))
		).map(periodMonitorPersistance -> ResponseEntity.ok(periodMonitorToDTO.toDTO(periodMonitorPersistance))).single();
	}
}
