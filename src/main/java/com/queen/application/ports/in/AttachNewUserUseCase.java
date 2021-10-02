package com.queen.application.ports.in;

import reactor.core.publisher.Mono;

public interface AttachNewUserUseCase {
	Mono<Void> attachNewUserDetails(final AttachUserCommand createNewUserCommand);
}
