package com.queen.adapters.persistance;

import com.queen.domain.user.FellaUser;

import java.util.List;

public class UserMapper {
	public FellaUser mapToDomain(com.queen.infrastructure.persistence.User user) {
		return new FellaUser(user.id(), user.userName(), "", List.of());
	}
}
