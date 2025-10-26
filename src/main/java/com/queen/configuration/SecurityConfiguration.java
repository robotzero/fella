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

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, AttachUserService attachUserService, JwtAuthenticationConverter jwtAuthenticationConverter) throws Exception {
		// @formatter:off
		http
				.securityMatcher("/**")
				.authorizeHttpRequests((authorize) -> authorize
						.requestMatchers("/user/**").hasAuthority("SCOPE_user.read")
						.requestMatchers("/actuator/**").permitAll()
						.requestMatchers("/view/login").permitAll()
						.requestMatchers("/view/login/**").permitAll()
						.requestMatchers("/login").permitAll()
						.requestMatchers("/login/**").permitAll()
						.requestMatchers("/logout").permitAll()
						.requestMatchers("/view/logout").permitAll()
						.requestMatchers("/images/**").permitAll()
						.requestMatchers("/css/**").permitAll()
						.requestMatchers("/js/**").permitAll()
						.requestMatchers("/webjars/**").permitAll()
						.requestMatchers("/assets/**").permitAll()
						.requestMatchers("/favicon.ico").permitAll()
						.anyRequest().authenticated()
				)
				.csrf(AbstractHttpConfigurer::disable)
				.cors(AbstractHttpConfigurer::disable)
				.oauth2ResourceServer((resourceServer) -> resourceServer.jwt(jwt -> jwt.jwtAuthenticationConverter(userIdExtractor(attachUserService, jwtAuthenticationConverter))))
				.oauth2Client(Customizer.withDefaults())
				.oauth2Login(o -> o.loginPage("/login"));
		// @formatter:on

		return http.build();
	}
}
