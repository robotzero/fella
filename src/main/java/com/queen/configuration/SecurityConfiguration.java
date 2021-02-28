package com.queen.configuration;

import com.queen.adapters.persistance.UserMapper;
import com.queen.application.ports.out.LoadUserPort;
import com.queen.application.service.AttachNewUserService;
import com.queen.application.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfiguration {

	@Bean
	public SecurityWebFilterChain securityWebFilterChain(
			ServerHttpSecurity http) {
		http.authenticationManager(reactiveAuthenticationManager());
		return http.authorizeExchange().pathMatchers("/**").hasAuthority("SCOPE_message.read").anyExchange()
				.authenticated()
				.and()
				.httpBasic().disable()
				.oauth2ResourceServer()
				.jwt(withDefaults())
				.and()
				.build();
	}

	@Bean
	ReactiveUserDetailsService reactiveUserDetailsService(final LoadUserPort loadUserPort, final UserMapper userMapper) {
		return new UserService(loadUserPort, userMapper);
	}

	@Bean
	ReactiveAuthenticationManager reactiveAuthenticationManager() {
		return new FellaReactiveAuthenticationManager();
	}

	@Bean
	public CustomWebFilter CustomWebFilter(final AttachNewUserService attachNewUserService) {
		return new CustomWebFilter(attachNewUserService);
	}
}
