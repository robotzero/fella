package com.queen.domain.monitortype;

import javax.validation.constraints.NotNull;

public record MonitorType(@NotNull String id, @NotNull String name) {
	@Override
	public String name() {
		return name;
	}

	@Override
	public String id() {
		return id;
	}
}
