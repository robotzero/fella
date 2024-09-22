package com.queen.adapters.web.controller;

import com.queen.adapters.web.dto.PeriodRequest;
import com.queen.adapters.web.dto.PeriodToDTOMapper;
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
	private final PeriodToDTOMapper periodToDTOMapper;

	public PeriodController(final PeriodToDTOMapper periodToDTOMapper) {
		this.periodToDTOMapper = periodToDTOMapper;
	}

	@PostMapping(value = "/api/period", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	Mono<com.queen.infrastructure.persistence.Period> createPeriod(
			final @CurrentSecurityContext(expression = "authentication.userId") String userId,
			final FellaJwtAuthenticationToken token,
			final @RequestBody PeriodRequest periodRequest
	) {
		// @TODO make sure that no new period can be created if there is already an active period
		final var periodServiceDTO = periodToDTOMapper.mapToServiceDTO(token.getUserId(), periodRequest);
		System.out.println(periodServiceDTO);
		return Mono.empty();
	}

	// @TODO End period endpoint and also implement cyclelength calculation in here, and andjust it
}
