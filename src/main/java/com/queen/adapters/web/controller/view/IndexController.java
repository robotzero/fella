package com.queen.adapters.web.controller.view;

import com.queen.adapters.web.dto.EndPeriodRequest;
import com.queen.adapters.web.dto.PeriodDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class IndexController {
	private final WebClient webClient;

	public IndexController(final WebClient webClient) {
		this.webClient = webClient;
	}

	@GetMapping("/view/period/all")
	public Mono<String> viewAllPeriods(Model model, @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient) {
		String token = authorizedClient.getAccessToken().getTokenValue();
		return webClient.get()
				.uri("/api/period/all")
				.header("Authorization", "Bearer " + token)
				.retrieve()
				.bodyToFlux(PeriodDTO.class)
				.collectList()
				.map(periods -> {
					model.addAttribute("periods", periods);
					return "fragments/period-list";
				});
	}

	@GetMapping("/view/period/end")
	public Mono<String> viewEndPeriod(
			Model model,
			@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient,
			@RequestParam("id") UUID periodId,
			@RequestParam(value = "endDate", required = false) LocalDate endDate
	) {
		String token = authorizedClient.getAccessToken().getTokenValue();
		final var request = new EndPeriodRequest(periodId.toString(), Optional.of(LocalDate.now()));
		return webClient.put()
				.uri("/api/period/end")
				.body(Mono.just(request), EndPeriodRequest.class)
				.header("Authorization", "Bearer " + token)
				.retrieve()
				.onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), response -> Mono.error(new RuntimeException(response.toString())))
				.bodyToMono(PeriodDTO.class)
				.map(response -> {
					model.addAttribute("period", response);
					return "fragments/period-item";
				});
	}

	@GetMapping("/")
	public String viewPeriod(Model model, Principal principal, Authentication authentication) {
		model.addAttribute("name", principal.getName());
		return "index";
	}
}
