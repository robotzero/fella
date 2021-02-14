package com.queen.configuration;

import com.queen.application.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {
	@Bean
	public SecurityWebFilterChain securityWebFilterChain(
			ServerHttpSecurity http) {
		return http.authorizeExchange().pathMatchers("/**").hasAuthority("SCOPE_message.read").anyExchange()
				.authenticated()
				.and()
				.oauth2ResourceServer()
				.jwt(withDefaults())
				.and()
				.build();
	}

	@Bean
	public CustomWebFilter CustomWebFilter(final UserService userService) {
		return new CustomWebFilter(userService);
	}
}
