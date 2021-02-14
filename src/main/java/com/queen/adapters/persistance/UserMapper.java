package com.queen.adapters.persistance;

import com.queen.domain.user.User;

public class UserMapper {
	public User mapToDomain(com.queen.infrastructure.persitence.User user) {
		return new User(user.getId(), user.getUserName());
	}
}
