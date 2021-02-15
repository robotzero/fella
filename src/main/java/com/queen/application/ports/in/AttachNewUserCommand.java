package com.queen.application.ports.in;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import javax.validation.constraints.NotNull;

public record AttachNewUserCommand(@NotNull JwtAuthenticationToken jwtAuthenticationToken) {

}
