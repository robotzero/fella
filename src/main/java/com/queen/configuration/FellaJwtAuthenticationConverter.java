package com.queen.configuration;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class FellaJwtAuthenticationConverter implements Converter<AbstractAuthenticationToken, AbstractAuthenticationToken> {

	@Override
	public FellaJwtAuthenticationToken convert(final AbstractAuthenticationToken sourceToken) {
		JwtAuthenticationToken token = (JwtAuthenticationToken) sourceToken;
		return new FellaJwtAuthenticationToken(token);
	}
}
