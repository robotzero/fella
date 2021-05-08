package com.queen.adapters.web;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.queen.domain.fieldtype.FieldTypeValue;

public record FieldTypeDTO(String name, FieldTypeValue fieldTypeValue) {
	@JsonGetter("name")
	@Override
	public String name() {
		return name;
	}

	@JsonGetter("fieldTypeValue")
	@Override
	public FieldTypeValue fieldTypeValue() {
		return fieldTypeValue;
	}
}
