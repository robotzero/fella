package com.queen.configuration;

import com.queen.application.ports.in.AttachNewUserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfiguration {
	@Bean
	public SecurityWebFilterChain securityWebFilterChain(
			ServerHttpSecurity http, AttachNewUserUseCase attachNewUserUseCase) {
		return http.
				authorizeExchange().pathMatchers("/**").hasAuthority("SCOPE_message.read").anyExchange()
				.authenticated()
				.and()
				.httpBasic().disable()
				.oauth2ResourceServer(oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec.jwt(jwtSpec -> {
					jwtSpec.jwtAuthenticationConverter(userIdExtractor(attachNewUserUseCase));
				}))
				.build();
	}

//	@Bean
//	public CustomWebFilter CustomWebFilter(final AttachUserService attachUserService) {
//		return new CustomWebFilter(attachUserService);
//	}

	Converter<Jwt, Mono<AbstractAuthenticationToken>> userIdExtractor(final com.queen.application.ports.in.AttachNewUserUseCase attachUserService) {
		return new JwtAuthenticationConverter().andThen(new FellaJwtAuthenticationConverter(attachUserService));
	}

//	@Bean
//	PasswordEncoder passwordEncoder() {
//		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//	}
}
