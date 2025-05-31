package com.queen.adapters.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.ResponseStatus;

//
//import com.queen.adapters.web.dto.UserDTO;
//import com.queen.adapters.web.dto.UserToDTO;
//import com.queen.application.ports.in.CreateUserCommand;
//import com.queen.application.ports.in.CreateUserUseCase;
//import com.queen.application.ports.in.UserEmailQuery;
//import com.queen.configuration.FellaJwtAuthenticationToken;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.core.annotation.CurrentSecurityContext;
//import org.springframework.security.core.context.ReactiveSecurityContextHolder;
//import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class TestController {

	@GetMapping("/principal")
	String[] getUser(Principal principal, @AuthenticationPrincipal Jwt jwt) {
		return new String[] {jwt.getTokenValue()};
	}

	@GetMapping("/token")
	@ResponseStatus(HttpStatus.OK)
	public String[] test(Authentication authentication, Principal principal, @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient,
			@AuthenticationPrincipal OAuth2User oauth2User) {
		OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
//	  FellaJwtAuthenticationToken jwtAuthentication = (FellaJwtAuthenticationToken) authentication;
		return new String[] {"Message 1", "Message 2", "Message 4", accessToken.getTokenValue()};
	}

//	@GetMapping(value = "/test", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//	public Flux<Integer> test() {
//		return Flux.fromIterable(List.of(1, 2, 3, 4, 5)).delayElements(java.time.Duration.ofSeconds(1));
//	}

}
//	private final UserEmailQuery userEmailQuery;
//	private final UserToDTO userToDTO;
//	private final CreateUserUseCase createUserUseCase;
//
//	public UserController(final UserEmailQuery userEmailQuery, final UserToDTO userToDTO, final CreateUserUseCase createUserUseCase) {
//		this.userEmailQuery = userEmailQuery;
//		this.userToDTO = userToDTO;
//		this.createUserUseCase = createUserUseCase;
//	}
//
//	@GetMapping("/user")
//	@Transactional(readOnly = true)
//	Mono<UserDTO> getUser(@CurrentSecurityContext() FellaJwtAuthenticationToken token) {
//		//@TODO validate the email
//		//@TODO custom exception throwing 401
//		return userEmailQuery.getUserByEmail(token.getName()).map(userToDTO::userDTO).switchIfEmpty(Mono.defer(() -> {
//			return Mono.error(new BadCredentialsException("Unknown error"));
//		}));
//	}
//
//	@PostMapping("/user")
//	// @TODO check if indeed transactional works with weblux
//	@Transactional
//	Mono<UserDTO> createUser(FellaJwtAuthenticationToken token) {
//		//TODO Response entity with proper code
//		final var createUserCommand = new CreateUserCommand(token.getName(), token);
//		return createUserUseCase.createUser(createUserCommand).map(userToDTO::userDTO);
//	}
//
//	@GetMapping("/user/context")
//	@Transactional(readOnly = true)
//	Mono<Object> getUserContext(@CurrentSecurityContext() FellaJwtAuthenticationToken token) {
//		return ReactiveSecurityContextHolder.getContext().map(securityContext -> (securityContext.getAuthentication().getCredentials()));
//	}
//}
