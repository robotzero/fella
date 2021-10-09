package com.queen.application.ports.in;

import com.queen.domain.user.FellaUser;
import reactor.core.publisher.Mono;

public interface AttachNewUserUseCase {
	Mono<FellaUser> attachNewUserDetails(final AttachUserCommand createNewUserCommand);
}
