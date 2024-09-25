package com.queen.domain.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class FellaUser extends User {
	private final UUID id;

	public FellaUser(UUID id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.id = id;
	}

	public UUID getId() {
		return id;
	}

	public static FellaUser empty(String name) {
		return new FellaUser(null, name, name, List.of());
	}
}
