package com.queen.adapters.web.controller;

import com.queen.adapters.web.dto.TrackingDTO;
import com.queen.application.service.DailyTrackingService;
import com.queen.configuration.FellaJwtAuthenticationToken;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
public class DailyTrackingController {
	private final DailyTrackingService dailyTrackingService;

	public DailyTrackingController(
			final DailyTrackingService dailyTrackingService
	) {
		this.dailyTrackingService = dailyTrackingService;
	}

	@GetMapping(value = "/api/tracking/all", produces = MediaType.APPLICATION_JSON_VALUE)
	List<TrackingDTO> getDailyTrackingCalendar(
			FellaJwtAuthenticationToken token
	) {
		return dailyTrackingService.getDailyTracking(token.getUserId());
	}
}
