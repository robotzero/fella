package com.queen.adapters.web;

import com.fasterxml.jackson.annotation.JsonGetter;

import javax.validation.constraints.NotNull;
import java.util.List;

public record MonitorTypeDTO(@NotNull String id, @NotNull String name, List<FieldsDTO> fieldDTOs, List<FieldTypeDTO> fieldTypes) {
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

	@JsonGetter("fieldTypes")
	@Override
	public List<FieldTypeDTO> fieldTypes() {
		return fieldTypes;
	}
}
