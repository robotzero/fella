package com.queen.adapters.web.controller.view;

import com.queen.application.service.PeriodService;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class IndexController {
	private final PeriodService periodService;

	public IndexController(final PeriodService periodService) {
		this.periodService = periodService;
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
}
