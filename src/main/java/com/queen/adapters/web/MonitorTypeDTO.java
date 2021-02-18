package com.queen.adapters.web;

import com.fasterxml.jackson.annotation.JsonGetter;

import javax.validation.constraints.NotNull;

public record MonitorTypeDTO(@NotNull String id, @NotNull String name, @NotNull String userId) {
	@JsonGetter("id")
	@Override
	public String id() {
		return id;
	}

	@JsonGetter("name")
	@Override
	public String name() {
		return name;
	}
}
