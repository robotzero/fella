package com.queen.application.ports.in;

import com.queen.adapters.web.FieldsDTO;

import javax.validation.constraints.NotNull;

public record CreateFieldTypeCommand(@NotNull FieldsDTO fieldTypeDTO) {
}
