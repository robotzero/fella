package com.queen.application.ports.out;

import com.queen.infrastructure.persistence.User;

public interface CreateUserPort {
	User createUser(final User user);
}
