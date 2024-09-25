package com.queen.configuration;

import com.queen.domain.user.FellaUser;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.Transient;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.validation.annotation.Validated;

import java.util.Map;
import java.util.UUID;

@Transient
@Validated
public class FellaJwtAuthenticationToken extends AbstractOAuth2TokenAuthenticationToken<Jwt> {
	private final JwtAuthenticationToken jwtAuthenticationToken;
	private final @NotNull String name;
	private final FellaUser fellaUser;

	public FellaJwtAuthenticationToken(final JwtAuthenticationToken jwtAuthenticationToken, final FellaUser fellaUser) {
		super(jwtAuthenticationToken.getToken(), jwtAuthenticationToken.getAuthorities());
		this.setAuthenticated(true);
		this.jwtAuthenticationToken = jwtAuthenticationToken;
		this.fellaUser = fellaUser;
		this.name = jwtAuthenticationToken.getName();
	}

	@Override
	public Map<String, Object> getTokenAttributes() {
		return this.jwtAuthenticationToken.getToken().getClaims();
	}

	public UUID getUserId() {
		return fellaUser.getId();
	}

	@Override
	public String getName() {
		return name;
	}
}
