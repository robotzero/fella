package com.queen.adapters.persistance;

import com.queen.domain.user.FellaUser;

import java.util.List;

public class UserMapper {
	public FellaUser mapToDomain(com.queen.infrastructure.persitence.User user) {
		return new FellaUser(user.getId(), user.getUserName(), "", List.of());
	}
}
