package com.queen.application.ports.in;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public record AttachUserCommand(JwtAuthenticationToken jwtAuthenticationToken) {

}
