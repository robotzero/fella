package com.queen.application.ports.in;

import com.queen.domain.user.FellaUser;

public interface CreateUserUseCase {
	FellaUser createUser(final CreateUserCommand createuserCommand);
}
