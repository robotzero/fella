package com.queen.adapters.web.controller.view;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
import java.util.Set;
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
		ParameterizedTypeReference<@org.jetbrains.annotations.NotNull List<PeriodDto>> periodsBody = new ParameterizedTypeReference<>() {};

		String token = authorizedClient.getAccessToken().getTokenValue();
		List<PeriodDto> dto = restClient.get()
				.uri("api/period/all")
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer "  + token)
				.retrieve()
				.body(periodsBody);

		if (dto == null) {
			model.addAttribute("year", y);
			model.addAttribute("months", buildMonths(y, Set.of(), ZoneId.systemDefault()));
			model.addAttribute("error", "Something went wrong");
			return "calendar";
		}
		Set<LocalDate> periodDates = dto.stream().flatMap(d -> d.dailyTracking().stream())
				.map(d -> LocalDate.parse(d.trackingDate(), DMY))
				.collect(Collectors.toSet());

		List<MonthVM> months = buildMonths(y, periodDates, ZoneId.systemDefault());

		model.addAttribute("year", y);
		model.addAttribute("months", months);
		return "calendar";
	}

	@PostMapping("/tracking/save")
	public String saveTracking(
			@RequestParam String trackingDate,
			@RequestParam int painLevel,
			@RequestParam int flowLevel,
			Model model) {

		// TODO: persist in DB or call your service
		System.out.printf("Saving tracking for %s — pain=%d, flow=%d%n",
				trackingDate, painLevel, flowLevel);

		// Return a small snippet that replaces the modal with success feedback
		model.addAttribute("message", "Saved successfully for " + trackingDate);
		return "fragments/modal-success";
	}

//
//	@RequestMapping("/view/period/all")
//	public String viewAllPeriods(Model model, @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient) {
//		String token = authorizedClient.getAccessToken().getTokenValue();
//		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//		System.out.println("token: " + token);
//		return "index";
//	}
//
//	@GetMapping("/view/period/end")
//	public Mono<String> viewEndPeriod(
//			Model model,
//			@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient,
//			@RequestParam("id") UUID periodId,
//			@RequestParam(value = "endDate", required = false) LocalDate endDate
//	) {
//		String token = authorizedClient.getAccessToken().getTokenValue();
//		final var request = new EndPeriodRequest(periodId.toString(), Optional.of(LocalDate.now()));
//		return webClient.put()
//				.uri("/api/period/end")
//				.body(Mono.just(request), EndPeriodRequest.class)
//				.header("Authorization", "Bearer " + token)
//				.retrieve()
//				.onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), response -> Mono.error(new RuntimeException(response.toString())))
//				.bodyToFlux(PeriodDTO.class)
//				.collectList()
//				.filter(periods -> !periods.isEmpty())
//				.map(period -> {
//					model.addAttribute("period", period.getFirst());
//					return "fragments/period-item";
//				});
//	}

	@GetMapping("/")
	public String viewPeriod(Model model, Principal principal, Authentication authentication, @RegisteredOAuth2AuthorizedClient("fella-webui") OAuth2AuthorizedClient authorizedClient) {
		String token = authorizedClient.getAccessToken().getTokenValue();
		System.out.println(token);
		System.out.println(authentication.getPrincipal());
		System.out.println(principal.getName());
		model.addAttribute("name", principal.getName());
		return "index";
	}

	private List<MonthVM> buildMonths(int year, Set<LocalDate> tracked, ZoneId zone) {
		LocalDate today = LocalDate.now(zone);
		List<MonthVM> res = new ArrayList<>(12);
		for (int m = 1; m <= 12; m++) {
			YearMonth ym = YearMonth.of(year, m);
			List<DayCell> cells = new ArrayList<>();

			// Monday-first offset
			LocalDate first = ym.atDay(1);
			int iso = first.getDayOfWeek().getValue(); // 1..7 (Mon..Sun)
			int offset = iso - 1; // 0..6

			// Leading blanks
			for (int i = 0; i < offset; i++) cells.add(new DayCell(null, true, false, false, false));

			// Actual days
			for (int d = 1; d <= ym.lengthOfMonth(); d++) {
				LocalDate date = ym.atDay(d);
				boolean weekend = date.getDayOfWeek() == DayOfWeek.SATURDAY
						|| date.getDayOfWeek() == DayOfWeek.SUNDAY;
				boolean isToday = date.equals(today);
				boolean isTracked = tracked.contains(date);
				cells.add(DayCell.of(d, weekend, isToday, isTracked));
			}

			// Trailing blanks to complete rows
			int total = cells.size();
			int target = ((total + 6) / 7) * 7;
			for (int i = total; i < target; i++) cells.add(new DayCell(null, true, false, false, false));

			res.add(new MonthVM(ym.getMonth().name().charAt(0) + ym.getMonth().name().substring(1).toLowerCase(), cells));
		}
		return res;
	}

	// --- simple DTOs (replace with your real ones) -------------------------
	public record PeriodDto(String periodId, String startDate, String endDate,
							List<Migraine> migraine, List<DailyTracking> dailyTracking) {}
	public record Migraine(String migraineDate, int severityLevel, String description) {}
	public record DailyTracking(int painLevel, int flowLevel, String trackingDate) {}

	// --- View Model ---------------------------------------------------------
	public record MonthVM(String name, List<DayCell> days) {}
	public record DayCell(Integer dayOfMonth, boolean blank, boolean weekend, boolean today, boolean tracked) {
		public static DayCell of(int d, boolean weekend, boolean today, boolean tracked) {
			return new DayCell(d, false, weekend, today, tracked);
		}
	}
}
