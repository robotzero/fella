package com.queen.adapters.web.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Controller
public class IndexController {
	private final WebClient webClient;

	public IndexController(final WebClient webClient) {
		this.webClient = webClient;
	}

	@GetMapping("/view/period/all")
	public Mono<String> viewAllPeriods(Model model) {
		return webClient.get()
				.uri("/api/period/all")
				.retrieve()
				.bodyToFlux(String.class).collectList().map(periods -> {
					model.addAttribute("periods", periods);
					return "periods";
				});
	}

	@GetMapping("/")
	public String viewPeriod(Model model) {
		return "index";
	}
}
