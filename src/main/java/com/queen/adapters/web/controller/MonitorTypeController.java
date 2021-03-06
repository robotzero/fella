package com.queen.adapters.web.controller;

import com.queen.adapters.web.dto.MonitorTypeDTO;
import com.queen.adapters.web.dto.MonitorTypeToDTO;
import com.queen.adapters.web.dto.PageSupportDTO;
import com.queen.application.ports.in.AllMonitorTypesQuery;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class MonitorTypeController {
	private final AllMonitorTypesQuery allMonitorTypesQuery;
	private final MonitorTypeToDTO monitorTypeToDTO;

	public MonitorTypeController(final AllMonitorTypesQuery allMonitorTypesQuery, final MonitorTypeToDTO monitorTypeToDTO) {
		this.allMonitorTypesQuery = allMonitorTypesQuery;
		this.monitorTypeToDTO     = monitorTypeToDTO;
	}

	@GetMapping("/monitor-types")
	//@TODO errors
	Mono<ResponseEntity<List<MonitorTypeDTO>>> loadMonitorTypes(
			@CurrentSecurityContext(expression = "authentication.userId") String userId,
			@RequestParam(name = "page", defaultValue = PageSupportDTO.FIRST_PAGE_NUM) int page,
			@RequestParam(name = "size", defaultValue = PageSupportDTO.DEFAULT_PAGE_SIZE) int size
	) {
		return allMonitorTypesQuery.load(userId, PageRequest.of(page, size)).map(monitorType -> {
			return monitorTypeToDTO.toDTO(monitorType);
		}).collectList().map(monitoryTypeDTOs -> {
			return ResponseEntity.status(HttpStatus.OK).body(monitoryTypeDTOs);
		}).defaultIfEmpty(ResponseEntity.notFound().build());
	}
}
