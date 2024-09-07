package com.queen.application.ports.out;

import com.queen.infrastructure.persistence.User;
import reactor.core.publisher.Mono;

public interface LoadUserPort {
	Mono<User> loadUser(final String email);
}
