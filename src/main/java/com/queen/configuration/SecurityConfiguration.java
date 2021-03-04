package com.queen.configuration;

import com.queen.adapters.persistance.UserMapper;
import com.queen.application.ports.out.LoadUserPort;
import com.queen.application.service.AttachNewUserService;
import com.queen.application.service.UserService;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

import javax.inject.Scope;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfiguration {
	@Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
	private String issuer;

	@Bean
	public SecurityWebFilterChain securityWebFilterChain(
			ServerHttpSecurity http) {
//		http.authenticationManager(reactiveAuthenticationManager());
		return http.authorizeExchange().pathMatchers("/**").hasAuthority("SCOPE_message.read").anyExchange()
				.authenticated()
				.and()
				.httpBasic().disable()
				.oauth2ResourceServer()
				.jwt()
				.and()
				.and().build();
	}

	@Bean
	ReactiveUserDetailsService reactiveUserDetailsService(final LoadUserPort loadUserPort, final UserMapper userMapper) {
		return new UserService(loadUserPort, userMapper);
	}

//	@Bean
//	ReactiveAuthenticationManager reactiveAuthenticationManager() {
//		return new FellaAuthenticationManager();
//	}

	@Bean
	@RequestScoped
	public CustomWebFilter CustomWebFilter(final AttachNewUserService attachNewUserService) {
		return new CustomWebFilter(attachNewUserService);
	}


//	@Bean
//	PasswordEncoder passwordEncoder() {
//		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//	}
//
//	ReactiveJwtDecoder jwtDecoder() {
//		OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuer);
//		OAuth2TokenValidator<Jwt> validator = new DelegatingOAuth2TokenValidator<>(withIssuer);
//
//		NimbusJwtDecoder jwtDecoder = (NimbusJwtDecoder) JwtDecoders.fromOidcIssuerLocation(issuer);
//		jwtDecoder.setJwtValidator(validator);
//
//		return new ReactiveJwtDecoder() {
//			@Override
//			public Mono<Jwt> decode(String token) throws JwtException {
//				return Mono.just(jwtDecoder.decode(token));
//			}
//		};
//	}
}
