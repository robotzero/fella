package com.queen.adapters.persistance;

import com.queen.domain.user.FellaUser;
import com.queen.infrastructure.persitence.User;

import java.util.List;

public class UserMapper {
	public FellaUser mapToDomain(com.queen.infrastructure.persitence.User user) {
		return new FellaUser(user.getId(), user.getUserName(), "", List.of());
	}

	public User mapToPersistence(FellaUser fellaUser) {
		return new User(fellaUser.getId(), fellaUser.getUsername());
	}
}
