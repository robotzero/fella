package com.queen.domain.monitor;

public record Monitor(int id, String name) {
	@Override
	public String name() {
		return name;
	}

	@Override
	public int id() {
		return id;
	}
}
