package com.queen.application.ports.in;

import com.queen.configuration.FellaJwtAuthenticationToken;

public record AttachUserCommand(FellaJwtAuthenticationToken jwtAuthenticationToken) {

}
