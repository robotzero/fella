package com.queen.configuration;

import com.queen.adapters.persistance.UserMapper;
import com.queen.application.ports.in.CreateUserTemplateEvent;
import com.queen.application.ports.out.CreateUserPort;
import com.queen.application.ports.out.LoadUserPort;
import com.queen.application.service.AttachNewUserService;
import com.queen.application.service.MonitorTypeService;
import com.queen.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfiguration {
	@Autowired
	AttachNewUserService attachNewUserService;

	@Bean
	public SecurityWebFilterChain securityWebFilterChain(
			ServerHttpSecurity http) {
		return http.authorizeExchange().pathMatchers("/**").hasAuthority("SCOPE_message.read").anyExchange()
				.authenticated()
				.and()
				.httpBasic().disable()
				.oauth2ResourceServer(oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec.jwt(jwtSpec -> {
							jwtSpec.jwtAuthenticationConverter(userIdExtractor());
				}))
				.build();
	}

	@Bean
	ReactiveUserDetailsService reactiveUserDetailsService(final LoadUserPort loadUserPort, final UserMapper userMapper, final MonitorTypeService monitorTypeService, final CreateUserPort createUserPort) {
		return new UserService(loadUserPort, userMapper, monitorTypeService, createUserPort);
	}

	@Bean
	public CustomWebFilter CustomWebFilter(final CreateUserTemplateEvent createUserTemplateUseCase) {
		return new CustomWebFilter(createUserTemplateUseCase);
	}

	Converter<Jwt, Mono<AbstractAuthenticationToken>> userIdExtractor() {
		final var jwtAuthenticationConverter =
				new JwtAuthenticationConverter().andThen(new FellaJwtAuthenticationConverter(attachNewUserService));
		return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
	}
}
