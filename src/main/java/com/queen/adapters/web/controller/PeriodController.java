package com.queen.adapters.web.controller;

import com.queen.adapters.web.dto.PeriodDTO;
import com.queen.adapters.web.dto.PeriodRequest;
import com.queen.adapters.web.dto.PeriodMapper;
import com.queen.application.service.PeriodService;
import com.queen.configuration.FellaJwtAuthenticationToken;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Validated
public class PeriodController {
	private final PeriodMapper periodToDTOMapper;
	private final PeriodService periodService;

	public PeriodController(final PeriodMapper periodToDTOMapper, final PeriodService periodService) {
		this.periodToDTOMapper = periodToDTOMapper;
		this.periodService = periodService;
	}

	//@TODO figure out why authentication.userId is not working
	@PostMapping(value = "/api/period", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	Mono<PeriodDTO> createPeriod(
			final @CurrentSecurityContext(expression = "authentication.userId") String userId,
			final FellaJwtAuthenticationToken token,
			final @RequestBody PeriodRequest periodRequest
	) {
		final var period = periodToDTOMapper.mapToDomain(token.getUserId(), periodRequest);
		return periodService.createPeriod(period);
	}

	// @TODO End period endpoint and also implement cyclelength calculation in here, and andjust it
}
