package com.queen.adapters.web.controller.view;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.security.Principal;

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
				.bodyToFlux(String.class)
				.collectList()
				.map(periods -> {
					model.addAttribute("periods", periods);
					return "fragments/period-list";
				});
	}

	@GetMapping("/")
	public String viewPeriod(Model model, Principal principal, Authentication authentication) {
		model.addAttribute("name", principal.getName());
		return "index";
	}
}
