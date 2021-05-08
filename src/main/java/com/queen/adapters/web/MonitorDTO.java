package com.queen.adapters.web;

import com.fasterxml.jackson.annotation.JsonGetter;

public record MonitorDTO(int id, String name) {
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
