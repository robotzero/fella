package com.queen.domain.user;

import javax.validation.constraints.NotNull;

public record User(@NotNull String id, @NotNull String name) {
	@Override
	public String id() {
		return this.id;
	}

	@Override
	public String name() {
		return this.name;
	}
}
