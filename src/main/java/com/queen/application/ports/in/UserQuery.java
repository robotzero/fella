package com.queen.application.ports.in;

import com.queen.domain.user.FellaUser;

public interface UserQuery {
	FellaUser getUserByUsername(final String username);
}
