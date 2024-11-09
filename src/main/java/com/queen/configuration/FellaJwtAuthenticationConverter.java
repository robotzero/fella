package com.queen.configuration;

import com.queen.application.ports.in.AttachNewUserUseCase;
import com.queen.application.ports.in.AttachUserCommand;
import com.queen.domain.user.FellaUser;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import reactor.core.publisher.Mono;

public class FellaJwtAuthenticationConverter implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {
	private final AttachNewUserUseCase attachUserService;
	private final JwtAuthenticationConverter jwtAuthenticationConverter;

	public FellaJwtAuthenticationConverter(final AttachNewUserUseCase attachUserService, final JwtAuthenticationConverter jwtAuthenticationConverter) {
		this.attachUserService = attachUserService;
		this.jwtAuthenticationConverter = jwtAuthenticationConverter;
	}

	@Override
	public Mono<AbstractAuthenticationToken> convert(Jwt source) {
		final var attachUserCommand = new AttachUserCommand(source);
		final var token = (JwtAuthenticationToken) jwtAuthenticationConverter.convert(source);
		return attachUserService.attachNewUserDetails(attachUserCommand).map(fellaUser -> {
			return (AbstractAuthenticationToken) new FellaJwtAuthenticationToken(token, fellaUser);
		}).defaultIfEmpty(new FellaJwtAuthenticationToken(token, FellaUser.empty(token.getName())));
	}
}
