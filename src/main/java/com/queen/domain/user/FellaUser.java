package com.queen.domain.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

public class FellaUser extends User {
	private final String id;

	public FellaUser(String id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public static FellaUser empty(String name) {
		return new FellaUser(null, name, name, List.of());
	}
}
