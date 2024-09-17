package com.queen.adapters.web.controller;

import com.queen.adapters.web.dto.UserDTO;
import com.queen.adapters.web.dto.UserToDTO;
import com.queen.application.ports.in.CreateUserCommand;
import com.queen.application.ports.in.CreateUserUseCase;
import com.queen.application.ports.in.UserEmailQuery;
import com.queen.configuration.FellaJwtAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class UserController {
	private final UserEmailQuery userEmailQuery;
	private final UserToDTO userToDTO;
	private final CreateUserUseCase createUserUseCase;

	public UserController(final UserEmailQuery userEmailQuery, final UserToDTO userToDTO, final CreateUserUseCase createUserUseCase) {
		this.userEmailQuery = userEmailQuery;
		this.userToDTO = userToDTO;
		this.createUserUseCase = createUserUseCase;
	}

	@GetMapping("/user")
	@Transactional(readOnly = true)
	Mono<UserDTO> getUser(@CurrentSecurityContext() FellaJwtAuthenticationToken token) {
		//@TODO validate the email
		//@TODO custom exception throwing 401
		return userEmailQuery.getUserByEmail(token.getName()).map(userToDTO::userDTO).switchIfEmpty(Mono.defer(() -> {
			return Mono.error(new BadCredentialsException("Unknown error"));
		}));
	}

	@PostMapping("/user")
	// @TODO check if indeed transactional works with weblux
	@Transactional
	Mono<UserDTO> createUser(FellaJwtAuthenticationToken token) {
		//TODO Response entity with proper code
		final var createUserCommand = new CreateUserCommand(token.getName(), token);
		return createUserUseCase.createUser(createUserCommand).map(userToDTO::userDTO);
	}

	@GetMapping("/user/context")
	@Transactional(readOnly = true)
	Mono<Object> getUserContext(@CurrentSecurityContext() FellaJwtAuthenticationToken token) {
		return ReactiveSecurityContextHolder.getContext().map(securityContext -> (securityContext.getAuthentication().getCredentials()));
	}
}
