package com.queen.adapters.web.controller;

import com.queen.adapters.web.dto.MonitorTypeDTO;
import com.queen.adapters.web.dto.MonitorTypeRequest;
import com.queen.adapters.web.dto.MonitorTypeToDTO;
import com.queen.adapters.web.dto.PageSupportDTO;
import com.queen.application.ports.in.AllMonitorTypesQuery;
import com.queen.application.ports.in.CreateMonitorTypeCommand;
import com.queen.application.ports.in.CreateMonitorTypeUseCase;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class MonitorTypeController {
	private final AllMonitorTypesQuery allMonitorTypesQuery;
	private final MonitorTypeToDTO monitorTypeToDTO;
	private final CreateMonitorTypeUseCase createMonitorTypeUseCase;

	public MonitorTypeController(final AllMonitorTypesQuery allMonitorTypesQuery, final MonitorTypeToDTO monitorTypeToDTO, final CreateMonitorTypeUseCase createMonitorTypeUseCase) {
		this.allMonitorTypesQuery 	  = allMonitorTypesQuery;
		this.monitorTypeToDTO     	  = monitorTypeToDTO;
		this.createMonitorTypeUseCase = createMonitorTypeUseCase;
	}

	@GetMapping(value = "/monitor-types", produces = MediaType.APPLICATION_JSON_VALUE)
	//@TODO errors
	Mono<ResponseEntity<List<MonitorTypeDTO>>> loadMonitorTypes(
			@CurrentSecurityContext(expression = "authentication.userId") String userId,
			@RequestParam(name = "page", defaultValue = PageSupportDTO.FIRST_PAGE_NUM) int page,
			@RequestParam(name = "size", defaultValue = PageSupportDTO.DEFAULT_PAGE_SIZE) int size
	) {
		return allMonitorTypesQuery.load(userId, PageRequest.of(page, size)).map(monitorTypeToDTO::toDTO).collectList().map(monitoryTypeDTOs -> {
			return ResponseEntity.ok(monitoryTypeDTOs);
		}).defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@PostMapping(value = "/monitor-types", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	Mono<ResponseEntity<MonitorTypeDTO>> createMonitorType(
			@CurrentSecurityContext(expression = "authentication.userId") String userId,
			@RequestBody MonitorTypeRequest monitorTypeRequest
	) {
		return createMonitorTypeUseCase.createManyMonitorTypes(
				new CreateMonitorTypeCommand(List.of(monitorTypeToDTO.toServiceDTO(monitorTypeRequest, userId)), userId)
		).map(monitorTypePersistence -> {
			return ResponseEntity.ok(monitorTypeToDTO.toDTO(monitorTypePersistence));
		}).single();
	}
}
