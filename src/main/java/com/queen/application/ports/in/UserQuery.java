package com.queen.application.ports.in;

import com.queen.domain.user.User;
import reactor.core.publisher.Mono;

public interface UserQuery {
	Mono<User> load();
}
