package com.queen.application.ports.in;

import com.queen.domain.user.FellaUser;

public interface AttachNewUserUseCase {
	FellaUser attachNewUserDetails(final AttachUserCommand createNewUserCommand);
}
