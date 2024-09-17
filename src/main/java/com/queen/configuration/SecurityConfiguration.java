package com.queen.configuration;

import com.queen.application.service.AttachUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@EnableWebFluxSecurity
@Configuration
public class SecurityConfiguration {
//	@Bean
//	public CustomWebFilter CustomWebFilter(final AttachUserService attachUserService) {
//		return new CustomWebFilter(attachUserService);
//	}

	Converter<Jwt, Mono<AbstractAuthenticationToken>> userIdExtractor(final com.queen.application.ports.in.AttachNewUserUseCase attachUserService) {
		return new JwtAuthenticationConverter().andThen(new FellaJwtAuthenticationConverter(attachUserService));
	}

	@Bean
	SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, AttachUserService attachUserService) {
		http
				.authorizeExchange((authorize) -> {
					authorize.pathMatchers("/actuator/**").permitAll();
					//authorize.pathMatchers("/**").hasAuthority("SCOPE_message.read");
					authorize.anyExchange().authenticated();
				})
				.csrf(ServerHttpSecurity.CsrfSpec::disable)
				.oauth2ResourceServer((resourceServer) -> resourceServer.jwt(jwt -> jwt.jwtAuthenticationConverter(userIdExtractor(attachUserService))))
				.oauth2Login(Customizer.withDefaults())
				.oauth2Client(Customizer.withDefaults());
		return http.build();
	}

}
