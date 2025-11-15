package com.queen.adapters.web.controller.view;

import com.queen.adapters.web.dto.FullPeriodRequest;
import com.queen.adapters.web.dto.MigraineDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClient;

import java.security.Principal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class IndexController {
	private static final DateTimeFormatter DMY = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	private final RestClient restClient;

	public IndexController(final RestClient restClient) {
		this.restClient = restClient;
	}

	// HTMX target: returns the calendar fragment HTML
	@GetMapping("/calendar")
	public String calendar(@RequestParam(value = "year", required = false) Integer year,
			Model model, @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient) {
		int y = (year != null) ? year : Year.now().getValue();
		ParameterizedTypeReference<@org.jetbrains.annotations.NotNull List<Tracking>> periodsBody = new ParameterizedTypeReference<>() {};

		String token = authorizedClient.getAccessToken().getTokenValue();
		List<Tracking> dto = restClient.get()
				.uri("api/tracking/all")
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer "  + token)
				.retrieve()
				.body(periodsBody);

		if (dto == null) {
			model.addAttribute("year", y);
			model.addAttribute("months", buildMonths(y, List.of(), ZoneId.systemDefault()));
			model.addAttribute("error", "Something went wrong");
			return "calendar";
		}
		List<Tracking> periodDates = dto.stream().map(d -> {
			var migraine = d.migraine();
			if (migraine == null) {
				return d.withPeriodId(d.period().periodId);
			}
			if (d.period() == null) {
				return d.withMigraine(migraine.severityLevel(), migraine.migraineId());
			}
			return d.withPeriodId(d.period().periodId).withMigraine(d.migraine.severityLevel(), d.migraine.migraineId());
		}).toList();

		List<MonthVM> months = buildMonths(y, periodDates, ZoneId.systemDefault());

		model.addAttribute("year", y);
		model.addAttribute("months", months);
		return "calendar";
	}

	@PostMapping("/tracking/save")
	public String saveTracking(
			@RequestParam LocalDate trackingDate,
			@RequestParam(required = false) int painLevel,
			@RequestParam(required = false) int flowLevel,
			@RequestParam(required = false) int migraineLevel,
			@RegisteredOAuth2AuthorizedClient("fella-webui") OAuth2AuthorizedClient client,
			Model model) {

		if (trackingDate == null) {
			model.addAttribute("message", "Tracking date is required");
			return "fragments/modal-success";
		}
		if (painLevel == 0 && flowLevel == 0 && migraineLevel == 0) {
			model.addAttribute("message", "At least one of pain level, flow level, or migraine level must be provided");
			return "fragments/modal-success";
		}

		var token = client.getAccessToken().getTokenValue();
		restClient.post().uri("api/period").accept(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + token)
				.body(FullPeriodRequest.fromUI(trackingDate, painLevel, flowLevel, migraineLevel)).retrieve().body(Void.class);
		// Return a small snippet that replaces the modal with success feedback
		model.addAttribute("message", "Saved successfully for " + trackingDate);
		return "fragments/modal-success";
	}

	@PostMapping("/tracking/delete")
	public String deleteTracking(
			@RequestParam String periodId,
			@RegisteredOAuth2AuthorizedClient("fella-webui") OAuth2AuthorizedClient client
	) {
		var token = client.getAccessToken().getTokenValue();
		restClient.post().uri("api/periods/delete").accept(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + token)
				.body(Map.of("periodIds", List.of(UUID.fromString(periodId)))).retrieve().body(Void.class);
		return "index";
	}

	@PutMapping("/tracking/edit/{periodId}")
	public String editTracking(
			@RequestParam LocalDate trackingDate,
			@RequestParam int painLevel,
			@RequestParam int flowLevel,
			@RequestParam(required = false) int migraineLevel,
			@RequestParam(required = false) String trackingId,
			@RequestParam(required = false) String migraineId,
			@PathVariable String periodId,
			@RegisteredOAuth2AuthorizedClient("fella-webui") OAuth2AuthorizedClient client
	) {
		var token = client.getAccessToken().getTokenValue();
		var trackingIdFinal = trackingId.isEmpty() ? null : UUID.fromString(trackingId);
		var migraineIdFinal = migraineId.isEmpty() ? null : UUID.fromString(migraineId);
		var request = FullPeriodRequest.fromUIEdit(trackingDate, painLevel, flowLevel, migraineLevel, trackingIdFinal, migraineIdFinal);
		restClient.put().uri("api/periods/" + periodId).accept(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + token)
				.body(request).retrieve().body(Void.class);
		return "index";
	}

	@GetMapping("/")
	public String viewPeriod(Model model, Principal principal, Authentication authentication, @RegisteredOAuth2AuthorizedClient("fella-webui") OAuth2AuthorizedClient authorizedClient) {
		String token = authorizedClient.getAccessToken().getTokenValue();
		System.out.println(token);
		System.out.println(authentication.getPrincipal());
		System.out.println(principal.getName());
		model.addAttribute("name", principal.getName());
		return "index";
	}

	private List<MonthVM> buildMonths(int year, List<Tracking> trackingList, ZoneId zone) {
		LocalDate today = LocalDate.now(zone);
		List<MonthVM> res = new ArrayList<>(12);
		Map<LocalDate, Tracking> trackingByDate = trackingList.stream()
				.collect(Collectors.toMap(
						t -> LocalDate.parse(t.trackingDate(), DMY), // same formatter as before
						t -> t,
						(a, b) -> b
				));
		for (int m = 1; m <= 12; m++) {
			YearMonth ym = YearMonth.of(year, m);
			List<DayCell> cells = new ArrayList<>();

			// Monday-first offset
			LocalDate first = ym.atDay(1);
			int iso = first.getDayOfWeek().getValue(); // 1..7 (Mon..Sun)
			int offset = iso - 1; // 0..6

			// Leading blanks
			for (int i = 0; i < offset; i++) cells.add(new DayCell(null, true, false, false, false, null, null, null, null, null, null));

			// Actual days
			for (int d = 1; d <= ym.lengthOfMonth(); d++) {
				LocalDate date = ym.atDay(d);
				Tracking t = trackingByDate.get(date);
				boolean weekend = date.getDayOfWeek() == DayOfWeek.SATURDAY
						|| date.getDayOfWeek() == DayOfWeek.SUNDAY;
				boolean isToday = date.equals(today);
				cells.add(DayCell.of(d, weekend, isToday, t));
			}

			// Trailing blanks to complete rows
			int total = cells.size();
			int target = ((total + 6) / 7) * 7;
			for (int i = total; i < target; i++) cells.add(new DayCell(null, true, false, false, false, null, null, null, null, null, null));

			res.add(new MonthVM(ym.getMonth().name().charAt(0) + ym.getMonth().name().substring(1).toLowerCase(), cells));
		}
		return res;
	}

	// --- simple DTOs (replace with your real ones) -------------------------
	public record PeriodDto(String periodId) {}
	public record Migraine(UUID migraineId, String migraineDate, Integer severityLevel, String description) {}
	public record Tracking(String trackingId, UUID migraineId, Integer painLevel, Integer flowLevel, String trackingDate, String periodId, Integer migraineLevel, PeriodDto period, MigraineDTO migraine) {
		public Tracking withPeriodId(String periodId) {
			return new Tracking(this.trackingId, this.migraineId, this.painLevel, this.flowLevel, this.trackingDate, periodId, this.migraineLevel, this.period, this.migraine);
		}
		public Tracking withMigraine(Integer migraineLevel, UUID migraineId) {
			return new Tracking(this.trackingId, migraineId, this.painLevel, this.flowLevel, this.trackingDate, this.periodId, migraineLevel, this.period, this.migraine);
		}
	}

	// --- View Model ---------------------------------------------------------
	public record MonthVM(String name, List<DayCell> days) {}
	public record DayCell(Integer dayOfMonth, boolean blank, boolean weekend, boolean today, boolean tracked, Integer painLevel, Integer flowLevel, String periodId, Integer migraineLevel, String trackingId, UUID migraineId) {
		public static DayCell of(int d, boolean weekend, boolean today, Tracking trackingForThisDate) {
			boolean tracked = trackingForThisDate != null;
			Integer painLevel = tracked ? trackingForThisDate.painLevel() : null;
			Integer flowLevel = tracked ? trackingForThisDate.flowLevel() : null;
			String periodId = tracked ? trackingForThisDate.periodId() : null;
			Integer migraineLevel = tracked ? trackingForThisDate.migraineLevel() : null;
			UUID migraineId = tracked ? trackingForThisDate.migraineId() : null;
			String trackingId = tracked ? trackingForThisDate.trackingId() : null;
			return new DayCell(d, false, weekend, today, tracked, painLevel, flowLevel, periodId, migraineLevel, trackingId, migraineId);
		}
	}
}
