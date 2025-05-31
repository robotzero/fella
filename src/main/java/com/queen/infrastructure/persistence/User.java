package com.queen.infrastructure.persistence;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
	@Id
	@Column(name = "user_id", nullable = false, updatable = false)
	private UUID id;

	@Column(name = "username", nullable = false)
	private String userName;

	@Column(name = "enabled", nullable = false)
	private boolean enabled;

	protected User() {
		// Required by JPA
	}

	public User(UUID id, String userName, boolean enabled) {
		this.id = id;
		this.userName = userName;
		this.enabled = enabled;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
