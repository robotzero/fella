package com.queen.adapters.web.controller;

import com.queen.adapters.web.dto.MigraineMapper;
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

import java.util.Optional;

@RestController
@Validated
public class PeriodController {
	private final PeriodMapper periodMapper;
	private final PeriodService periodService;
	private final MigraineMapper migraineMapper;

	public PeriodController(final PeriodMapper periodMapper, final PeriodService periodService, final MigraineMapper migraineMapper) {
		this.periodMapper = periodMapper;
		this.periodService = periodService;
		this.migraineMapper = migraineMapper;
	}

	//@TODO figure out why authentication.userId is not working
	@PostMapping(value = "/api/period", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	Mono<PeriodDTO> createPeriod(
			final @CurrentSecurityContext(expression = "authentication.userId") String userId,
			final FellaJwtAuthenticationToken token,
			final @RequestBody PeriodRequest periodRequest
	) {
		final var period = periodMapper.mapToDomain(token.getUserId(), periodRequest);
		final var migraine = periodRequest.migraine().map(m -> migraineMapper.mapToDomain(token.getUserId(), m));
		return periodService.createPeriod(period, migraine.orElse(null));
	}

	// @TODO End period endpoint and also implement cyclelength calculation in here, and andjust it
}
