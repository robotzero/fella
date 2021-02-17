package com.queen.infrastructure.persitence;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("users")
public class User implements Persistable<String> {
	@Id
	@Column("id")
	private final String id;

	@Column("username")
	private final String userName;

	@Transient
	private boolean newUser;

	public User(final String id, final String userName) {
		this.id = id;
		this.userName = userName;
	}

	@Override
	@Transient
	public boolean isNew() {
		return this.newUser || id == null || id.isEmpty();
	}

	public String getId() {
		return id;
	}

	public void setAsNew() {
		this.newUser = true;
	}

	public String getUserName() {
		return userName;
	}
}
