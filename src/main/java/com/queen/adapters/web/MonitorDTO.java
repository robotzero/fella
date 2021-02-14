package com.queen.adapters.web;

import com.fasterxml.jackson.annotation.JsonGetter;

import javax.validation.constraints.NotNull;

public record MonitorDTO(@NotNull int id, @NotNull String name) {
	@JsonGetter("id")
	@Override
	public int id() {
		return id;
	}

	@JsonGetter("name")
	@Override
	public String name() {
		return name;
	}
}
