package com.queen.adapters.web.controller;

import com.queen.adapters.web.dto.PeriodMonitorDTO;
import com.queen.adapters.web.dto.PeriodMonitorRequest;
import com.queen.adapters.web.dto.PeriodRequest;
import com.queen.application.ports.in.CreatePeriodMonitorCommand;
import com.queen.infrastructure.persistence.PeriodRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Validated
public class PeriodController {
	private final PeriodRepository periodRepository;

	public PeriodController(final PeriodRepository periodRepository) {
		this.periodRepository = periodRepository;
	}

	@PostMapping(value = "/api/period", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	Mono<com.queen.infrastructure.persistence.Period> createPeriod(
			final @CurrentSecurityContext(expression = "authentication.userId") String userId,
			final @RequestBody PeriodRequest periodRequest
	) {
		return Mono.empty();
	}
}
