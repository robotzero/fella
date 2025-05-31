package com.queen.infrastructure.persistence;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "users")
public record User(
		@Id
		@Column(name = "user_id")
		UUID id,

		@Column(name = "username")
		String userName,

		@Column(name = "enabled")
		boolean enabled
) {}

