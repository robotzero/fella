package com.queen.application.ports.in;

import com.queen.configuration.FellaJwtAuthenticationToken;

public record CreateUserCommand(String username, FellaJwtAuthenticationToken token) {
}
