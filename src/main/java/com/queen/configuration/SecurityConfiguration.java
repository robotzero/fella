package com.queen.configuration;

import com.queen.application.service.AttachUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {
//	@Bean
//	public CustomWebFilter CustomWebFilter(final AttachUserService attachUserService) {
//		return new CustomWebFilter(attachUserService);
//	}

	Converter<Jwt, AbstractAuthenticationToken> userIdExtractor(final com.queen.application.ports.in.AttachNewUserUseCase attachUserService, final JwtAuthenticationConverter jwtAuthenticationConverter) {
		return new FellaJwtAuthenticationConverter(attachUserService, jwtAuthenticationConverter);
	}

//	@Bean
//	SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, AttachUserService attachUserService, JwtAuthenticationConverter jwtAuthenticationConverter) {
//		http
//				.authorizeExchange((authorize) -> {
//					authorize.pathMatchers("/actuator/**").permitAll();
//					authorize.pathMatchers("/view/login").permitAll();
//					authorize.pathMatchers("/view/login/**").permitAll();
//					//authorize.pathMatchers("/**").hasAuthority("SCOPE_message.read");
//					authorize.anyExchange().authenticated();
//				})
//				.csrf(ServerHttpSecurity.CsrfSpec::disable)
//				.cors(ServerHttpSecurity.CorsSpec::disable)
//				.oauth2ResourceServer((resourceServer) -> resourceServer.jwt(jwt -> jwt.jwtAuthenticationConverter(userIdExtractor(attachUserService, jwtAuthenticationConverter))))
//				.oauth2Login(Customizer.withDefaults())
//				.oauth2Client(Customizer.withDefaults());
//		;
//		return http.build();
//	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, AttachUserService attachUserService, JwtAuthenticationConverter jwtAuthenticationConverter) throws Exception {
		// @formatter:off
		http
				.securityMatcher("/user/**")
				.authorizeHttpRequests((authorize) -> authorize
						.requestMatchers("/user/**").hasAuthority("SCOPE_user.read")
						.requestMatchers("/actuator/**").permitAll()
						.requestMatchers("/view/login").permitAll()
						.requestMatchers("/view/login/**").permitAll()
				)
				.csrf(AbstractHttpConfigurer::disable)
				.cors(AbstractHttpConfigurer::disable)
				.oauth2ResourceServer((resourceServer) -> resourceServer.jwt(jwt -> jwt.jwtAuthenticationConverter(userIdExtractor(attachUserService, jwtAuthenticationConverter))))
				.oauth2Client(Customizer.withDefaults());
		// @formatter:on

		return http.build();
	}
}
