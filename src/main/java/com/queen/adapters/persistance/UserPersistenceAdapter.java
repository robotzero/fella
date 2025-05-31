package com.queen.adapters.persistance;

import com.queen.application.ports.out.CreateUserPort;
import com.queen.application.ports.out.LoadUserPort;
import com.queen.infrastructure.persistence.User;
import com.queen.infrastructure.persistence.UserRepository;

import java.util.Optional;

public class UserPersistenceAdapter implements CreateUserPort, LoadUserPort {
	private final UserRepository userRepository;

	public UserPersistenceAdapter(final UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User createUser(final User user) {
		return this.userRepository.save(user);
	}

	@Override
	public Optional<User> loadUser(final String username) {
		return userRepository.findByUserName(username);
	}
}
