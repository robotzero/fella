package com.queen.infrastructure.persistence;

import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "users")
public class User {
	@Id
	@Column("user_id")
	private UUID id;

	@Column("username")
	private String userName;

	@Column("enabled")
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
