package com.queen.infrastructure.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("users")
public class User {
	@Id
	@Column("user_id")
	private final UUID id;

	@Column("username")
	private final String userName;

	@Column("enabled")
	private final boolean isEnabled;

	public User(final UUID id, final String userName, final boolean isEnabled) {
		this.id = id;
		this.userName = userName;
		this.isEnabled = isEnabled;
	}

	public UUID getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}

	public boolean isEnabled() {
		return isEnabled;
	}
}
