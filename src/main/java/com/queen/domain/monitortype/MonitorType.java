package com.queen.domain.monitortype;

import javax.validation.constraints.NotNull;

public record MonitorType(@NotNull int id, @NotNull String name) {
	@Override
	public String name() {
		return name;
	}

	@Override
	public int id() {
		return id;
	}
}
