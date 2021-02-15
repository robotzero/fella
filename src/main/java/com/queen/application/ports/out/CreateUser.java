package com.queen.application.ports.out;

import com.queen.infrastructure.persitence.User;

public interface CreateUser {
	void createUser(final User user);
}
