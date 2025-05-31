package com.queen.configuration;

import com.queen.application.ports.in.AttachNewUserUseCase;
import com.queen.application.ports.in.AttachUserCommand;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class FellaJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
	private final AttachNewUserUseCase attachUserService;
	private final JwtAuthenticationConverter jwtAuthenticationConverter;

	public FellaJwtAuthenticationConverter(final AttachNewUserUseCase attachUserService, final JwtAuthenticationConverter jwtAuthenticationConverter) {
		this.attachUserService = attachUserService;
		this.jwtAuthenticationConverter = jwtAuthenticationConverter;
	}

	@Override
	public AbstractAuthenticationToken convert(Jwt source) {
		final var attachUserCommand = new AttachUserCommand(source);
		final var token = (JwtAuthenticationToken) jwtAuthenticationConverter.convert(source);
		return new FellaJwtAuthenticationToken(token, attachUserService.attachNewUserDetails(attachUserCommand));
	}
}
