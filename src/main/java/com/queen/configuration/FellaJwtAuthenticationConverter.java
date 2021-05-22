package com.queen.configuration;

import com.queen.application.ports.in.AttachUserCommand;
import com.queen.application.service.AttachUserService;
import com.queen.domain.user.FellaUser;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class FellaJwtAuthenticationConverter implements Converter<AbstractAuthenticationToken, AbstractAuthenticationToken> {
	private final AttachUserService attachNewUserService;

	public FellaJwtAuthenticationConverter(final AttachUserService attachNewUserService) {
		this.attachNewUserService = attachNewUserService;
	}

	@Override
	public FellaJwtAuthenticationToken convert(final AbstractAuthenticationToken sourceToken) {
		JwtAuthenticationToken token = (JwtAuthenticationToken) sourceToken;
		attachNewUserService.attachNewUserDetails(new AttachUserCommand(token));
		final FellaUser user = (FellaUser) token.getDetails();
		return new FellaJwtAuthenticationToken(token, user);
	}
}
