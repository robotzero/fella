package com.queen.adapters.persistance;

import com.queen.application.ports.out.CreateUserPort;
import com.queen.application.ports.out.LoadUserPort;
import com.queen.infrastructure.persitence.User;
import com.queen.infrastructure.persitence.UserRepository;
import reactor.core.publisher.Mono;

public class UserPersistenceAdapter implements CreateUserPort, LoadUserPort {
	private final UserRepository userRepository;

	public UserPersistenceAdapter(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public void createUser(final User user) {
		user.setAsNew();
		final var created = this.userRepository.save(user);
		created.subscribe();
	}

	@Override
	public Mono<User> loadUser(String name) {
		return userRepository.findByUserName(name);
	}
}
