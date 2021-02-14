package com.queen.infrastructure.persitence;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("users")
public class User {
	@Id
	@Column("id")
	private final String id;

	@Column("username")
	private final String userName;

	public User(final String id, final String userName) {
		this.id = id;
		this.userName = userName;
	}

	public String getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}
}
