package com.queen.infrastructure.persitence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User, String> {
	Mono<User> findByUserName(String name);
}
