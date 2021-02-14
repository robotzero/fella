package com.queen.domain.monitor;

import javax.validation.constraints.NotNull;

public record Monitor(@NotNull int id, @NotNull String name) {
	@Override
	public String name() {
		return name;
	}

	@Override
	public int id() {
		return id;
	}
}
