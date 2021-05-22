package com.queen.adapters.persistance;

import com.queen.application.ports.out.CreateUserPort;
import com.queen.application.ports.out.LoadUserPort;
import com.queen.infrastructure.persitence.User;
import com.queen.infrastructure.persitence.UserRepository;
import reactor.core.publisher.Mono;

public class UserPersistenceAdapter implements CreateUserPort, LoadUserPort {
	private final UserRepository userRepository;

	public UserPersistenceAdapter(final UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public void createUser(final User user) {
		//TODO is there a better way than just have to subscribe? Maybe return created user to the caller and let
		//Spring to subscribe to the output
		user.setAsNew();
		final var created = this.userRepository.save(user);
		created.subscribe();
	}

	@Override
	public Mono<User> loadUser(final String email) {
		return userRepository.findByUserName(email);
	}
}
