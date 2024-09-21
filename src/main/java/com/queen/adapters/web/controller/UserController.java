package com.queen.adapters.web.controller;

import com.queen.adapters.web.dto.UserDTO;
import com.queen.adapters.web.dto.UserToDTO;
import com.queen.application.ports.in.CreateUserCommand;
import com.queen.application.ports.in.CreateUserUseCase;
import com.queen.application.ports.in.UserQuery;
import com.queen.application.service.exception.InvalidUserException;
import com.queen.configuration.FellaJwtAuthenticationToken;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Validated
@RestController
public class UserController {
	private final UserQuery userQuery;
	private final UserToDTO userToDTO;
	private final CreateUserUseCase createUserUseCase;

	public UserController(final UserQuery userQuery, final UserToDTO userToDTO, final CreateUserUseCase createUserUseCase) {
		this.userQuery = userQuery;
		this.userToDTO = userToDTO;
		this.createUserUseCase = createUserUseCase;
	}

	@GetMapping("/user")
	@Transactional(readOnly = true)
	Mono<ResponseEntity<UserDTO>> getUser(@NotNull(message = "Invalid authentication") FellaJwtAuthenticationToken token) {
		//@TODO validate the email
		return userQuery.getUserByUsername(token.getName())
				.map(userToDTO::userDTO)
				.map(ResponseEntity::ok)
				.switchIfEmpty(Mono.error(new InvalidUserException("Unauthorized")));
	}

	@PostMapping("/user")
	@Transactional
	Mono<ResponseEntity<UserDTO>> createUser(FellaJwtAuthenticationToken token) {
		final var createUserCommand = new CreateUserCommand(token.getName(), token);
		return createUserUseCase.createUser(createUserCommand).map(userToDTO::userDTO).map(ResponseEntity::ok);
	}

	@GetMapping("/user/context")
	@Transactional(readOnly = true)
	Mono<Object> getUserContext(FellaJwtAuthenticationToken token) {
		return ReactiveSecurityContextHolder.getContext().map(securityContext -> (securityContext.getAuthentication().getCredentials()));
	}
}
