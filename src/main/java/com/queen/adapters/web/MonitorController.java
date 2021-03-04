package com.queen.adapters.web;

import com.queen.application.service.MonitorService;
import com.queen.configuration.CurrentUser;
import com.queen.domain.user.FellaUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class MonitorController {
	private final MonitorService monitorService;
	private final MonitorToDTO monitorToDTO;

	public MonitorController(final MonitorService monitorService, final MonitorToDTO monitorToDTO) {
		this.monitorService = monitorService;
		this.monitorToDTO   = monitorToDTO;
	}

	@GetMapping("/monitors")
	public Flux<MonitorDTO> getAllMonitors(final JwtAuthenticationToken jwtAuthenticationToken, @AuthenticationPrincipal Mono<UserDetails> details, @CurrentUser FellaUser user) {
		System.out.println(user);
		System.out.println(details);
		ReactiveSecurityContextHolder.getContext().hasElement().filter(a -> a == false).subscribe(c -> System.out.println("NOT!!!!!!!!!!!!!"));
		return monitorService.loadAllMonitors().map(monitor -> {
			return monitorToDTO.toDTO(monitor);
		});
	}
}