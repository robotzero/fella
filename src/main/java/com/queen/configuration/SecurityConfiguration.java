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
	//	 @Bean
//	 public SecurityWebFilterChain securityWebFilterChain(
//			ServerHttpSecurity http, AttachNewUserUseCase attachNewUserUseCase) {
//		 return http.authorizeExchange((authCustomizer) -> {
//			 authCustomizer.pathMatchers("/actuator/**").permitAll();
//			 authCustomizer.pathMatchers("/**").hasAuthority("SCOPE_message.read").anyExchange().authenticated();
//		 }).oauth2ResourceServer(oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec.jwt(jwtSpec -> {
//			 jwtSpec.jwtAuthenticationConverter(userIdExtractor(attachNewUserUseCase));
//		 })).build();
//	 }
	@Bean
	public CustomWebFilter CustomWebFilter(final AttachUserService attachUserService) {
		return new CustomWebFilter(attachUserService);
	}

	Converter<Jwt, Mono<AbstractAuthenticationToken>> userIdExtractor(final com.queen.application.ports.in.AttachNewUserUseCase attachUserService) {
		return new JwtAuthenticationConverter().andThen(new FellaJwtAuthenticationConverter(attachUserService));
	}
//	@Bean
//	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
//		// Apply the default OAuth2 Authorization Server security configurations
//		//OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
//
//		return http
//				.csrf(csrf -> csrf.disable()).authorizeExchange(authorizeExchangeSpec -> authorizeExchangeSpec.anyExchange().authenticated())// Disable CSRF
//					.ignoringRequestMatchers("/oauth2/token") // Disable CSRF for /oauth2/token
//			)
//			.authorizeExchange(exchanges -> exchanges
//					.anyExchange().authenticated() // Ensure authentication is required for other requests
//			)
//			.oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::jwt) // If using JWT tokens
//				.build();
//	}
	@Bean
	SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		http
				.authorizeExchange((authorize) -> authorize.anyExchange().authenticated())
				.csrf(ServerHttpSecurity.CsrfSpec::disable)
				.oauth2ResourceServer((resourceServer) -> resourceServer.jwt(Customizer.withDefaults()))
				.oauth2Login(Customizer.withDefaults())
				.oauth2Client(Customizer.withDefaults());
		return http.build();
	}

}
