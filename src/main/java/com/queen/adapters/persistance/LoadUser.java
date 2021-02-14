package com.queen.adapters.persistance;

import com.queen.infrastructure.persitence.User;
import com.queen.infrastructure.persitence.UserRepository;
import reactor.core.publisher.Mono;

public class LoadUser implements com.queen.application.ports.out.LoadUser {
	private final UserRepository userRepository;

	public LoadUser(final UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public Mono<User> loadUser(String name) {
		return userRepository.findByUserName(name);
	}
}
