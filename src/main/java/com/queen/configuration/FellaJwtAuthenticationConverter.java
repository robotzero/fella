package com.queen.configuration;

import com.queen.application.ports.in.AttachNewUserUseCase;
import com.queen.application.ports.in.AttachUserCommand;
import com.queen.domain.user.FellaUser;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import reactor.core.publisher.Mono;

public class FellaJwtAuthenticationConverter implements Converter<AbstractAuthenticationToken, Mono<AbstractAuthenticationToken>> {
	private final AttachNewUserUseCase attachUserService;

	public FellaJwtAuthenticationConverter(final AttachNewUserUseCase attachUserService) {
		this.attachUserService = attachUserService;
	}

	@Override
	public Mono<AbstractAuthenticationToken> convert(final AbstractAuthenticationToken sourceToken) {
		JwtAuthenticationToken token = (JwtAuthenticationToken) sourceToken;
		final var attachUserCommand = new AttachUserCommand(token);
		return attachUserService.attachNewUserDetails(attachUserCommand).map(fellaUser -> {
			return (AbstractAuthenticationToken) new FellaJwtAuthenticationToken(token, fellaUser);
		}).defaultIfEmpty(new FellaJwtAuthenticationToken(token, FellaUser.empty(token.getName())));
	}
}
