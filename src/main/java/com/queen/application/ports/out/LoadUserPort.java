package com.queen.application.ports.out;

import com.queen.infrastructure.persistence.User;

import java.util.Optional;

public interface LoadUserPort {
	Optional<User> loadUser(final String email);
}
