package com.queen.application.ports.out;

import com.queen.infrastructure.persistence.User;
import reactor.core.publisher.Mono;

public interface CreateUserPort {
	Mono<User> createUser(final User user);
}
