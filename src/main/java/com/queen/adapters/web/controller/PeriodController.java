package com.queen.adapters.web.controller;

import com.queen.adapters.web.dto.DailyTrackingMapper;
import com.queen.adapters.web.dto.EndPeriodRequest;
import com.queen.adapters.web.dto.MigraineMapper;
import com.queen.adapters.web.dto.PeriodDTO;
import com.queen.adapters.web.dto.FullPeriodRequest;
import com.queen.adapters.web.dto.PeriodMapper;
import com.queen.application.service.PeriodService;
import com.queen.application.service.exception.DatabaseException;
import com.queen.configuration.FellaJwtAuthenticationToken;
import com.queen.domain.DailyTracking;
import jakarta.validation.Valid;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@RestController
@Validated
public class PeriodController {
	private final PeriodMapper periodMapper;
	private final PeriodService periodService;
	private final MigraineMapper migraineMapper;
	private final DailyTrackingMapper dailyTrackingMapper;

	public PeriodController(
			final PeriodMapper periodMapper,
			final PeriodService periodService,
			final MigraineMapper migraineMapper,
			final DailyTrackingMapper dailyTrackingMapper
	) {
		this.periodMapper = periodMapper;
		this.periodService = periodService;
		this.migraineMapper = migraineMapper;
		this.dailyTrackingMapper = dailyTrackingMapper;
	}

	@PostMapping(value = "/api/period", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	PeriodDTO createPeriod(
			final FellaJwtAuthenticationToken token,
			final @Valid @RequestBody FullPeriodRequest periodRequest
	) {
		final var period = periodMapper.mapToDomain(token.getUserId(), periodRequest);
		final var migraine = periodRequest.migraine().map(m -> migraineMapper.mapToDomain(token.getUserId(), m));
		final var dailyTracking = periodRequest.dailyTracking().map(d -> dailyTrackingMapper.mapToDomain(token.getUserId(), d));
		return periodService.createPeriod(period, migraine.orElse(null), dailyTracking.orElse(new DailyTracking(token.getUserId(), LocalDate.now(), 0, 0)));
	}

	@PutMapping(value = "/api/period/end", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	PeriodDTO endPeriod(
			final FellaJwtAuthenticationToken token,
			final @Valid @RequestBody EndPeriodRequest endPeriodRequest
	) {
		final var period = periodMapper.mapToDomain(token.getUserId(), endPeriodRequest);
		return periodService.endPeriod(period);
	}

	@GetMapping(value = "/api/period/all", produces = MediaType.APPLICATION_JSON_VALUE)
	List<PeriodDTO> getPeriods(
			FellaJwtAuthenticationToken token
	) {
		return periodService.getPeriods(token.getUserId());
	}
}
