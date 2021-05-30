package com.queen.domain.user;

import com.queen.domain.monitortype.MonitorType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

public class FellaUser extends User {
	private final String id;
	private final List<MonitorType> monitorTypes;

	public FellaUser(String id, String username, String password, Collection<? extends GrantedAuthority> authorities, List<MonitorType> monitorTypes) {
		super(username, password, authorities);
		this.id = id;
		this.monitorTypes = monitorTypes;
	}

	public String getId() {
		return id;
	}

	public static FellaUser empty(String name) {
		return new FellaUser(null, name, name, List.of(), List.of());
	}
}
