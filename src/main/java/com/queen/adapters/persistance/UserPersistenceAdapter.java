package com.queen.adapters.persistance;

import com.queen.application.ports.out.CreateUserPort;
import com.queen.application.ports.out.LoadUserPort;
import com.queen.infrastructure.persistence.User;
import com.queen.infrastructure.persistence.UserRepository;
import reactor.core.publisher.Mono;

public class UserPersistenceAdapter implements CreateUserPort, LoadUserPort {
	private final UserRepository userRepository;

	public UserPersistenceAdapter(final UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public Mono<User> createUser(final User user) {
		user.setAsNew();
		return this.userRepository.save(user);
	}

	@Override
	public Mono<User> loadUser(final String email) {
		return userRepository.findByUserName(email);
	}
}
