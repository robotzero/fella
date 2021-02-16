package com.queen.application.ports.out;

import com.queen.infrastructure.persitence.User;

public interface CreateUserPort {
	void createUser(final User user);
}
