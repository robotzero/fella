package com.queen.application.service;

import com.queen.adapters.persistance.UserMapper;
import com.queen.application.ports.in.AttachUserCommand;
import com.queen.application.ports.out.LoadUserPort;
import com.queen.configuration.FellaJwtAuthenticationToken;
import com.queen.infrastructure.persitence.User;
import reactor.core.publisher.Mono;

public class AttachUserService implements com.queen.application.ports.in.AttachNewUserUseCase {
	private final LoadUserPort loadUser;
	private final UserMapper userMapper;

	public AttachUserService(final LoadUserPort loadUser, final UserMapper userMapper) {
		this.loadUser = loadUser;
		this.userMapper = userMapper;
	}

//	@Override
//	public void attachNewUserDetails(final AttachUserCommand attachNewUserCommand) {
//		final var disposable = this.loadUser.loadUser(attachNewUserCommand.jwtAuthenticationToken().getName()).single()
//				.subscribe((user) -> {
//			attachUserDetailsToToken(user, attachNewUserCommand);
//			FellaJwtAuthenticationToken token = attachNewUserCommand.jwtAuthenticationToken();
//			token.setUserId(user.getId());
//		}, (throwable) -> {
//			throw new RuntimeException("Unknown Error");
//		});
//		disposable.dispose();
//	}

	@Override
	public Mono<Void> attachNewUserDetails(final AttachUserCommand attachNewUserCommand) {
		return this.loadUser.loadUser(attachNewUserCommand.jwtAuthenticationToken().getName()).single().doOnNext(user -> {
			System.out.println("USER CRAP !!!!!!!!!!!!!!!!!!!!");
			attachUserDetailsToToken(user, attachNewUserCommand);
			FellaJwtAuthenticationToken token = attachNewUserCommand.jwtAuthenticationToken();
			token.setUserId(user.getId());
		}).and(Mono.fromRunnable(() -> System.out.println("BLAAAAAA"))).then();
	}

	private void attachUserDetailsToToken(final User user, final AttachUserCommand attachNewUserCommand) {
		attachNewUserCommand.jwtAuthenticationToken().setDetails(userMapper.mapToDomain(user));
	}
}
