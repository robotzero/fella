package com.queen.adapters.web;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.List;

public record MonitorTypeDTO(String id, String name, List<FieldsDTO> fieldDTOs, List<FieldTypeDTO> fieldTypes) {
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
