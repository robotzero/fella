//package com.queen.adapters.web.controller;
//
//import com.queen.adapters.web.dto.MonitorTypeDTO;
//import com.queen.adapters.web.dto.MonitorTypeRequest;
//import com.queen.adapters.web.dto.MonitorTypeToDTO;
//import com.queen.adapters.web.dto.PageSupportDTO;
//import com.queen.application.ports.in.MonitorTypesQuery;
//import com.queen.application.ports.in.CreateMonitorTypeCommand;
//import com.queen.application.ports.in.CreateMonitorTypeUseCase;
//import jakarta.validation.constraints.Min;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.CurrentSecurityContext;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import reactor.core.publisher.Mono;
//
//import java.util.List;
//
//@RestController
//@Validated
//public class MonitorTypeController {
//	private final MonitorTypesQuery monitorTypesQuery;
//	private final MonitorTypeToDTO monitorTypeToDTO;
//	private final CreateMonitorTypeUseCase createMonitorTypeUseCase;
//
//	public MonitorTypeController(
//			final MonitorTypesQuery monitorTypesQuery,
//			final MonitorTypeToDTO monitorTypeToDTO,
//			final CreateMonitorTypeUseCase createMonitorTypeUseCase
//	) {
//		this.monitorTypesQuery = monitorTypesQuery;
//		this.monitorTypeToDTO = monitorTypeToDTO;
//		this.createMonitorTypeUseCase = createMonitorTypeUseCase;
//	}
//
//	@GetMapping(value = "/monitor-types", produces = MediaType.APPLICATION_JSON_VALUE)
//	Mono<ResponseEntity<List<MonitorTypeDTO>>> loadMonitorTypes(
//			final @CurrentSecurityContext(expression = "authentication.userId") String userId,
//			final @RequestParam(name = "page", defaultValue = PageSupportDTO.FIRST_PAGE_NUM) @Min(0) int page,
//			final @RequestParam(name = "size", defaultValue = PageSupportDTO.DEFAULT_PAGE_SIZE) @Min(1) int size
//	) {
//		return monitorTypesQuery.load(userId, PageRequest.of(page, size))
//				.map(monitorTypeToDTO::toDTO)
//				.collectList()
//				.map(ResponseEntity::ok)
//				.defaultIfEmpty(ResponseEntity.notFound().build());
//	}
//
//	@GetMapping(value = "/monitor-types/{monitorTypeId}", produces = MediaType.APPLICATION_JSON_VALUE)
//	Mono<ResponseEntity<MonitorTypeDTO>> loadMonitorType(
//			final @CurrentSecurityContext(expression = "authentication.userId") String userId,
//			final @PathVariable String monitorTypeId
//	) {
//		return monitorTypesQuery.load(monitorTypeId, userId)
//				.map(monitorTypeToDTO::toDTO)
//				.map(ResponseEntity::ok)
//				.defaultIfEmpty(ResponseEntity.notFound().build());
//	}
//
//	@PostMapping(value = "/monitor-types", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
//	Mono<ResponseEntity<MonitorTypeDTO>> createMonitorType(
//			final @CurrentSecurityContext(expression = "authentication.userId") String userId,
//			final @RequestBody MonitorTypeRequest monitorTypeRequest
//	) {
//		return createMonitorTypeUseCase.createManyMonitorTypes(
//				new CreateMonitorTypeCommand(List.of(monitorTypeToDTO.toServiceDTO(monitorTypeRequest, userId)), userId)
//		).map(monitorTypePersistence -> ResponseEntity.ok(monitorTypeToDTO.toDTO(monitorTypePersistence))).single();
//	}
//}
