package com.queen.configuration;

import com.queen.domain.user.FellaUser;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.Transient;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.io.Serial;
import java.util.Map;

@Transient
public class FellaJwtAuthenticationToken extends AbstractOAuth2TokenAuthenticationToken<Jwt> {
	private final JwtAuthenticationToken jwtAuthenticationToken;
	@Serial
	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
	private final String name;
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

	public String getUserId() {
		return fellaUser.getId();
	}

	@Override
	public String getName() {
		return name;
	}
}
