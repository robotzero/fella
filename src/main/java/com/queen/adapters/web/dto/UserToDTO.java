package com.queen.adapters.web.dto;

import com.queen.domain.user.FellaUser;

public class UserToDTO {
	public UserDTO userDTO(final FellaUser fellaUser) {
		return new UserDTO(fellaUser.getId(), fellaUser.getUsername(), fellaUser.getUsername());
	}
}
