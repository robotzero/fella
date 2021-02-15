package com.queen.adapters.persistance;

import com.queen.infrastructure.persitence.User;
import com.queen.infrastructure.persitence.UserRepository;

public class CreateUser implements com.queen.application.ports.out.CreateUser {
	private final UserRepository userRepository;

	public CreateUser(final UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public void createUser(final User user) {
		final var created = this.userRepository.save(user);
	}
}
